package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;

public class Common {

	public static final String gzFolder = "\\\\poseidon\\team\\semantic search\\BillionTripleData\\gz\\";
	public static final String wordnet = gzFolder+"wordnet.gz"; // 1942887 triples
	public static final String dblp = gzFolder+"dblp.gz"; // 14936600 triples
	public static final String dbpedia = gzFolder+"dbpedia.gz"; // 110241463 triples
	public static final String geonames = gzFolder+"geonames.gz"; // 69778255 triples
	public static final String uscensus = gzFolder+"uscensus.gz"; // 445752172 triples
	public static final String foaf = gzFolder+"foaf.gz"; // 54000000 triples
	public static final String mbi = gzFolder+"mb.instance.gz"; // 8772612 triples
	public static final String mbr = gzFolder+"mb.relation.gz"; // 13591684 triples
	public static final String mba = gzFolder+"mb.attribute.gz"; // 16721842 triples
	
	public static final String rdfType = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	public static final String owlClass = "<http://www.w3.org/2002/07/owl#Class>";
	public static final String dbpediaSubject = "<http://www.w3.org/2004/02/skos/core#subject>";
	public static final String sameAs = "<http://www.w3.org/2002/07/owl#sameAs>";
	public static final String dbpediaSubclass = "<http://www.w3.org/2004/02/skos/core#broader>";
	
	/**
	 * get integer set from input, each line in input is an integer
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static HashSet<Integer> getIntSet(String input) throws Exception {
		HashSet<Integer> ret = new HashSet<Integer>();
		BufferedReader br = new BufferedReader(new FileReader(input));
		for (String line = br.readLine(); line != null; line = br.readLine()) 
			ret.add(Integer.parseInt(line));
		return ret;
	}

	/**
	 * get line set of input
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static HashSet<String> getStringSet(String input) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(input));
		HashSet<String> ret = new HashSet<String>();
		for (String line = br.readLine(); line != null; line = br.readLine()) ret.add(line);
		return ret;
	}

	/**
	 * get integers from a string, neighboring integers are separated with a whitespace
	 * result array are sorted in ascending order
	 * @param line
	 * @return
	 */
	public static int[] getNumsInLineSorted(String line) {
		String[] parts = line.split(" ");
		int[] ret = new int[parts.length];
		for (int i = 0; i < parts.length; i++) ret[i] = Integer.parseInt(parts[i]);
		Arrays.sort(ret);
		return ret;
	}

	/**
	 * print precision/recall result
	 * @param overlap
	 * @param stdAns
	 * @param canSize
	 * @throws Exception
	 */
	public static void printResult(int overlap, String stdAns, int canSize) throws Exception {
		System.out.println(overlap + " lines overlap");
		int stdSize = Analyzer.countLines(stdAns);
		System.out.println("standard answer size: " + stdSize);
		System.out.println("recall: " + (overlap+0.0)/stdSize);
		System.out.println("result size: " + canSize);
		System.out.println("precision: " + (overlap+0.0)/canSize);
	}

}
