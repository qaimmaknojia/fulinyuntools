import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import basic.GzReader;
import basic.IDataSourceReader;
import basic.TarGzReader;
import basic.WarcReader;


public class Analyzer {

	public static String workFolder = 
//		"\\\\poseidon\\team\\semantic search\\data\\musicbrainz\\Rdf data\\";
		"\\\\poseidon\\team\\semantic search\\BillionTripleData\\";
	public static final String rdfType = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	
	// group the col-th column of input and count the size of each group
	public static void summarize(IDataSourceReader input, int col, String output) {
		System.out.println("summarize to " + output);
		HashMap<String, Integer> summaryTable = new HashMap<String, Integer>();
		int count = 0;
		try {
			for (String line = input.readLine(); line != null; line = input.readLine()) {
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
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(count + " lines in all");
		try {
			writeSummaryTable(summaryTable, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// count concept sizes, utilizing "rdf:type" predicates
	public static void sumConcept(IDataSourceReader input, String output) {
		System.out.println("sumConcept to " + output);
		HashMap<String, Integer> summaryTable = new HashMap<String, Integer>();
		int count = 0;
		try {
			for (String line = input.readLine(); line != null; line = input.readLine()) {
				String[] parts = line.split(" ");
				if (parts.length > 2 && parts[1].equals(rdfType)) {
					if (summaryTable.containsKey(parts[2]))
						summaryTable.put(parts[2], summaryTable.get(parts[2])+1);
					else summaryTable.put(parts[2], 1);
				}
				count++;
				if (count % 3000000 == 0) System.out.println(
						new Date().toString() + " : " + count);
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(count + " lines in all");
		try {
			writeSummaryTable(summaryTable, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void writeSummaryTable(HashMap<String, Integer> summaryTable, 
			String output) throws Exception {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(output)));
		for (String key : summaryTable.keySet()) {
			pw.println(key + " " + summaryTable.get(key));
		}
		pw.close();
	}

	// count attribute sizes, an attribute is indicated by a quotation mark at the beginning of 
	// the third part of a triple
	public static void sumAttribute(IDataSourceReader input, String output) {
		System.out.println("sumAttribute to " + output);
		HashMap<String, Integer> summaryTable = new HashMap<String, Integer>();
		int count = 0;
		try {
			for (String line = input.readLine(); line != null; line = input.readLine()) {
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
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(count + " lines in all");
		try {
			writeSummaryTable(summaryTable, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// delete lines in file2 from file1, and write to result
	public static void diff(String file1, String file2, String result) throws Exception {
		System.out.println("diff to " + result);
		HashSet<String> lines = new HashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(file1));
		for (String line = br.readLine(); line != null; line = br.readLine()) lines.add(line);
		br.close();
		br = new BufferedReader(new FileReader(file2));
		for (String line = br.readLine(); line != null; line = br.readLine()) lines.remove(line);
		br.close();
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(result)));
		for (String s : lines) pw.println(s);
		pw.close();
	}
	
	public static void main(String[] args) throws Exception {
//		summarize(RDF2NTRIPLE.relation, 1, workfolder + "relationSize");
//		find(RDF2NTRIPLE.relation, 1, "rdf:li");
//		summarize(new TarGzReader(workFolder + "dbpedia-v3.nt.tar.gz"), 1, 
//				workFolder + "dbpedia.property.txt");
//		sumConcept(new TarGzReader(workFolder + "dbpedia-v3.nt.tar.gz"), 
//				workFolder + "dbpedia.concept.txt");
//		sumAttribute(new TarGzReader(workFolder + "dbpedia-v3.nt.tar.gz"), 
//				workFolder + "dbpedia.attribute.txt");
//		
//		summarize(new GzReader(workFolder + "swetodblp_noblank.gz"), 1, 
//				workFolder + "dblp.property.txt");
//		sumConcept(new GzReader(workFolder + "swetodblp_noblank.gz"), 
//				workFolder + "dblp.concept.txt");
//		sumAttribute(new GzReader(workFolder + "swetodblp_noblank.gz"), 
//				workFolder + "dblp.attribute.txt");
//		
//		summarize(new TarGzReader(workFolder + "uscensus.nt.tar.gz"), 1, 
//				workFolder + "uscensus.property.txt");
//		sumConcept(new TarGzReader(workFolder + "uscensus.nt.tar.gz"),  
//				workFolder + "uscensus.concept.txt");
//		sumAttribute(new TarGzReader(workFolder + "uscensus.nt.tar.gz"),  
//				workFolder + "uscensus.attribute.txt");
//		
//		summarize(new WarcReader(workFolder + "geonames.warc"), 1, 
//				workFolder + "geonames.property.txt");
//		sumConcept(new WarcReader(workFolder + "geonames.warc"),  
//				workFolder + "geonames.concept.txt");
//		sumAttribute(new WarcReader(workFolder + "geonames.warc"),  
//				workFolder + "geonames.attribute.txt");
		
//		diff(workFolder + "dbpedia.property.txt", workFolder + "dbpedia.attribute.txt", 
//				workFolder + "dbpedia.relation.txt");
		diff(workFolder + "dblp.property.txt", workFolder + "dblp.attribute.txt", 
				workFolder + "dblp.relation.txt");
		diff(workFolder + "uscensus.property.txt", workFolder + "uscensus.attribute.txt", 
				workFolder + "uscensus.relation.txt");
//		diff(workFolder + "geonames.property.txt", workFolder + "geonames.attribute.txt", 
//				workFolder + "geonames.relation.txt");

	}
}

// find lines with the col-th column containing contain
//public static void find(String inputgz, int col, String contain) throws Exception {
//	BufferedReader br = RDF2NTRIPLE.getGzBufferedReader(inputgz);
//	int count = 0;
//	for (String line = br.readLine(); line != null; line = br.readLine()) {
//		String toCheck = line.split(" ")[col];
//		if (toCheck.contains(contain)) {
//			System.out.println(line);
//			count++;
//			if (count == 10) break;
//		}
//	}
//	br.close();
//}

