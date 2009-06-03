package main;

import java.io.PrintWriter;
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
	
	public static void main(String[] args) throws Exception {
		findBlock("e:\\user\\fulinyun\\findBlockTestIn.txt", "e:\\user\\fulinyun\\findBlockTestOut.txt");
	}
	
	/**
	 * find blocks from ppjoin result, note that in ppjoin result doc# starts from 1, in findBlock() output
	 * doc# starts from 0.
	 * @param input
	 * @param output
	 * @throws Exception
	 */
	public static void findBlock(String input, String output) throws Exception {
		IDataSourceReader br = IOFactory.getReader(input);
		HashSet<HashSet<Integer>> blocks = new HashSet<HashSet<Integer>>();
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			
			// get document numbers
			int ix = Integer.parseInt(parts[0])-1;
			int iy = Integer.parseInt(parts[1])-1;

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

	public static String ppIndex = 
		"\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\ppIndex";
	
	/**
	 * index contains a set of records
	 * t is a Jaccard similarity threshold
	 * output contains all pairs of records (x, y), such that sim(x, y) >= t
	 */
	public static void ppjoin(String indexR, double t, 
			String output) throws Exception {
		IndexReader ireader = IndexReader.open(indexR);
		for (int r = 0; r < ireader.maxDoc(); r++) {
			// record how many words have been overlapped
			HashMap<Integer, Integer> matched = new HashMap<Integer, Integer>();
			
			// get record, calculate necessary prefix length
			String[] x = ireader.document(r).get("extended").split(" ");
			int lx = x.length;
			int p = lx-(int)Math.ceil(t*lx)+1;
			
			for (int i = 0; i < p; i++) {
				String w = x[i];
				TermPositions tp = ireader.termPositions(new Term("extended", w));
				while (tp.next()) {
					int iy = tp.doc();
					String[] y = ireader.document(iy).get(
							"extended").split(" ");
					int ly = y.length;
					if (ly >= t*lx || lx >= t*ly) continue;
					int j = tp.nextPosition();
					int alpha = (int)Math.ceil(t*(lx+ly)/(1+t));
					int ubound = 1+Math.min(lx-i-1, ly-j-1);
					int m = matched.get(iy);
					if (m+ubound >= alpha) matched.put(iy, m+1);
					else matched.put(iy, 0);
				}
			}
			verify(x, matched, ireader, output);
		}
		ireader.close();
	}

	private static void verify(String[] x, HashMap<Integer, Integer> matched,
			IndexReader ireader, String output) throws Exception {
		
	}
}
