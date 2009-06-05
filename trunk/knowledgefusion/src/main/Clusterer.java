package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;

import org.apache.lucene.index.IndexReader;

import basic.IDataSourceReader;
import basic.IOFactory;

public class Clusterer {

	public static void main(String[] args) throws Exception {
		
//		NN me = new NN(0, 0);
//		NN a = new NN(1, 50);
//		NN b = new NN(2, 75);
//		NN c = new NN(3, 101);
//		NN d = new NN(4, 102);
//		NNListAndNG listAndNg = new NNListAndNG(new NN[]{me, a, b, c, d}, 2);
//		
//		System.out.println(calcNg(new NN[]{me, a, b, c, d}));
		cluster(Blocker.workFolder+"r0.5block.txt", Blocker.workFolder+"r0.5cluster.txt", 3);
	}
	
	public static void evaluate(String clusterFile, String stdAns) throws Exception {
		HashSet<String> stdSet = Common.getStringSet(stdAns);
		BufferedReader br = new BufferedReader(new FileReader(clusterFile));
		int count = 0;
		int resultCount = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			int[] parts = Common.getNumsInLineSorted(line);
			resultCount += parts.length*(parts.length-1)/2;
			for (int i = 0; i < parts.length; i++) for (int j = i+1; j < parts.length; j++) {
				if (stdSet.contains(parts[j] + " " + parts[i])) count++;
			}
		}
		br.close();
		System.out.println(count + " lines overlap");
		int stdSize = stdSet.size();
		System.out.println("standard answer size: " + stdSize);
		System.out.println("recall: " + (count+0.0)/stdSize);
		System.out.println("result size: " + resultCount);
		System.out.println("precision: " + (count+0.0)/resultCount);
	}
	
	/**
	 * implement the CS & SN criteria
	 * @param input a set of doc# blocks, one per line 
	 * @param output a set of doc# clusters, one per line
	 * @param tsn threshold for SN criteria
	 * @throws Exception
	 */
	public static void cluster(String input, String output, float tsn) throws Exception {
		IDataSourceReader br = IOFactory.getReader(input);
		int count = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] records = line.split(" ");
			System.out.println(records.length);
			int[] docNums = new int[records.length];
			for (int i = 0; i < records.length; i++) docNums[i] = Integer.parseInt(records[i]);
			cluster(docNums, output, tsn);
			count++;
			if (count%100 == 0) System.out.println(new Date().toString() + " : " + count + " blocks");
		}
		br.close();
	}

	/**
	 * clustering within a block
	 * @param docNums map from internal index (0-n) to doc# of index
	 * @param output
	 */
	private static void cluster(int[] docNums, String output, float tsn) throws Exception {
		String[] basicFeatures = new String[docNums.length];
		getBasicFeatures(docNums, basicFeatures);
		NN[][] nnList = new NN[docNums.length][docNums.length];
		for (int i = 0; i < docNums.length; i++) for (int j = i+1; j < docNums.length; j++) {
			nnList[i][j] = new NN(j, hamming(basicFeatures, i, j));
		}
		for (int i = 0; i < docNums.length; i++) for (int j = 0; j < i; j++) { 
			nnList[i][j] = new NN(j, nnList[j][i].distance);
		}
		for (int i = 0; i < docNums.length; i++) {
			nnList[i][i] = new NN(i, 0);
		}
		for (int i = 0; i < docNums.length; i++) Arrays.sort(nnList[i], new Comparator<NN>() {
			public int compare(NN a, NN b) {
				if (a.distance > b.distance) return 1;
				if (a.distance == b.distance) return 0;
				return -1;
			}
		});
		NNListAndNG[] records = new NNListAndNG[docNums.length];
		for (int i = 0; i < docNums.length; i++) { 
			records[i] = new NNListAndNG(nnList[i], calcNg(nnList[i]));
		}
		boolean[] clustered = new boolean[docNums.length];
		for (int i = 0; i < docNums.length; i++) if (!clustered[i]) 
			cluster(docNums, records, i, tsn, output, clustered);
	}

	private static void getBasicFeatures(int[] docNums, String[] basicFeatures) throws Exception {
		IndexReader ireader = IndexReader.open(Indexer.lap3index);
		for (int i = 0; i < docNums.length; i++) basicFeatures[i] = 
			ireader.document(docNums[i]).get("basic");
		ireader.close();
	}

	private static float hamming(String[] basicFeatures, int i, int j) throws Exception {
		String[] r1 = basicFeatures[i].split(" ");
		String[] r2 = basicFeatures[j].split(" ");
		HashSet<String> tokenSet = new HashSet<String>();
		for (String s : r1) tokenSet.add(s);
		for (String s : r2) tokenSet.add(s);
		int intersection = r1.length+r2.length-tokenSet.size();
		return (float)(tokenSet.size()-intersection+0.0)/tokenSet.size();
	}

	private static float calcNg(NN[] nn) {
		float nnDistance = nn[1].distance;
		int i;
		for (i = 2; i < nn.length; i++) if (nn[i].distance > 2*nnDistance) break;
		return i-1;
	}

	/**
	 * try to cluster i and its nearest neighbors
	 * @param nnList
	 * @param i
	 * @param tsn
	 * @param output
	 * @param clustered
	 */
	private static void cluster(int[] docNums, NNListAndNG[] records, int i, float tsn, String output,
			boolean[] clustered) throws Exception {
		NNListAndNG theOne = records[i];
		NNListAndNG nn = records[theOne.nnList[1].neighbor];
		for (int j = records.length-1; j >= 1; j--) {
			if (equalSet(theOne.nnList, nn.nnList, j) && 
					avgNg(records, theOne.nnList, j) < tsn) {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(output, true)));
				pw.print(docNums[theOne.nnList[0].neighbor]);
				for (int k = 1; k <= j; k++) pw.print(" " + docNums[theOne.nnList[k].neighbor]);
				pw.println();
				pw.close();
				for (int k = 0; k <= j; k++) clustered[k] = true;
				break;
			}
		}
	}

	/**
	 * the average neighborhood growth of the first j+1 elements in nnList
	 * @param records
	 * @param nnList
	 * @param j
	 * @return
	 */
	private static float avgNg(NNListAndNG[] records, NN[] nnList, int j) {
		float ret = 0;
		for (int i = 0; i <= j; i++) ret += records[nnList[i].neighbor].ng;
		ret /= (j+1);
		return ret;
	}

	/**
	 * is this two nnList the same for the first j+1 elements?
	 * @param nnList
	 * @param nnList2
	 * @param j
	 * @return
	 */
	private static boolean equalSet(NN[] nnList1, NN[] nnList2, int j) {
		HashSet<Integer> nnSet1 = new HashSet<Integer>();
		for (int i = 0; i <= j; i++) nnSet1.add(nnList1[i].neighbor);
		for (int i = 0; i <= j; i++) if (!nnSet1.contains(nnList2[i].neighbor)) return false;
		return true;
	}
	
