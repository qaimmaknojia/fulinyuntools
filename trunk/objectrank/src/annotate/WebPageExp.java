package annotate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

public class WebPageExp {
	
	public static String preAddPre = "<b style=\"color:black;background-color:#";
	public static int color = 0xffff44;
	public static int diff = 0x11;
	public static String preAddPost = "\">";
	public static String postAdd = "</b>";
	
//	public static String linkRecomIdxFold = "E:\\NER\\LinkSuggestIndex_v4";	
	public static String linkRecomIdxFold = "E:\\NamedEntityAnnotatorIndex\\LinkSuggestIndex_v4";
	
//	public static String projectFolder = "E:\\eclipse_workspace\\objectrank\\";
	public static String projectFolder = "E:\\users\\fulinyun\\objectrank\\";
		
	public static double threshold = 0.5;
	public static int maxPhraseLength = 6;
	public static Annotation[] annotationBuffer = new Annotation[999999];
	public static int numAnnotation = 0;
	public static String outputFileName = "E:\\NamedEntityAnnotatorTest\\output.html";
	public static HashSet<String> stopwords = new HashSet<String>();
	public static String stopwordsFile = projectFolder+"info\\stopwords.txt";
	
	static {
		try {
			loadStopWords();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadStopWords() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(stopwordsFile));
		for (String line = br.readLine(); line != null; line = br.readLine()) stopwords.add(line);
	}
	
	public static void main(String[] args) throws Exception {
//		Date start = new Date();
//		WebPageExp exp = new WebPageExp();
//		stopwords.add("-");
//		stopwords.add("&nbsp;");
//		stopwords.add("a");
//		stopwords.add("the");
//		stopwords.add("that");
//		stopwords.add("will");
//		stopwords.add("you");
//		stopwords.add("use");
//		stopwords.add("for");
//		StringBuffer sb = new StringBuffer();
//		Searcher searcher = new IndexSearcher(linkRecomIdxFold);
//		exp.annotate("http://www.microsoft.com/windowsmobile/smallbiz/default.mspx", sb, searcher);
//		//		System.out.println(sb.toString());
//		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFileName)));
//		bw.write(sb.toString());
//		bw.close();
//		Date end = new Date();
//		System.out.println("total processing time: " + (end.getTime() - start.getTime())
//				+ " milliseconds.");
		String text = highlight("http://en.wikipedia.org/wiki/Cognitive_science", new String[]{"cognitive science", "Artificial intelligence", 
				"Neuroscience", "James McClelland", "Psychology", "Philosophy", "Memory", "Brain", "Linguistics", "Noam Chomsky"});
		PrintWriter pw = new PrintWriter(new FileWriter("E:\\test.html"));
		pw.println(text);
		pw.close();
	}

//	public static String getStream(String str) {
//		Date start = new Date();
//		WebPageExp exp = new WebPageExp();
//		stopwords.add("-");
//		stopwords.add("&nbsp;");
//		stopwords.add("a");
//		stopwords.add("the");
//		stopwords.add("that");
//		stopwords.add("will");
//		stopwords.add("you");
//		stopwords.add("use");
//		stopwords.add("for");
//		StringBuffer sb = new StringBuffer();
//		try {
//			Searcher searcher = new IndexSearcher(linkRecomIdxFold);
//			exp.annotate(str, sb, searcher);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//		System.out.println(sb.toString());
//		///BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFileName)));
//		//bw.write(sb.toString());
//		//bw.close();
//		Date end = new Date();
//		System.out.println("total processing time: " + (end.getTime() - start.getTime())
//				+ " milliseconds.");
//		return sb.toString();
//
//	}

	public static String highlight(String url, String[] entities) throws Exception {
		System.out.println("top 10 entities:");
		for (String s : entities) System.out.println(s);
		Parser parser = new Parser(url);
		parser.setEncoding("utf-8");
		StringBuilder sb = new StringBuilder();
		for (NodeIterator i = parser.elements(); i.hasMoreNodes(); ) {
			Node n = i.nextNode();
			if (n instanceof TextNode) {
				String text = n.getText();
				for (int j = 0; j < entities.length; j++) text = replaceAllIgnoreCase(text, entities[j], j);
				sb.append(text);
			} else if (n instanceof TagNode){
				TagNode tag = (TagNode) n;
				String tn = tag.getRawTagName();
				if (tn.equalsIgnoreCase("title") || tn.equalsIgnoreCase("head")
						|| tn.equalsIgnoreCase("script") || tn.equalsIgnoreCase("img")
						|| tn.startsWith("![")) {
					sb.append(n.toHtml());
				} else {
					if (n.getChildren() == null) sb.append(n.toHtml());
					else {
						sb.append(getStartHtml(n));
						NodeList nl = n.getChildren();
						if (nl != null) for (NodeIterator ni = nl.elements(); ni.hasMoreNodes(); ) sb.append(highlight(ni.nextNode(), entities));
						sb.append(getEndHtml(n));
					}
				}
			} else {
				sb.append(n.toHtml());
			}
		}
		return sb.toString();
	}
	
