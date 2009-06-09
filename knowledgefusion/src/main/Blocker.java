package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermPositions;

import basic.IDataSourceReader;
import basic.IOFactory;

/**
 * Implementation of the positional prefix filtering algorithm from 
 * C. Xiao et al. WWW 2008 paper
 * not necessary, since we reuse the programs from 
 * http://www.cse.unsw.edu.au/~weiw/project/simjoin.html
 * @author fulinyun
 *
 */
public class Blocker {
	
	public static String workFolder = "E:\\User\\fulinyun\\ppjoin\\";
	
	public static void main(String[] args) throws Exception {
//		findBlock(workFolder+"r0.3sorted.txt", workFolder+"r0.3block.txt");
//		findBlock(workFolder+"r0.4sorted.txt", workFolder+"r0.4block.txt");
//		evaluate(workFolder+"r0.3block.txt", workFolder+"nonNullSameAs.txt");
//		evaluate(workFolder+"r0.4block.txt", workFolder+"nonNullSameAs.txt");
		
//		findBlock(workFolder+"r0.5sorted.txt", workFolder+"r0.5block.txt"); // done
//		evaluate(workFolder+"r0.5block.txt", Indexer.indexFolder+"sameAsID.txt"); // done
//		prefixBlocking(Blocker.workFolder+"cheatBasicFeature.txt.bin", 
//				Analyzer.countLines(Blocker.workFolder+"cheatBasicFeature.txt"), 0.2f, 
//				Blocker.workFolder+"prefix0.2block.txt"); // done
//		translateBlock(workFolder+"prefix0.2block.txt", Indexer.indexFolder+"keyInd.txt", workFolder+"prefix0.2blockTranslated.txt");
		evaluate(workFolder+"prefix0.2blockTranslated.txt", Indexer.indexFolder+"sameAsID.txt");
	}
	