//	/**
//	 * based on the input blocking results and basic feature index, calculate clusters
//	 * @param input is the output of the ssjoin program
//	 * @param output one basic feature per line, format: doc# \t basicFeature 
//	 * @throws Exception
//	 */
//	public static void dumpBasicFeature(String input, String output) throws Exception {
//		IndexReader ireader = IndexReader.open(Indexer.lap3index);
//		IDataSourceReader br = IOFactory.getReader(input);
//		HashSet<Integer> docNumSet = new HashSet<Integer>();
//		for (String line = br.readLine(); line != null; line = br.readLine()) {
//			String[] parts = line.split(" ");
//			
//			// get document numbers
//			int ix = Integer.parseInt(parts[0])-1;
//			int iy = Integer.parseInt(parts[1])-1;
//			docNumSet.add(ix);
//			docNumSet.add(iy);
//		}
//		br.close();
//		PrintWriter pw = IOFactory.getPrintWriter(output);
//		for (Integer i : docNumSet) {
//			// get and write doc# & basic features
//			pw.println(i.intValue() + "\t" + ireader.document(i).get("basic"));
//		}
//		pw.close();
//	}
	
}

class NN {
	public int neighbor;
	public float distance;
	public NN(int neighbor, float distance) {
		this.neighbor = neighbor;
		this.distance = distance;
	}
}

class NNListAndNG {
	public NN[] nnList;
	public float ng;
	public NNListAndNG(NN[] nnList, float ng) {
		this.nnList = nnList;
		this.ng = ng;
	}
}
