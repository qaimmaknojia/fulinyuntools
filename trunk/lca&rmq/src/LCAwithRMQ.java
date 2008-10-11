import java.util.Date;
import java.util.Random;


public class LCAwithRMQ {

	public static int[][] naiveRmqTable;
	public static int[][] rmqTable;
	public static int[][][] pn1rmqTable;
	public static int[] index;
	public static int[] blockMinValue;
	public static void pn1rmqPreprocess(int[] array) {
		int len = floorToPowerOf2(array.length);
		int blockSize = (len>>1)+1;
		int numPossible = (1<<(blockSize-1));
		if (array.length%blockSize == 0) blockMinValue = new int[array.length/blockSize];
		else blockMinValue = new int[array.length/blockSize+1];
		pn1rmqTable = new int[numPossible][blockSize][blockSize+1];
		for (int i = 0; i < numPossible; i++) {
			int[] tarray = new int[blockSize];
			tarray[0] = 0;
			for (int j = 0; j < blockSize-1; j++) {
				if ((i&(1<<j)) != 0) tarray[j+1] = tarray[j]+1;
				else tarray[j+1] = tarray[j]-1;
			}
			naiveRmqPreprocess(tarray);
			for (int j = 0; j < blockSize; j++) for (int k = 0; k < blockSize+1; k++)
				pn1rmqTable[i][j][k] = naiveRmqTable[j][k];
		}
		index = new int[blockMinValue.length];
		for (int i = 0; i < array.length; i += blockSize) {
			int[] tarray = getBlock(i, blockSize, array);
			index[i/blockSize] = getBlockIndex(tarray);
		}
		for (int i = 0; i < blockMinValue.length; i++) {
			blockMinValue[i] = array[i*blockSize+pn1rmqTable[index[i]][0][blockSize]];
		}
		rmqPreprocess(blockMinValue);
	}
	
	public static int pn1rmq(int[] array, int start, int end) {
		int blockSize = pn1rmqTable[0].length;
		int startBlock = start/blockSize;
		int endBlock = (end-1)/blockSize;
		if (startBlock == endBlock) {
			int ind = index[startBlock/blockSize];
			return pn1rmqTable[ind][start%blockSize][(end-1)%blockSize+1];
		}
		int min = Integer.MAX_VALUE;
		int pos = -1;
		int ind = index[startBlock];
		int tpos = startBlock*blockSize + pn1rmqTable[ind][start%blockSize][blockSize];
		int t = array[tpos];
		if (t < min) {
			min = t;
			pos = tpos;
		}
		ind = index[endBlock];
		tpos = endBlock*blockSize + pn1rmqTable[ind][0][(end-1)%blockSize+1];
		t = array[tpos];
		if (t < min) {
			min = t;
			pos = tpos;
		}
		if (endBlock > startBlock+1) {
			int minMidBlock = rmq(blockMinValue, startBlock+1, endBlock);
			ind = index[minMidBlock];
			int midpos = minMidBlock*blockSize+pn1rmqTable[ind][0][blockSize];
			int midmin = blockMinValue[minMidBlock];
			if (midmin < min) {
				min = midmin;
				pos = midpos;
			}
		}
		return pos;
	}
	
	private static int getBlockIndex(int[] block) {
		int ret = 0;
		for (int i = 1; i < block.length; i++) if (block[i] > block[i-1]) ret += (1<<(i-1));
		return ret;
	}

	private static int[] getBlock(int startBlock, int blockSize, int[] array) {
		int[] block = new int[blockSize];
		for (int i = startBlock*blockSize, j = 0; j < blockSize; i++, j++) {
			if (i < array.length) block[j] = array[i];
			else block[j] = block[j-1]+1;
		}
		return block;
	}

	//sparse table
	public static void rmqPreprocess(int[] array) {
		rmqTable = new int[array.length][];
		int ii;
		for (ii = 0; (1 << ii) <= array.length; ii++) ;
		for (int i = 0; i < array.length; i++) rmqTable[i] = new int[ii-1];
		for (int i = 0; i < array.length; i++) rmqTable[i][0] = i;
		for (int i = 1; i < ii-1; i++) for (int j = 0; j < array.length; j++) {
			int k = j+(1<<(i-1));
			if (k >= array.length) rmqTable[j][i] = rmqTable[j][i-1];
			else rmqTable[j][i] = rmqTable[j][i-1] > rmqTable[k][i-1] 
			                              ? rmqTable[k][i-1] : rmqTable[j][i-1];
		}
	}
	
	public static int rmq(int[] array, int start, int end) {
		int span = end-start;
		int len = floorToPowerOf2(span);
		return rmqTable[start][len] > rmqTable[end-(1<<len)][len] 
		                                   ? rmqTable[end-(1<<len)][len] : rmqTable[start][len];
	}
	
