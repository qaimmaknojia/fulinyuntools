package annotate;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;

public class Experiment {

	public static String linkRecomIdxFold = "E:\\NamedEntityAnnotatorIndex\\LinkSuggestIndex_v4";
	public static double threshold = 0.5;
	public static int maxPhraseLength = 6;
	
	public static void main(String[] args) throws Exception {
		Date start = new Date();
		Experiment exp = new Experiment();
		exp.annotate(args[0]);
		Date end = new Date();
		System.out.println("total processing time: " + (end.getTime()-start.getTime()) + " milliseconds.");
	}
	
	public void annotate(String fin) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File(fin)));
		Searcher searcher = new IndexSearcher(linkRecomIdxFold);

		String tmp = br.readLine();
		while (tmp != null) {
			String[] terms = tmp.split(" ");
			Annotation[] r = annotate(searcher, terms);
			show(r);
			tmp = br.readLine();
		}
		br.close();
	}
	
	// find all possible annotations in this sequence of terms
	public Annotation[] annotate(Searcher searcher, String[] terms) throws Exception {
		String[] candidates = generateCandidates(terms);
		Annotation[] buffer = new Annotation[maxPhraseLength*terms.length];
		int r = 0;
		for (int i = 0; i < candidates.length; i++) if (containalnum(candidates[i])){
			TermQuery queryanchor = new TermQuery(new Term("Anchor", candidates[i]));
			queryanchor.setBoost((float)1.03);
			TermQuery querytitle = new TermQuery(new Term("Title", candidates[i]));
			querytitle.setBoost((float)23.61);
			TermQuery queryredirect = new TermQuery(new Term("RedirectFrom", candidates[i]));
			queryredirect.setBoost((float)23.61);
			TermQuery queryinbold = new TermQuery(new Term("Inbold", candidates[i]));
			queryinbold.setBoost((float)22.92);
			TermQuery querydisambig = new TermQuery(new Term("DisambigFrom", candidates[i]));
			queryredirect.setBoost((float)23.61);
			BooleanQuery query = new BooleanQuery();
			BooleanQuery.setMaxClauseCount(999999);
			query.add(queryanchor, BooleanClause.Occur.SHOULD);
			query.add(querytitle, BooleanClause.Occur.SHOULD);
			query.add(queryredirect, BooleanClause.Occur.SHOULD);
			query.add(queryinbold, BooleanClause.Occur.SHOULD);
			query.add(querydisambig, BooleanClause.Occur.SHOULD);
			//System.out.println(query.toString());
			Hits hits = searcher.search(query);
			if (hits.length() > 0 && hits.score(0) > threshold) {
				buffer[r] = new Annotation();
				String title = hits.doc(0).get("Title");
				buffer[r].articleTitle = title;
				buffer[r].originalText = candidates[i];
				buffer[r].targetArticleURL = "http://en.wikipedia.org/wiki/"
					+URLEncoder.encode(title.replaceAll(" ", "_"), "UTF-8");
				r++;
				/*
				if (hits.doc(0).get("DisambigFrom") != null)////////////////////
					System.out.println("disambig: " + hits.doc(0).get("DisambigFrom"));///////////////
				*/
			}
		}
		Annotation[] result = new Annotation[r];
		for (int i = 0; i < r; i++) result[i] = buffer[i];
		return result;
	}
	
	public String[] generateCandidates(String[] terms) {
		String[] result = null;
		if (terms.length <= maxPhraseLength)
			result = new String[terms.length*(terms.length-1)/2];
		else
			result = new String[(2*terms.length-maxPhraseLength+1)*maxPhraseLength/2];
		int r = 0;
		for (int i = 1; i <= maxPhraseLength; i++) {
			for (int j = 0; j < terms.length-i; j++) {
				StringBuffer sb = new StringBuffer();
				sb.append(terms[j]);
				for (int k = 1; k < i; k++) sb.append(" "+terms[j+k]);
				result[r++] = sb.toString();
			}
		}
		return result;
	}
	
	public void show(Annotation[] r) {
		System.out.println("original text\trecognized entity\tURL");
		for (int i = 0; i < r.length; i++) {
			System.out.println(r[i].originalText+"\t"+r[i].articleTitle+"\t"+r[i].targetArticleURL);
		}
	}
	
	public boolean containalnum(String s) {
		if (s == null || s.length() == 0) return false;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isLetterOrDigit(s.charAt(i))) return true;
		}
		return false;
	}
}
