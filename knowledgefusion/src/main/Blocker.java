package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
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
		evaluate(workFolder+"r0.5block.txt", Indexer.indexFolder+"sameAsID.txt"); // done

	}
	
	/**
	 * evaluate recall of blocking
	 * @param blockFile
	 * @param stdAns
	 * @throws Exception
	 */
	public static void evaluate(String blockFile, String stdAns) throws Exception {
		HashSet<String> stdSet = Common.getStringSet(stdAns);
		BufferedReader br = new BufferedReader(new FileReader(blockFile));
		int canSize = 0;
		int overlap = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			int[] docNums = Common.getNumsInLineSorted(line);
			System.out.println(docNums.length);
			canSize += docNums.length*(docNums.length-1)/2;
			for (int i = 0; i < docNums.length; i++) for (int j = 0; j < i; j++) 
				if (stdSet.contains(docNums[i] + " " + docNums[j])) overlap++;
		}
		br.close();
		Common.printResult(overlap, stdAns, canSize);
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
