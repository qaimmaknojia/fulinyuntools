package org.apexlab.sw.relationbrowser.relationbrowser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;

public class Translator {

	public static String resourceID = "E:\\DBpediaData1\\resourceID";
	public static String categoryID = "E:\\DBpediaData1\\categoryID";
	public static String datatypeID = "E:\\DBpediaData1\\datatypeID";
	public static String relationNameIndexFold = "E:\\DBpediaData1\\RelationNameIndex";
	public static int datatypeOffset = 200000;
	
	public static HashMap<Integer, String> resID2name = null;
	public static HashMap<Integer, String> catID2name = null;
	public static IndexSearcher is = null;
	
	public static void init() throws Exception {
		resID2name = new HashMap<Integer, String>();
		BufferedReader br = new BufferedReader(new FileReader(resourceID));
		int id = 0;
		for (String line = br.readLine(); line != null; line = br.readLine(), id++) {
			String[] data = line.split("\t");
			if (data.length > 1 && data[1] != null) resID2name.put(id, data[1]);
		}
		br.close();
		
		catID2name = new HashMap<Integer, String>();
		br = new BufferedReader(new FileReader(categoryID));
		id = 0;
		for (String line = br.readLine(); line != null; line = br.readLine(), id++) {
			String[] data = line.split("\t");
			catID2name.put(id, data[1]);
		}
		br.close();
		br = new BufferedReader(new FileReader(datatypeID));
		id = 200000;
		for (String line = br.readLine(); line != null; line = br.readLine(), id++) {
			String[] data = line.split("\t");
			catID2name.put(id, data[1]);
		}
		br.close();
		is = new IndexSearcher(relationNameIndexFold);
	}
	
	public static String getRelationName(int id) throws Exception {
		if (is == null) init();
		TermQuery tq = new TermQuery(new Term("id", id+""));
		Hits hits = is.search(tq);
		return hits.doc(0).get("name");
	}
	
	public static String getResName(int id) throws Exception {
		if (resID2name == null) init();
		if (resID2name.containsKey(id)) return resID2name.get(id);
		else {
			System.err.println("can not find resource ID: " + id);
			return "";
		}
	}
	
	public static String getCatName(int id) throws Exception {
		if (catID2name == null) init();
		if (catID2name.containsKey(id)) return catID2name.get(id);
		else {
			System.err.println("can not find category ID: " + id);
			return "";
		}
	}
	
	public static int getRelationID(String name) throws Exception {
		if (is == null) init();
		TermQuery tq = new TermQuery(new Term("name", name));
		Hits hits = is.search(tq);
		if (hits.length() == 0) {
			System.err.println("relation name not found: " + name);
			return -1;
		}
		if (hits.length() > 1) {
			System.err.println("relation name not unique: " + name);
		}
		int ret = -1;
		try {
			ret = Integer.parseInt(hits.doc(0).get("id"));
		} catch (Exception e) {
			System.err.println("abnormal relation id for: " + name);
		}
		return ret;
	}
}
