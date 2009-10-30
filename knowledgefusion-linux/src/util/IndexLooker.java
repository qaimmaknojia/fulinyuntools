package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import main.Blocker;
import main.Indexer;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;

import basic.IOFactory;

public class IndexLooker {
	
	public static String refIndex = Indexer.indexFolder+"refIndex";
	public static String basicFeatureIndex = Indexer.indexFolder+"basicFeatureIndex";
	
	public static void main(String[] args) throws Exception {
//		lookBasicFeature();
		for (int i = 5000; i <= 50000; i += 5000) {
			batchGenerateRefIndexHtml("extended60miss.txt", i-5000, i, Blocker.workFolder+"extended"+(i-5000)+"-"+i+"/");
		}
		for (int i = 5000; i <= 40000; i += 5000) {
			batchGenerateRefIndexHtml("basic60miss.txt", i-5000, i, Blocker.workFolder+"extended"+(i-5000)+"-"+i+"/");
		}
	}
	
	/**
	 * convert id pairs from tolabel to html files describing entity information
	 * @param tolabel
	 * @param start starting line number
	 * @param end ending line number
	 * @throws Exception
	 */
	public static void batchGenerateRefIndexHtml(String tolabel, int start, int end, String outputFolder) throws Exception {
		File labeldir = new File(outputFolder);
		if (!labeldir.exists() || !labeldir.isDirectory()) labeldir.mkdir(); 
		IndexReader ireader = IndexReader.open(refIndex);
		BufferedReader br = IOFactory.getBufferedReader(tolabel);
		for (int i = 0; i < start; i++) br.readLine();
		for (int i = start; i < end; i++) {
			String line = br.readLine();
			if (line == null) break;
			String[] parts = line.split(" ");
			generateRefIndexHtml(ireader, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), i, outputFolder);
			if ((i+1) % 100 == 0) System.out.println((i+1)+"");
		}
		ireader.close();
	}
	
	/**
	 * generate information about a pair of entities
	 * @param ireader
	 * @param id1
	 * @param id2
	 * @param num
	 * @throws Exception
	 */
	public static void generateRefIndexHtml(IndexReader ireader, int id1, int id2, int num, String outputFolder) throws Exception {
		PrintWriter pw = IOFactory.getPrintWriter(outputFolder + num+".html");
		pw.println("<html><body>");
		if (num > 0) pw.println("<a href=" + (num-1) + ".html>previous</a>");
		pw.println("<a href=" + (num+1) + ".html>next</a>");
		pw.println("<table border=1><tr><td width=50% valign=top>");
		pw.println("<b>" + ireader.document(id1).get("URI").replaceAll("<", "&lt;").replaceAll(">", "&gt;") + 
				"</b>");
		pw.println("<p>");
		List fieldList = ireader.document(id1).getFields();
		for (Object obj : fieldList) {
			Field field = (Field)obj;
			if (!field.name().equals("URI")) 
				pw.println(field.name().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + 
						" : " + field.stringValue().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "<br>");
			
		}
		pw.println("</td><td>");
		pw.println("<b>" + ireader.document(id2).get("URI").replaceAll("<", "&lt;").replaceAll(">", "&gt;") + 
				"</b>");
		pw.println("<p>");
		fieldList = ireader.document(id2).getFields();
		for (Object obj : fieldList) {
			Field field = (Field)obj;
			if (!field.name().equals("URI")) 
				pw.println(field.name().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + 
						" : " + field.stringValue().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "<br>");
			
		}
		pw.println("</td></tr></table>");
		pw.println("</body></html>");
		pw.close();
	}
	
	public static void lookBasicFeature() throws Exception {
		Scanner sc = new Scanner(System.in);
		IndexReader ireader = IndexReader.open(basicFeatureIndex);
		while (true) {
			int n = sc.nextInt();
			System.out.println(n);
			System.out.println(ireader.document(n).get("URI"));
			System.out.println(ireader.document(n).get("basic"));
		}
	}
	
	public static void lookRefIndex() throws Exception {
		Scanner sc = new Scanner(System.in);
		IndexReader ireader = IndexReader.open(refIndex);
		while (true) {
			int n = sc.nextInt();
			System.out.println(n);
			System.out.println(ireader.document(n).get("URI"));
			List fieldList = ireader.document(n).getFields();
			for (Object obj : fieldList) {
				Field field = (Field)obj;
				if (!field.name().equals("URI")) 
					System.out.println(field.name() + " : " + field.stringValue());
			}
		}
	}

}
