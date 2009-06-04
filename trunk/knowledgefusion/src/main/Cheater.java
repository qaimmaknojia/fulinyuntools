package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;

import basic.IDataSourceReader;
import basic.IOFactory;

public class Cheater {

	public static String dbpediaSameAs = 
		"\\\\poseidon\\team\\semantic search\\BillionTripleData\\crude\\dbpedia-v3.equ";
	public static String geonamesSameAs = 
		"\\\\poseidon\\team\\semantic search\\BillionTripleData\\crude\\geonames.equ";
	public static String dblpSameAs = 
		"\\\\poseidon\\team\\semantic search\\BillionTripleData\\crude\\swetodblp.equ";

	public static String domainDBpedia = "dbpedia.org";
	public static String domainGeonames = "sws.geonames.org";
	public static String domainDblp = "www.informatik.uni-trier.de";
	public static int cheatLineNum = 89746;
//	public static int stdAnsSize = 88867;
//	public static int stdAnsSize = 2318;
	
	public static void main(String[] args) throws Exception {
//		extractSameAsByDomain(dbpediaSameAs, domainDBpedia, domainGeonames, 
//				Indexer.indexFolder+"dbpedia2geonames.equ");
//		extractSameAsByDomain(geonamesSameAs, domainGeonames, domainDBpedia, 
//				Indexer.indexFolder+"geonames2dbpedia.equ");
//		extractSameAsByDomain(dblpSameAs, domainDblp, domainDblp, 
//				Indexer.indexFolder+"dblp.equ");
//		toID(new String[]{Indexer.indexFolder+"dbpedia2geonames.equ", 
//				Indexer.indexFolder+"geonames2dbpedia.equ", 
//				Indexer.indexFolder+"dblp.equ"}, Indexer.indexFolder+"sameAsID.txt");
//		getNonNullSameAsInd(Indexer.indexFolder+"nonNullInd.txt", Indexer.indexFolder+"sameAsID.txt", 
//				Indexer.indexFolder+"nonNullSameAsInd.txt");
//		normSameAsID(Indexer.indexFolder+"sameAsID.txt", Indexer.indexFolder+"sameAsIDNorm.txt"); // done
		// sort sameAsIDNorm.txt | uniq > sameAsIDNonDup.txt and rename sameAsIDNonDup.txt to sameAsID.txt
//		evaluate(Indexer.indexFolder+"NonNullSameAsInd.txt", cheatLineNum, 
//				Indexer.indexFolder+"ppjoin\\r0.3.txt",
//				Indexer.indexFolder+"sameAsID.txt"); // done
//		removeNullSameAsPairs(Indexer.indexFolder+"sameAsID.txt", 
//				Indexer.indexFolder+"nonNullSameAsInd.txt", Indexer.indexFolder+"nonNullSameAs.txt");
		evaluate(Indexer.indexFolder+"NonNullSameAsInd.txt", cheatLineNum, 
				Indexer.indexFolder+"ppjoin\\r0.3.txt",
				Indexer.indexFolder+"nonNullSameAs.txt"); // done
	}
	