	/**
	 * translate line#s in the block file to doc#s of individuals
	 * @param input
	 * @param keyIndList
	 * @param output
	 * @throws Exception
	 */
	public static void translateBlock(String input, String keyIndList, String output) throws Exception {
		int lineNum = Analyzer.countLines(keyIndList);
		int[] lineList = new int[lineNum+1];
		BufferedReader br = new BufferedReader(new FileReader(keyIndList));
		for (int i = 1; i <= lineNum; i++) lineList[i] = Integer.parseInt(br.readLine());
		br.close();
		br = new BufferedReader(new FileReader(input));
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			boolean first = true;
			for (String s : parts) {
				if (first) {
					pw.print(lineList[Integer.parseInt(s)]);
					first = false;
				} else {
					pw.print(" " + lineList[Integer.parseInt(s)]);
				}
			}
			pw.println();
		}
		pw.close();
		br.close();
	}
	
	/**
	 * words in each records in input is sorted by document frequency, if ceil(prefix*length)-prefix share
	 * at least one token, block them 
	 * @param input
	 * @param lines
	 * @param prefix
	 * @param output
	 * @throws Exception
	 */
	public static void prefixBlocking(String input, int lines, float prefix, String output) throws Exception {
		int[][] feature = new int[lines+1][];
		Common.getBinaryFeature(input, lines, feature);
		HashMap<Integer, ArrayList<Integer>> token2rec = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 1; i <= lines; i++) {
			for (int j = 0; j < (int)Math.ceil(feature[i].length*prefix); j++) {
				if (token2rec.containsKey(feature[i][j])) token2rec.get(feature[i][j]).add(i);
				else {
					ArrayList<Integer> value = new ArrayList<Integer>();
					value.add(i);
					token2rec.put(feature[i][j], value);
				}
			}
			if (i%10000 == 0) System.out.println(new Date().toString() + " : " + i + " lines indexed");
		}
		System.out.println(token2rec.size() + " blocks in all");
		int maxBlockSize = 0;
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (Integer i : token2rec.keySet()) {
			ArrayList<Integer> recs = token2rec.get(i);
			if (recs.size() == 1) continue;
			if (recs.size() > maxBlockSize) maxBlockSize = recs.size();
//			System.out.println(recs.size());
			boolean first = true;
			for (Integer j : recs) {
				if (first) {
					pw.print(j);
					first = false;
				} else {
					pw.print(" " + j);
				}
			}
			pw.println();
		}
		pw.close();
		System.out.println("max block size: " + maxBlockSize);
	}
	
	/**
	 * evaluate recall of blocking
	 * @param blockFile
	 * @param stdAns
	 * @throws Exception
	 */
	public static void evaluate(String blockFile, String stdAns) throws Exception {
		HashSet<String> stdSet = Common.getStringSet(stdAns);
		HashSet<String> resSet = new HashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(blockFile));
		int overlap = 0;
		int maxBlockSize = 0;
		int blockNum = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			int[] docNums = Common.getNumsInLineSorted(line);
			if (docNums.length > maxBlockSize) maxBlockSize = docNums.length;
			System.out.println(docNums.length);
			blockNum++;
			for (int i = 0; i < docNums.length; i++) for (int j = 0; j < i; j++) {
				String toTest = docNums[i] + " " + docNums[j];
				if (stdSet.contains(toTest)) {
					overlap++;
					stdSet.remove(toTest); // to avoid duplicate counting
				}
				resSet.add(toTest);
			}
		}
		br.close();
		Common.printResult(overlap, stdAns, resSet.size());
		System.out.println("max block size: " + maxBlockSize + " ; #block: " + blockNum);
		
	}
	
	/**
	 * find blocks from translated and sorted ppjoin result, each input line contains a pair of doc#s
	 * @param input
	 * @param output
	 * @throws Exception
	 */
	public static void findBlock(String input, String output) throws Exception {
		IDataSourceReader br = IOFactory.getReader(input);
		HashSet<HashSet<Integer>> blocks = new HashSet<HashSet<Integer>>();
		int count = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			
			// get document numbers
			int ix = Integer.parseInt(parts[0]);
			int iy = Integer.parseInt(parts[1]);

			if (blocks.size() == 0) {
				HashSet<Integer> block = new HashSet<Integer>();
				block.add(ix);
				block.add(iy);
				blocks.add(block);
			} else {
				boolean added = false;
				for (HashSet<Integer> block : blocks) {
					if (block.contains(ix)) {
						block.add(iy);
						added = true;
						break;
					} else if (block.contains(iy)) {
						block.add(ix);
						added = true;
						break;
					}
				}
				if (!added) {
					HashSet<Integer> block = new HashSet<Integer>();
					block.add(ix);
					block.add(iy);
					blocks.add(block);
				}
			}
			count++;
			if (count%100000 == 0) System.out.println(new Date().toString() + " : " + count);
		}
		br.close();
		PrintWriter pw = IOFactory.getPrintWriter(output);
		for (HashSet<Integer> block : blocks) {
			boolean first = true;
			for (Integer i : block) {
				if (first) {
					pw.print(i.intValue());
					first = false;
				} else {
					pw.print(" " + i.intValue());
				}
			}
			pw.println();
		}
		pw.close();
	}

//	public static String ppIndex = 
//		"\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\ppIndex";
//	
//	/**
//	 * index contains a set of records
//	 * t is a Jaccard similarity threshold
//	 * output contains all pairs of records (x, y), such that sim(x, y) >= t
//	 */
//	public static void ppjoin(String indexR, double t, 
//			String output) throws Exception {
//		IndexReader ireader = IndexReader.open(indexR);
//		for (int r = 0; r < ireader.maxDoc(); r++) {
//			// record how many words have been overlapped
//			HashMap<Integer, Integer> matched = new HashMap<Integer, Integer>();
//			
//			// get record, calculate necessary prefix length
//			String[] x = ireader.document(r).get("extended").split(" ");
//			int lx = x.length;
//			int p = lx-(int)Math.ceil(t*lx)+1;
//			
//			for (int i = 0; i < p; i++) {
//				String w = x[i];
//				TermPositions tp = ireader.termPositions(new Term("extended", w));
//				while (tp.next()) {
//					int iy = tp.doc();
//					String[] y = ireader.document(iy).get(
//							"extended").split(" ");
//					int ly = y.length;
//					if (ly >= t*lx || lx >= t*ly) continue;
//					int j = tp.nextPosition();
//					int alpha = (int)Math.ceil(t*(lx+ly)/(1+t));
//					int ubound = 1+Math.min(lx-i-1, ly-j-1);
//					int m = matched.get(iy);
//					if (m+ubound >= alpha) matched.put(iy, m+1);
//					else matched.put(iy, 0);
//				}
//			}
//			verify(x, matched, ireader, output);
//		}
//		ireader.close();
//	}
//
//	private static void verify(String[] x, HashMap<Integer, Integer> matched,
//			IndexReader ireader, String output) throws Exception {
//		
//	}
}
