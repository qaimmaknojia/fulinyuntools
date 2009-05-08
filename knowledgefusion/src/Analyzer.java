import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import basic.IDataSourceReader;
import basic.IOFactory;


public class Analyzer {

	public static String workFolder = 
//		"\\\\poseidon\\team\\semantic search\\data\\musicbrainz\\Rdf data\\";
		"\\\\poseidon\\team\\semantic search\\BillionTripleData\\gz\\";
	public static final String rdfType = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	public static final String owlClass = "<http://www.w3.org/2002/07/owl#Class>";
	public static final String dbpediaSubject = "<http://www.w3.org/2004/02/skos/core#subject>";
	
	// group the col-th column of input (.gz file) and count the size of each group
	public static void summarize(String input, int col, String output) throws Exception {
		System.out.println("summarize to " + output);
		BufferedReader br = IOFactory.getGzBufferedReader(input);
		HashMap<String, Integer> summaryTable = new HashMap<String, Integer>();
		int count = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			if (parts.length > 2) {
				String toSum = parts[col];
				if (summaryTable.containsKey(toSum)) 
					summaryTable.put(toSum, summaryTable.get(toSum)+1);
				else summaryTable.put(toSum, 1);
			}
			count++;
			if (count % 3000000 == 0) System.out.println(
					new Date().toString() + " : " + count);
		}
		br.close();
		System.out.println(count + " lines in all");
		writeSummaryTable(summaryTable, output);
	}
	
	// count concept sizes, utilizing "rdf:type" & "owl:Class" predicates
	public static void sumConcept(String input, String output) throws Exception {
		System.out.println("sumConcept to " + output);
		BufferedReader br = IOFactory.getGzBufferedReader(input);
		HashMap<String, Integer> summaryTable = new HashMap<String, Integer>();
		int count = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			if (parts.length > 2 && (parts[1].equals(rdfType) || parts[1].equals(owlClass)
					|| parts[1].equals(dbpediaSubject))) {
				if (summaryTable.containsKey(parts[2]))
					summaryTable.put(parts[2], summaryTable.get(parts[2])+1);
				else summaryTable.put(parts[2], 1);
			}
			count++;
			if (count % 3000000 == 0) System.out.println(
					new Date().toString() + " : " + count);
		}
		br.close();
		System.out.println(count + " lines in all");
		writeSummaryTable(summaryTable, output);
	}
	
	private static void writeSummaryTable(HashMap<String, Integer> summaryTable, 
			String output) throws Exception {
		PrintWriter pw = IOFactory.getPrintWriter(output); // to ensure the encoding is utf-8
		for (String key : summaryTable.keySet()) {
			pw.println(key + " " + summaryTable.get(key));
		}
		pw.close();
	}

	// count attribute sizes, an attribute is indicated by a quotation mark at the beginning of 
	// the third part of a triple
	public static void sumAttribute(String input, String output) throws Exception {
		System.out.println("sumAttribute to " + output);
		BufferedReader br = IOFactory.getGzBufferedReader(input);
		HashMap<String, Integer> summaryTable = new HashMap<String, Integer>();
		int count = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			if (parts.length > 2 && parts[2].startsWith("\"")) {
				if (summaryTable.containsKey(parts[1]))
					summaryTable.put(parts[1], summaryTable.get(parts[1])+1);
				else summaryTable.put(parts[1], 1);
			}
			count++;
			if (count % 3000000 == 0) System.out.println(
					new Date().toString() + " : " + count);
		}
		br.close();
		System.out.println(count + " lines in all");
		writeSummaryTable(summaryTable, output);
	}
	
	// delete lines in file2 from file1, and write to result
	public static void diff(String file1, String file2, String result) throws Exception {
		System.out.println("diff to " + result);
		HashSet<String> lines = new HashSet<String>();
		IDataSourceReader idr = IOFactory.getReader(file1); // to ensure the encoding is utf-8
		for (String line = idr.readLine(); line != null; line = idr.readLine()) lines.add(line);
		idr.close();
		idr = IOFactory.getReader(file2);
		for (String line = idr.readLine(); line != null; line = idr.readLine()) lines.remove(line);
		idr.close();
		PrintWriter pw = IOFactory.getPrintWriter(result); // to ensure the encoding is utf-8
		for (String s : lines) pw.println(s);
		pw.close();
	}
	
	// find lines in gz input file containing keyword
	public static void find(String input, String keyword) throws Exception {
		String[] lines = new String[]{"", "", "", "", ""};
		BufferedReader br = IOFactory.getGzBufferedReader(input);
		int count = 0;
		int hit = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			for (int i = 0; i < 4; i++) lines[i] = lines[i+1];
			lines[4] = line;
			if (lines[2].contains(keyword)) {
				System.out.println();
				for (String s : lines) System.out.println(s);
//				System.out.println();
				hit++;
				if (hit%10 == 0) {
					System.out.println("press <ENTER> to continue...");
					System.in.read();
				}
			}
			count++;
			if (count%3000000 == 0) System.out.println(count);
		}
		br.close();
	}
	
	// sort the lines in filename according to the values in the col-th column, and write the 
	// result to output
	public static void sort(String filename, int col, String output) throws Exception {
		TreeMap<Integer, ArrayList<String>> sorted = new TreeMap<Integer, ArrayList<String>>();
		IDataSourceReader br = IOFactory.getReader(filename); // to ensure the encoding is utf-8
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			int key = Integer.parseInt(parts[col]);
			if (sorted.containsKey(key)) sorted.get(key).add(line);
			else {
				ArrayList<String> value = new ArrayList<String>();
				value.add(line);
				sorted.put(key, value);
			}
		}
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (Integer key : sorted.keySet()) for (String line : sorted.get(key)) pw.println(line);
		pw.close();
	}
	
	public static void main(String[] args) throws Exception {
		String wordnet = "wordnet.gz", dblp = "dblp.gz", dbpedia = "dbpedia.gz", 
		geonames = "geonames.gz", uscensus = "uscensus.gz", foaf = "foaf.gz";
		
		summarize(workFolder+foaf, 1, workFolder + "foaf.property.txt");
		sumConcept(workFolder+foaf, workFolder + "foaf.concept.txt");
		sumAttribute(workFolder+foaf, workFolder + "foaf.attribute.txt");
		diff(workFolder + "foaf.property.txt", workFolder + "foaf.attribute.txt", 
		workFolder + "foaf.relation.txt");

		summarize(workFolder+wordnet, 1, workFolder + "wordnet.property.txt");
		sumConcept(workFolder+wordnet, workFolder + "wordnet.concept.txt");
		sumAttribute(workFolder+wordnet, workFolder + "wordnet.attribute.txt");
		diff(workFolder + "wordnet.property.txt", workFolder + "wordnet.attribute.txt", 
		workFolder + "wordnet.relation.txt");

		summarize(workFolder+dbpedia, 1, workFolder + "dbpedia.property.txt");
		sumConcept(workFolder+dbpedia, workFolder + "dbpedia.concept.txt");
		sumAttribute(workFolder+dbpedia, workFolder + "dbpedia.attribute.txt");
		diff(workFolder + "dbpedia.property.txt", workFolder + "dbpedia.attribute.txt", 
		workFolder + "dbpedia.relation.txt");

		summarize(workFolder+dblp, 1, workFolder + "dblp.property.txt");
		sumConcept(workFolder+dblp, workFolder + "dblp.concept.txt");
		sumAttribute(workFolder+dblp, workFolder + "dblp.attribute.txt");
		diff(workFolder + "dblp.property.txt", workFolder + "dblp.attribute.txt", 
		workFolder + "dblp.relation.txt");

		summarize(workFolder+geonames, 1, workFolder + "geonames.property.txt");
		sumConcept(workFolder+geonames, workFolder + "geonames.concept.txt");
		sumAttribute(workFolder+geonames, workFolder + "geonames.attribute.txt");
		diff(workFolder + "geonames.property.txt", workFolder + "geonames.attribute.txt", 
		workFolder + "geonames.relation.txt");

		summarize(workFolder+uscensus, 1, workFolder + "uscensus.property.txt");
		sumConcept(workFolder+uscensus, workFolder + "uscensus.concept.txt");
		sumAttribute(workFolder+uscensus, workFolder + "uscensus.attribute.txt");
		diff(workFolder + "uscensus.property.txt", workFolder + "uscensus.attribute.txt", 
		workFolder + "uscensus.relation.txt");
		
//		String dbpediaPredicates = workFolder + "dbpedia.property.txt"; // to run
//		sort(dbpediaPredicates, 1, workFolder+"dbpedia.property.sorted.txt"); // to run
		
	}
}

