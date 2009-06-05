package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

import main.Analyzer;
import main.Blocker;
import main.Common;
import main.Indexer;

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
//	public static int cheatLineNum = 89746;
//	public static int stdAnsSize = 88867;
//	public static int stdAnsSize = 2318;
//	public static int r03resultSize = 23359103;
	public static String keyInd = Indexer.indexFolder+"keyInd.txt";
		
	public static void main(String[] args) throws Exception {
//		extractSameAsByDomain(dbpediaSameAs, domainDBpedia, domainGeonames, 
//				Indexer.indexFolder+"dbpedia2geonames.equ");
//		extractSameAsByDomain(geonamesSameAs, domainGeonames, domainDBpedia, 
//				Indexer.indexFolder+"geonames2dbpedia.equ");
//		extractSameAsByDomain(dblpSameAs, domainDblp, domainDblp, 
//				Indexer.indexFolder+"dblp.equ");
		toIDNonNull(new String[]{Indexer.indexFolder+"dbpedia2geonames.equ", 
				Indexer.indexFolder+"geonames2dbpedia.equ", 
				Indexer.indexFolder+"dblp.equ"}, Indexer.indexFolder+"nonNullSameAsID.txt"); // running
		// sort -n nonNullSameAsID.txt | uniq > sameAsID.txt // to run
		// sameAsID.txt: no duplicate pairs, larger doc# comes first, sorted in ascending order
//		getIndFromPairs(Indexer.indexFolder+"sameAsID.txt", Indexer.indexFolder+"keyInd.txt"); // to run
		// keyInd.txt: all doc#s of individuals appear in some sameAs pair, sorted in ascending order
//		dumpFeature(Indexer.indexFolder+"keyInd.txt", 
//				Analyzer.countLines(Indexer.indexFolder+"keyInd.txt"), 
//				Blocker.workFolder+"cheatBasicFeature.txt"); // to run
		// tokenizer cheatBasicFeature.txt // to run
		// ppjoin j 0.5 cheatBasicFeature.txt.bin > r0.5.txt // to run
//		translateDocNum(Indexer.indexFolder+"keyInd.txt", 
//				Analyzer.countLines(Indexer.indexFolder+"keyInd.txt"), 
//				Blocker.workFolder+"r0.5.txt", Blocker.workFolder+"r0.5translated.txt"); // to run
		// sort -n r0.5translated.txt > r0.5sorted.txt // to run
		
		
//		evaluate(Indexer.indexFolder+"NonNullSameAsInd.txt", cheatLineNum, 
//				Indexer.indexFolder+"ppjoin\\r0.3.txt",
//				Indexer.indexFolder+"sameAsID.txt"); // done
//		evaluate(Indexer.indexFolder+"NonNullSameAsInd.txt", cheatLineNum, 
//				Indexer.indexFolder+"ppjoin\\r0.3.txt",
//				Indexer.indexFolder+"nonNullSameAs.txt"); // done
//		getIndFromPairs(Indexer.indexFolder+"nonNullSameAs.txt", Indexer.indexFolder+"keyInd.txt");
//		translateDocNum(Indexer.indexFolder+"nonNullSameAsInd.txt", cheatLineNum, 
//				Indexer.indexFolder+"ppjoin\\r0.3.txt", Indexer.indexFolder+"ppjoin\\r0.3translated.txt");
		// sort -n r0.3translated.txt > r0.3sorted.txt
		// ppjoin j 0.4 cheatBasicFeature.txt.bin > r0.4.txt
		// finished in 139s
//		evaluate(Blocker.workFolder+"nonNullSameAsInd.txt", cheatLineNum, 
//				Blocker.workFolder+"r0.4.txt", Blocker.workFolder+"nonNullSameAs.txt");
		// sort -n temp.txt > ppjoin\r0.4sorted.txt
//		translateDocNum(Blocker.workFolder+"nonNullSameAsInd.txt", cheatLineNum, 
//				Blocker.workFolder+"r0.5.txt", Blocker.workFolder+"r0.5translated.txt");
		// sort -n r0.5translated.txt > r0.5sorted.txt
	}
	
	/**
	 * translate the result of ppjoin, replace line# with individual IDs, and remove similarity values
	 * @param lineListFile
	 * @param ppjoinResult
	 * @param output
	 */
	public static void translateDocNum(String lineListFile, int lineNum, String ppjoinResult, 
			String output) throws Exception {
		int[] lineList = new int[lineNum+1];
		BufferedReader br = new BufferedReader(new FileReader(lineListFile));
		for (int i = 1; i <= lineNum; i++) lineList[i] = Integer.parseInt(br.readLine());
		br.close();
		br = new BufferedReader(new FileReader(ppjoinResult));
		PrintWriter pw = IOFactory.getPrintWriter(output);
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
		}
		pw.close();
		br.close();
	}
	
	/**
	 * get all distinct ids from an id pair file, used to get key individuals 
	 * @param input
	 * @param output
	 * @throws Exception
	 */
	public static void getIndFromPairs(String input, String output) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(input));
		TreeSet<Integer> ret = new TreeSet<Integer>();
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			ret.add(x);
			ret.add(y);
		}
		br.close();
		writeSet(ret, output);
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
		HashSet<Integer> indSet = Common.getIntSet(nonNullInd);
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
	 * estimate precision and recall of the blocking result, temp.txt recording doc#s of the candidate pairs
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
		Common.printResult(count, stdAns, Analyzer.countLines(ppJoinResult));
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
		HashSet<Integer> nonNullIndSet = Common.getIntSet(nonNullInd);
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
	public static void toIDNonNull(String[] inputs, String output) throws Exception {
		PrintWriter pw = IOFactory.getPrintWriter(output);
		IndexReader ireader = IndexReader.open(Indexer.lap3index);
		int count = 0;
		for (String fn : inputs) {
			IDataSourceReader br = IOFactory.getReader(fn);
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				String[] parts = line.split(" ");
				int x = getDocNum(ireader, parts[0]);
				int y = getDocNum(ireader, parts[1]);
				if (ireader.document(x).get("basic").equals("") || 
						ireader.document(y).get("basic").equals(""))
					continue;
				if (x < y) {
					int t = x;
					x = y;
					y = t;
				}
				pw.println(x + " " + y);
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