	public static String highlight(Node n, String[] entities) throws Exception {
		if (n instanceof TextNode) {
			String text = n.getText();
			for (int i = 0; i < entities.length; i++) text = replaceAllIgnoreCase(text, entities[i], i);
			return text;
		} else if (n instanceof TagNode) {
			TagNode tag = (TagNode) n;
			String tn = tag.getRawTagName();
			if (tn.equalsIgnoreCase("title") || tn.equalsIgnoreCase("head")
					|| tn.equalsIgnoreCase("script") || tn.equalsIgnoreCase("img")
					|| tn.startsWith("![")) {
				return n.toHtml();
			} else {
				if (n.getChildren() == null) return n.toHtml();
				StringBuilder sb = new StringBuilder();
				sb.append(getStartHtml(n));
				NodeList nl = n.getChildren();
				if (nl != null) for (NodeIterator ni = nl.elements(); ni.hasMoreNodes(); ) sb.append(highlight(ni.nextNode(), entities));
				sb.append(getEndHtml(n));
				return sb.toString();
			}
		} else {
			return n.toHtml();
		}
	}
	
	public static String replaceAllIgnoreCase(String text, String str, int rank) {
//		System.out.println("replaceAllIgnoreCase(" + text + ", " + str + ")");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			sb.append("(" + Character.toLowerCase(c) + "|" + Character.toUpperCase(c) + ")");
		}
		Pattern p = Pattern.compile(sb.toString());
		ArrayList<Integer> replaceList = new ArrayList<Integer>();
		for (int i = 0; i < text.length()-str.length()+1; i++) {
			Matcher m = p.matcher(text.substring(i, i+str.length()));
			if (m.matches()) {
				replaceList.add(i);
//				System.out.println(i + " : " + text.substring(i, i+str.length()));
			}
		}
		StringBuilder sb1 = new StringBuilder();
		int start = 0;
		for (Integer i : replaceList) {
			if (i > start){
				sb1.append(text.substring(start, i));
				sb1.append(preAddPre + Integer.toHexString(color+rank*diff) + preAddPost + text.substring(i, i+str.length())+postAdd);
				start = i+str.length();
			} else {
				sb1.append(preAddPre + Integer.toHexString(color+rank*diff) + preAddPost + text.substring(i, i+str.length())+postAdd);
				start = i+str.length();
			}
//			System.out.println(i + " : " + sb1.toString());
		}
		sb1.append(text.substring(start));
		return sb1.toString();
	}
	
	public static String getStartHtml(Node n) {
		String raw = n.toHtml();
		int pos = raw.indexOf('>')+1;
		return raw.substring(0, pos);
	}
	
	public static String getEndHtml(Node n) {
		String raw = n.toHtml();
		int pos = raw.lastIndexOf('<');
		return raw.substring(pos);
	}
	
	public static String[] recognize(String url) {

		WebPageExp exp = new WebPageExp();
		Searcher searcher = null;
		try {
			searcher = new IndexSearcher(linkRecomIdxFold);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Annotation> ret = null;
		try {
			ret = exp.recognize(url, searcher);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] retStr = new String[ret.size()];
		for (int i = 0; i < retStr.length; i++) retStr[i] = ret.get(i).articleTitle;
		return retStr;
	}
	
	public ArrayList<Annotation> recognize(String url, Searcher searcher) throws Exception {
		Parser parser = new Parser(url);
		parser.setEncoding("utf-8");
		ArrayList<Annotation> ret = new ArrayList<Annotation>();
		for (NodeIterator i = parser.elements(); i.hasMoreNodes(); ) 
			ret.addAll(recognize(i.nextNode(), searcher));
		return ret;
	}

	public ArrayList<Annotation> recognize(Node node, Searcher searcher) throws Exception {
		ArrayList<Annotation> ret = new ArrayList<Annotation>();
		if (node instanceof TextNode) {
			TextNode text = (TextNode) node;
			ret.addAll(recognizeText(text.getText(), searcher));
		} else if (node instanceof RemarkNode) {
			
		} else if (node instanceof TagNode) {
			TagNode tag = (TagNode) node;
			String tn = tag.getRawTagName();
			if (tn.equalsIgnoreCase("title") || tn.equalsIgnoreCase("head")
					|| tn.equalsIgnoreCase("script") || tn.equalsIgnoreCase("img")
					|| tn.startsWith("![")) {
				
			} else {
				NodeList nl = tag.getChildren();
				if (null != nl)
					for (NodeIterator i = nl.elements(); i.hasMoreNodes();)
						ret.addAll(recognize(i.nextNode(), searcher));
			}
		}
		return ret;
	}

	public ArrayList<Annotation> recognizeText(String text, Searcher searcher) throws Exception {
		ArrayList<Annotation> ret = new ArrayList<Annotation>();
		if (!containalnum(text)) return ret;
		String[] terms = text.trim().split(" ");
		boolean[] annotated = new boolean[terms.length];
		Arrays.fill(annotated, false);
		for (int i = maxPhraseLength; i >= 1; i--)
			recognizeOneLength(terms, ret, annotated, i, searcher);
		return ret;
	}
	
	public void recognizeOneLength(String[] terms, ArrayList<Annotation> annotations,
			boolean[] annotated, int len, Searcher searcher) throws Exception {
		if (len > terms.length) return;
		for (int i = 0; i <= terms.length - len; i++) {
			if (annotated[i + len - 1]) {
				for (i = i + len; i < annotated.length && annotated[i]; i++)
					;
				i--;
				continue;
			}
			StringBuffer sb = new StringBuffer();
			sb.append(terms[i]);
			for (int j = 1; j < len; j++)
				sb.append(" " + terms[i + j]);
			//String query = sb.toString();/////////////
			Annotation a = getAnnotation(sb.toString(), searcher);
			if (a != null && !stopwords.contains(a.originalText.toLowerCase())) {
				a.start = i;
				a.len = len;
				for (int j = i; j < i + len; j++)
					annotated[j] = true;
				annotations.add(a);
				i = i + len - 1;
			}
		}
	}
	
//	public void annotate(String fin, StringBuffer sb, Searcher searcher) throws Exception {
//		Parser parser = new Parser(fin);
//		for (NodeIterator i = parser.elements(); i.hasMoreNodes();)
//			annotate(i.nextNode(), sb, searcher);
//	}

//	public void annotate(Node node, StringBuffer sb, Searcher searcher) throws Exception {
//		if (node instanceof TextNode) {
//			TextNode text = (TextNode) node;
//			sb.append(annotate(text.getText(), searcher));
//		} else if (node instanceof RemarkNode) {
//			RemarkNode remark = (RemarkNode) node;
//			sb.append(remark.toHtml());
//		} else if (node instanceof TagNode) {
//			TagNode tag = (TagNode) node;
//			String tn = tag.getRawTagName();
//			if (tn.equalsIgnoreCase("title") || tn.equalsIgnoreCase("head")
//					|| tn.equalsIgnoreCase("script") || tn.equalsIgnoreCase("img")
//					|| tn.equalsIgnoreCase("a") || tn.startsWith("![")) {
//				sb.append(tag.toHtml());
//			} else {
//				StringBuffer sb1 = new StringBuffer();
//				Vector att = tag.getAttributesEx();
//				Iterator e = att.iterator();
//				while (e.hasNext()) {
//					Attribute key = (Attribute) e.next();
//					if (key.getName() != null && !key.getName().equalsIgnoreCase("$<TAGNAME>$")) {
//						sb1.append(" " + key.getName().toLowerCase() + "=" + "\"" + key.getValue()
//								+ "\"");
//					}
//				}
//				sb.append("<" + tn + sb1.toString() + ">");
//				NodeList nl = tag.getChildren();
//				if (null != nl)
//					for (NodeIterator i = nl.elements(); i.hasMoreNodes();)
//						annotate(i.nextNode(), sb, searcher);
//				sb.append("</" + tn + ">");
//			}
//		}
//	}

//	// find all annotations in the text and insert hrefs and font titles
//	public String annotate(String text, Searcher searcher) throws Exception {
//		/*
//		if (text.contains("Windows Mobile")) {
//			text = text+"";
//		}
//		 */
//		if (!containalnum(text))
//			return text;
//		String[] terms = text.trim().split(" ");
//		LinkedList<Annotation> annotations = new LinkedList<Annotation>();
//		boolean[] annotated = new boolean[terms.length];
//		for (int i = 0; i < annotated.length; i++)
//			annotated[i] = false;
//		for (int i = maxPhraseLength; i >= 1; i--)
//			annotateOneLength(terms, annotations, annotated, i, searcher);
//		Annotation[] annotationArray = annotations.toArray(new Annotation[0]);
//		Arrays.sort(annotationArray, 0, annotationArray.length, new Comparator<Annotation>() {
//			public int compare(Annotation a, Annotation b) {
//				if (a.start < b.start)
//					return -1;
//				if (a.start > b.start)
//					return 1;
//				return 0;
//			}
//		});
//		StringBuffer sb = new StringBuffer();
//		if (annotationArray.length == 0)
//			return text;
//		for (int i = 0, j = 0; i < terms.length; i++) {
//			if (j == annotationArray.length) {
//				sb.append(" " + terms[i]);
//				continue;
//			}
//			if (i < annotationArray[j].start) {
//				if (i == 0)
//					sb.append(terms[i]);
//				else
//					sb.append(" " + terms[i]);
//			} else if (i == annotationArray[j].start) {
//				sb.append(" <font style=\"background-color: #FFFF00\"><a href=\""
//						+ annotationArray[j].targetArticleURL + "\" title=\""
//						+ annotationArray[j].articleTitle + "\" target=\"_blank\">" + terms[i]);
//				if (i == annotationArray[j].start + annotationArray[j].len - 1) {
//					sb.append("</a></font>");
//					if (j < annotationArray.length)
//						j++;
//				}
//			} else if (i > annotationArray[j].start
//					&& i < annotationArray[j].start + annotationArray[j].len) {
//				sb.append(" " + terms[i]);
//				if (i == annotationArray[j].start + annotationArray[j].len - 1) {
//					sb.append("</a></font>");
//					if (j < annotationArray.length)
//						j++;
//				}
//			} else {
//				sb.append(" " + terms[i]);
//			}
//		}
//		return sb.toString();
//	}

//	// recognize all entity names with length len
//	public void annotateOneLength(String[] terms, LinkedList<Annotation> annotations,
//			boolean[] annotated, int len, Searcher searcher) throws Exception {
//		if (len > terms.length)
//			return;
//		for (int i = 0; i <= terms.length - len; i++) {
//			if (annotated[i + len - 1]) {
//				for (i = i + len; i < annotated.length && annotated[i]; i++)
//					;
//				i--;
//				continue;
//			}
//			StringBuffer sb = new StringBuffer();
//			sb.append(terms[i]);
//			for (int j = 1; j < len; j++)
//				sb.append(" " + terms[i + j]);
//			//String query = sb.toString();/////////////
//			Annotation a = getAnnotation(sb.toString(), searcher);
//			if (a != null && !stopwords.contains(a.originalText)) {
//				a.start = i;
//				a.len = len;
//				for (int j = i; j < i + len; j++)
//					annotated[j] = true;
//				annotations.addLast(a);
//				i = i + len - 1;
//			}
//		}
//	}

	// get possible annotation with a query
	public Annotation getAnnotation(String q, Searcher searcher) throws Exception {
		//		TermQuery queryanchor = new TermQuery(new Term("Anchor", q.toLowerCase()));
		//		queryanchor.setBoost((float)1.03);
		TermQuery querytitle = new TermQuery(new Term("Title", q.toLowerCase()));
		querytitle.setBoost((float) 23.61);
		//		TermQuery queryredirect = new TermQuery(new Term("RedirectFrom", q.toLowerCase()));
		//		queryredirect.setBoost((float)23.61);
		//		TermQuery queryinbold = new TermQuery(new Term("Inbold", q));
		//		queryinbold.setBoost((float)22.92);
		//		TermQuery querydisambig = new TermQuery(new Term("DisambigFrom", q));
		//		queryredirect.setBoost((float)23.61);
		BooleanQuery query = new BooleanQuery();
		BooleanQuery.setMaxClauseCount(999999);
		//		query.add(queryanchor, BooleanClause.Occur.SHOULD);
		query.add(querytitle, BooleanClause.Occur.SHOULD);
		//		query.add(queryredirect, BooleanClause.Occur.SHOULD);
		//		query.add(queryinbold, BooleanClause.Occur.SHOULD);
		//		query.add(querydisambig, BooleanClause.Occur.SHOULD);
		//		if (q == "Windows Mobile") {
		//			q = q+"";
		//		}
		//		TermQuery query = new TermQuery(new Term("Title", q.toLowerCase()));
		Hits hits = searcher.search(query);
		Annotation result = null;
		if (hits.length() > 0 && hits.score(0) > threshold && (hits.doc(0).get("Title").equals(q)
		/*|| hits.doc(0).get("Anchor") != null && hits.doc(0).get("Anchor").equals(q)
		|| hits.doc(0).get("RedirectFrom") != null && hits.doc(0).get("RedirectFrom").equals(q)*/)) {
			result = new Annotation();
			String title = hits.doc(0).get("Title");
			result.articleTitle = title;
			result.originalText = q;
			result.targetArticleURL = "http://en.wikipedia.org/wiki/"
					+ URLEncoder.encode(title.replaceAll(" ", "_"), "UTF-8");
		}
		return result;
	}

	public boolean containalnum(String s) {
		if (s == null || s.length() == 0)
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isLetterOrDigit(s.charAt(i)))
				return true;
		}
		return false;
	}
}

//<font style="background-color: #FFFF00"><a href="index.html" title="this is so cool" target="_blank">Click Here For Hypergurl</a></font>