	public static void testNaiveRmq() {
		int n = 10000;
		int m = 1000;
		int[] array = new int[n];
		for (int i = 0; i < n; i++) array[i] = i;
		Random r = new Random();
		r.setSeed(new Date().getTime());
		for (int i = 0; i < n; i++) {
			int j1 = r.nextInt(n);
			int j2 = r.nextInt(n);
			int t = array[j1];
			array[j1] = array[j2];
			array[j2] = t;
		}
		naiveRmqPreprocess(array);
		for (int i = 0; i < m; i++) {
			int start = r.nextInt(n);
			int end = r.nextInt(n);
			if (start > end) {
				int t = start;
				start = end;
				end = t;
			}
			if (start == end) end++;
			int res = naiveRmq(array, start, end);
			int min = Integer.MAX_VALUE;
			int pos = -1;
			for (int k = start; k < end; k++) if (array[k] < min) {
				min = array[k];
				pos = k;
			}
			if (res != pos) {
				System.out.println("error: " + start + "\t" + end);
				for (int k = start; k < end; k++) {
					System.out.print("\t" + array[k]);
					if (k % 20 == 19) System.out.println();
				}
				System.out.println();
				System.out.println("std: " + pos);
				System.out.println("naive: " + res);
				System.exit(1);
			}
		}
		
	}
	
	public static void testRmqEx() {
		int n = 1000;
		int[] array = new int[n];
		for (int i = 0; i < n; i++) array[i] = i;
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			int j1 = r.nextInt(n);
			int j2 = r.nextInt(n);
			int t = array[j1];
			array[j1] = array[j2];
			array[j2] = t;
		}
		rmqPreprocess(array);
		for (int i = 0; i < n; i++) for (int j = i+1; j < n; j++) {
			int res = rmq(array, i, j);
			int min = Integer.MAX_VALUE;
			int pos = -1;
			for (int k = i; k < j; k++) if (array[k] < min) {
				min = array[k];
				pos = k;
			}
			if (res != pos) {
				System.out.println("error: " + i + "\t" + j);
				for (int k = i; k < j; k++) {
					System.out.print("\t" + array[k]);
					if (k % 20 == 19) System.out.println();
				}
				System.out.println();
				System.out.println("std: " + pos);
				System.out.println("rmq: " + res);
				System.exit(1);
			}
		}
		
	}

	public static void testNaiveRmqEx() {
		int n = 1000;
		int[] array = new int[n];
		for (int i = 0; i < n; i++) array[i] = i;
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			int j1 = r.nextInt(n);
			int j2 = r.nextInt(n);
			int t = array[j1];
			array[j1] = array[j2];
			array[j2] = t;
		}
		naiveRmqPreprocess(array);
		for (int i = 0; i < n; i++) for (int j = i+1; j < n; j++) {
			int res = naiveRmq(array, i, j);
			int min = Integer.MAX_VALUE;
			int pos = -1;
			for (int k = i; k < j; k++) if (array[k] < min) {
				min = array[k];
				pos = k;
			}
			if (res != pos) {
				System.out.println("error: " + i + "\t" + j);
				for (int k = i; k < j; k++) {
					System.out.print("\t" + array[k]);
					if (k % 20 == 19) System.out.println();
				}
				System.out.println();
				System.out.println("std: " + pos);
				System.out.println("naive: " + res);
				System.exit(1);
			}
		}
		
	}
	
	public static int floorToPowerOf2(int input) {
		int low = 0, high = 30;
		while (low < high) {
			int mid = ((low+high)>>1);
			int p = (1<<mid);
			if (p == input) return mid;
			if (p < input) low = mid+1;
			else high = mid-1;
		}
		int p = (1<<low);
		if (p > input) return low-1;
		return low;
	}
	
	public static void testFloor2() {
		Random r = new Random();
		int n = 100000000;
		long t = new Date().getTime();
//		int temp = 0;
		for (int i = 0; i < n; i++) {
			int p = i+1;
			int f = floorToPowerOf2(p);
			//System.out.println(p + "\t" + f);
			if (p < (1<<f) || p >= 1<<(f+1)) System.out.println("error!\t" + p + "\t" + f); 
		}
		System.out.println(new Date().getTime()-t);
//		t = new Date().getTime();
//		for (int i = 0; i < n; i++) {
//			int p = i+1;
//			int f = (int)(Math.log(p)/Math.log(2));
//			temp += f;
//		}
//		System.out.println(new Date().getTime()-t);
	}
	
	public static void main(String[] args) {
//		testFloor2();
//		testNaiveRmq();
		testRmqEx();
	}
	
	public static void naiveRmqPreprocess(int[] array) {
		naiveRmqTable = new int[array.length][array.length+1];
		for (int span = 1; span < array.length; span++) for (int start = 0; start < array.length; start++) {
			if (span == 1) naiveRmqTable[start][start+1] = start;
			else {
				if (start+span <= array.length) {
					int v1 = array[naiveRmqTable[start][start+span-1]];
					int v2 = array[start+span-1];
					naiveRmqTable[start][start+span] 
					                     = (v1 > v2 ? start+span-1 : naiveRmqTable[start][start+span-1]);
				}
			}
		}
	}
	
	public static int naiveRmq(int[] array, int start, int end) {
		return naiveRmqTable[start][end];
	}
	
	public static int lca(int[] tree, int u, int v) {
		return -1;
	}
	
}
