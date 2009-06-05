package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
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
//		toIDNonNull(new String[]{Indexer.indexFolder+"dbpedia2geonames.equ", 
//				Indexer.indexFolder+"geonames2dbpedia.equ", 
//				Indexer.indexFolder+"dblp.equ"}, Indexer.indexFolder+"nonNullSameAsID.txt"); // done
		// sort -n nonNullSameAsID.txt | uniq > sameAsID.txt // done
		// sameAsID.txt: no duplicate pairs, larger doc# comes first, sorted in ascending order
//		getIndFromPairs(Indexer.indexFolder+"sameAsID.txt", Indexer.indexFolder+"keyInd.txt"); // done
		// keyInd.txt: all doc#s of individuals appear in some sameAs pair, sorted in ascending order
//		dumpFeature(Indexer.indexFolder+"keyInd.txt", 
//				Analyzer.countLines(Indexer.indexFolder+"keyInd.txt"), 
//				Blocker.workFolder+"cheatBasicFeature.txt"); // done
		// tokenizer cheatBasicFeature.txt // done
		// ppjoin j 0.5 cheatBasicFeature.txt.bin > r0.5.txt // done in 40s
//		translateDocNum(Indexer.indexFolder+"keyInd.txt", 
//				Analyzer.countLines(Indexer.indexFolder+"keyInd.txt"), 
//				Blocker.workFolder+"r0.5.txt", Blocker.workFolder+"r0.5translated.txt"); // done
		// sort -n r0.5translated.txt > r0.5sorted.txt // done
//		evaluate(Blocker.workFolder+"r0.5sorted.txt", Indexer.indexFolder+"sameAsID.txt"); // done
//		getFailed(Blocker.workFolder+"r0.5sorted.txt", Indexer.indexFolder+"sameAsID.txt", 
//				Blocker.workFolder+"r0.5failed.txt");
//		blockByRareWords(Blocker.workFolder+"cheatBasicFeature.txt.bin", 
//				Analyzer.countLines(Blocker.workFolder+"cheatBasicFeature.txt"), 3, 
//				Blocker.workFolder+"rare3.txt"); // done
//		readBinaryFile(Blocker.workFolder+"cheatBasicFeature.txt.bin"); // unexpected byte order!!!
//		translateDocNum(Indexer.indexFolder+"keyInd.txt", 
//		Analyzer.countLines(Indexer.indexFolder+"keyInd.txt"), 
//		Blocker.workFolder+"rare3.txt", Blocker.workFolder+"rare3translated.txt"); // done
		// sort -n rare3translated.txt > rare3sorted.txt // done
//		evaluate(Blocker.workFolder+"rare3sorted.txt", Indexer.indexFolder+"sameAsID.txt"); // done
//		blockByRareWords(Blocker.workFolder+"cheatBasicFeature.txt.bin", 
//				Analyzer.countLines(Blocker.workFolder+"cheatBasicFeature.txt"), 1, 
//				Blocker.workFolder+"rare1.txt"); // done
//		translateDocNum(Indexer.indexFolder+"keyInd.txt", 
//				Analyzer.countLines(Indexer.indexFolder+"keyInd.txt"), 
//				Blocker.workFolder+"rare1.txt", Blocker.workFolder+"rare1translated.txt"); // done
		// sort -n rare1translated.txt > rare1sorted.txt // done
//		evaluate(Blocker.workFolder+"rare1sorted.txt", Indexer.indexFolder+"sameAsID.txt"); // done
		// qtokenizer 5 cheatBasicFeatureU.txt // done
		// ppjoin j 0.5 cheatBasicFeatureU.txt.5gram.bin > u5j0.5.txt // done
//		translateDocNum(Indexer.indexFolder+"keyInd.txt", 
//				Analyzer.countLines(Indexer.indexFolder+"keyInd.txt"), 
//				Blocker.workFolder+"u5j0.5.txt", Blocker.workFolder+"u5j0.5translated.txt"); // done
		// sort -n u5j0.5translated.txt > u5j0.5sorted.txt // done
//		evaluate(Blocker.workFolder+"u5j0.5sorted.txt", Indexer.indexFolder+"sameAsID.txt"); // done
	}
	
	public static void readBinaryFile(String input) throws Exception {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(input)));
		while (true) {
			System.out.println(dis.readUnsignedByte());
			System.in.read();
			System.in.read();
		}
	}
	
	/**
	 * words in each records in input is sorted by document frequency, if the first n words of two records
	 * are the same, block them
	 * @param input
	 * @param n
	 * @param output
	 * @throws Exception
	 */
	private static void blockByRareWords(String input, int lines, int n, String output) throws Exception {
		int[][] feature = new int[lines+1][];
		getBinaryFeature(input, lines, feature);
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (int i = 1; i <= lines; i++) for (int j = 1; j < i; j++) 
			if (firstNSame(feature[i], feature[j], n)) pw.println(i + " " + j);
		pw.close();
	}
	
	private static boolean firstNSame(int[] a, int[] b, int n) throws Exception {
		for (int i = 0; i < n && i < a.length && i < b.length; i++) if (a[i] != b[i]) return false;
		return true;
	}

	private static void getBinaryFeature(String input, int lines,
			int[][] feature) throws Exception {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(input)));
		for (int i = 1; i <= lines; i++) {
			int idx = readBigEndianInt(dis);
			int n = readBigEndianInt(dis);
			feature[idx] = new int[n];
			for (int j = 0; j < n; j++) feature[idx][j] = readBigEndianInt(dis);
		}
		dis.close();
	}

	private static int readBigEndianInt(DataInputStream dis) throws Exception {
		return dis.readUnsignedByte()+(dis.readUnsignedByte()<<8)+(dis.readUnsignedByte()<<16)+
			(dis.readUnsignedByte()<<24);
	}

	private static void getFailed(String result, String ans, String output) throws Exception {
		HashSet<String> resultSet = Common.getStringSet(result);
		BufferedReader br = new BufferedReader(new FileReader(ans));
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			if (!resultSet.contains(line)) pw.println(line);
		}
		pw.close();
		br.close();
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
	 * estimate precision and recall of the blocking result according to ppjoin output pairs
	 * @param lineListFile
	 * @param lineNum
	 * @param ppJoinResult
	 * @param stdAns
	 * @throws Exception
	 */
	public static void evaluate(String ppJoinResultTranslated, String stdAns) throws Exception {
		HashSet<String> stdAnswers = getLines(stdAns);
		BufferedReader br = new BufferedReader(new FileReader(ppJoinResultTranslated));
		int count = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			if (stdAnswers.contains(line)) count++;
		}
		br.close();
		Common.printResult(count, stdAns, Analyzer.countLines(ppJoinResultTranslated));
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
