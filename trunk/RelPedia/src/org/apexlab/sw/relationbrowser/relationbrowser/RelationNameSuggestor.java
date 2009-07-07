package org.apexlab.sw.relationbrowser.relationbrowser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;


public class RelationNameSuggestor {
	
	public static String indexFold = "E:\\DBPediaData1\\RelationNameIndex";
	public static IndexSearcher searcher = null;
	public static int suggestListLength = 20;
	public static int minInputLength = 3;
	
	public static String[] suggest(String input) throws Exception {
		if (input.length() < minInputLength) return new String[0];
		input = trim(input);
		TermQuery tq = new TermQuery(new Term("snippet", input.toLowerCase()));
		if (searcher == null) searcher = new IndexSearcher(indexFold);
		Hits h = searcher.search(tq);
		String[] buffer = new String[suggestListLength];
		int c = 0;
		for (int i = 0; i < h.length() && i < suggestListLength; i++) {
			buffer[i] = h.doc(i).get("name");
			c++;
		}
		String[] ret = new String[c];
		for (int i = 0; i < c; i++) ret[i] = buffer[i];
		return ret;
	}
	
	public static String trim(String s) {
		String[] ts = s.split(" ");
		StringBuffer sb = new StringBuffer();
		sb.append(ts[0]);
		for (int i = 1; i < ts.length; i++) sb.append(ts[i]);
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String query = br.readLine();
		while (true) {
			String[] sug = suggest(query);
			for (int i = 0; i < sug.length; i++) System.out.println(sug[i]);
			query = br.readLine();
		}
	}
}