	/**
	 * remove standard answer pairs with individuals without basic features
	 * @param sameAsID
	 * @param nonNullInd
	 * @param output
	 * @throws Exception
	 */
	public static void removeNullSameAsPairs(String sameAsID, String nonNullInd, 
			String output) throws Exception {
		HashSet<Integer> indSet = getSet(nonNullInd);
		BufferedReader br = new BufferedReader(new FileReader(sameAsID));
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			if (indSet.contains(x) && indSet.contains(y)) pw.println(x + " " + y);
		}
		pw.close();
		br.close();
	}
	
	/**
	 * estimate precision and recall of the blocking result
	 * @param lineListFile
	 * @param lineNum
	 * @param ppJoinResult
	 * @param stdAns
	 * @throws Exception
	 */
	public static void evaluate(String lineListFile, int lineNum, String ppJoinResult, 
			String stdAns) throws Exception {
		int[] lineList = new int[lineNum+1];
		BufferedReader br = new BufferedReader(new FileReader(lineListFile));
		for (int i = 1; i <= lineNum; i++) lineList[i] = Integer.parseInt(br.readLine());
		br.close();
		HashSet<String> stdAnswers = getLines(stdAns);
		br = new BufferedReader(new FileReader(ppJoinResult));
		int count = 0;
		PrintWriter pw = IOFactory.getPrintWriter(Indexer.indexFolder+"temp.txt");
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			int line1 = lineList[Integer.parseInt(parts[0])];
			int line2 = lineList[Integer.parseInt(parts[1])];
			if (line1 < line2) {
				int tmp = line1;
				line1 = line2;
				line2 = tmp;
			}
			String ans = line1 + " " + line2;
			pw.println(ans);
			if (stdAnswers.contains(ans)) count++;
		}
		pw.close();
		br.close();
		System.out.println(count + " lines overlap");
		int stdSize = Analyzer.countLines(stdAns);
		System.out.println("standard answer size: " + stdSize);
		System.out.println("recall: " + (count+0.0)/stdSize);
		int resultSize = Analyzer.countLines(ppJoinResult);
		System.out.println("result size: " + resultSize);
		System.out.println("precision: " + (count+0.0)/resultSize);
	}
	
	private static HashSet<String> getLines(String stdAns) throws Exception {
		HashSet<String> ret = new HashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(stdAns));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			ret.add(line);
		}
		return ret;
	}

	/**
	 * within each pair, larger ID comes first, then sort and uniq
	 * @param sameAsID
	 * @param output
	 * @throws Exception
	 */
	public static void normSameAsID(String sameAsID, String output) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(sameAsID));
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			if (x < y) {
				int tmp = x;
				x = y;
				y = tmp;
			}
			pw.println(x + " " + y);
		}
		br.close();
		pw.close();
	}
	
	/**
	 * dump basic features of individuals listed in the lineListfile
	 * @param lineListFile
	 * @param numLines
	 * @param output
	 * @throws Exception
	 */
	public static void dumpFeature(String lineListFile, int numLines, String output) throws Exception {
		Indexer.dumpClassFeature(lineListFile, numLines, output);
	}
	
	/**
	 * obtain IDs from sameAsID that have non-null basic features
	 * @param nonNullInd
	 * @param sameAsID
	 * @throws Exception
	 */
	public static void getNonNullSameAsInd(String nonNullInd, String sameAsID, 
			String output) throws Exception {
		HashSet<Integer> nonNullIndSet = getSet(nonNullInd);
		TreeSet<Integer> ret = new TreeSet<Integer>();
		BufferedReader br = new BufferedReader(new FileReader(sameAsID));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			if (nonNullIndSet.contains(x)) ret.add(x);
			if (nonNullIndSet.contains(y)) ret.add(y);
		}
		br.close();
		writeSet(ret, output);
	}
	
	private static void writeSet(TreeSet<Integer> ret, String output) throws Exception {
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (Integer i : ret) pw.println(i.intValue());
		pw.close();
	}

	private static HashSet<Integer> getSet(String input) throws Exception {
		HashSet<Integer> ret = new HashSet<Integer>();
		BufferedReader br = new BufferedReader(new FileReader(input));
		for (String line = br.readLine(); line != null; line = br.readLine()) 
			ret.add(Integer.parseInt(line));
		return ret;
	}

	/**
	 * extract sameAs statements from domain1 to domain2 from input to output
	 * @param input
	 * @param domain1
	 * @param domain2
	 * @param output
	 * @throws Exception
	 */
	public static void extractSameAsByDomain(String input, String domain1, String domain2, 
			String output) throws Exception {
		System.out.println("extract to " + output);
		PrintWriter pw = IOFactory.getPrintWriter(output);
		IDataSourceReader br = IOFactory.getReader(input);
		int count = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			if (parts.length > 2) {
				String o1 = parts[0];
				String o2 = parts[2];
				if (o1.contains(domain1) && o2.contains(domain2)) pw.println(o1 + " " + o2);
			}
			count++;
			if (count % 3000000 == 0) System.out.println(
					new Date().toString() + " : " + count);
		}
		br.close();
		pw.close();
		System.out.println(count + " lines in all");
	}
	
	/**
	 * convert the sameAs individual pairs to doc#s in the basicFeatureIndex and write them to output
	 * @param inputs
	 * @param output
	 * @throws Exception
	 */
	public static void toID(String[] inputs, String output) throws Exception {
		PrintWriter pw = IOFactory.getPrintWriter(output);
		IndexReader ireader = IndexReader.open(Indexer.lap3index);
		int count = 0;
		for (String fn : inputs) {
			IDataSourceReader br = IOFactory.getReader(fn);
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				String[] parts = line.split(" ");
				pw.println(getDocNum(ireader, parts[0]) + " " + getDocNum(ireader, parts[1]));
				count++;
				if (count%10000 == 0) System.out.println(new Date().toString() + " : " + count);
			}
			br.close();
		}
		System.out.println(new Date().toString() + " : " + count + " lines in all");
		pw.close();
	}

	private static int getDocNum(IndexReader ireader, String string) throws Exception {
		TermDocs td = ireader.termDocs(new Term("URI", string));
		td.next();
		return td.doc();
	}
}
