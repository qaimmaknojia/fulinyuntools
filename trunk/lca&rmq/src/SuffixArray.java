import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;


public class SuffixArray {

	public static int[] suffix;
	public static int[] rank;
	public static int[] height;
	public static int[] h;
//	public static Integer[][] ksuffix;
	
	public static void createSuffixArray(final char[] str) {
		final char[] string = new char[str.length+1];
		for (int i = 0; i < str.length; i++) string[i] = str[i];
		string[str.length] = 0;
		int len = RMQ.floorToPowerOf2(string.length)+2;
		final Integer[] ksuffix = new Integer[string.length];
		for (int i = 0; i < ksuffix.length; i++) ksuffix[i] = i;
		Arrays.sort(ksuffix, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				if (string[a] > string[b]) return 1;
				if (string[a] < string[b]) return -1;
				return 0;
			}
		});
		final Integer[] krank = new Integer[string.length];
		for (int i = 0; i < ksuffix.length; i++) {
			if (i == 0) krank[ksuffix[i]] = i;
			else {
				if (string[ksuffix[i-1]] == string[ksuffix[i]]) krank[ksuffix[i]] = krank[ksuffix[i-1]];
				else krank[ksuffix[i]] = krank[ksuffix[i-1]]+1;
			}
		}
		
		for (int i = 1; i < len; i++) {
			final int shift = (1<<(i-1));
			for (int j = 0; j < ksuffix.length; j++) ksuffix[j] = j;
			Arrays.sort(ksuffix, new Comparator<Integer>() {
				public int compare(Integer a, Integer b) {
					if (krank[a] > krank[b]) return 1;
					if (krank[a] < krank[b]) return -1;
					if (krank[a+shift] > krank[b+shift]) return 1;
					if (krank[a+shift] < krank[b+shift]) return -1;
					return 0;
				}
			});
			final Integer[] trank = new Integer[string.length];
			for (int j = 0; j < ksuffix.length; j++) {
				if (j == 0) trank[ksuffix[j]] = j;
				else {
					if (krank[ksuffix[j-1]] == krank[ksuffix[j]] 
					                && krank[ksuffix[j-1]+shift] == krank[ksuffix[j]+shift]) 
						trank[ksuffix[j]] = trank[ksuffix[j-1]];
					else trank[ksuffix[j]] = trank[ksuffix[j-1]]+1;
				}
			}
			for (int j = 0; j < ksuffix.length; j++) krank[j] = trank[j];
		}
		suffix = new int[ksuffix.length];
		for (int i = 0; i < suffix.length; i++) suffix[i] = ksuffix[i];
		rank = new int[krank.length];
		for (int i = 0; i < rank.length; i++) rank[i] = krank[i];
		h = new int[suffix.length];
		for (int i = 0; i < suffix.length; i++) {
			if (rank[i] == 0) h[i] = 0;
			else if (i == 0 || h[i-1] <= 1) {
				int count = 0;
				int j = i;
				int k = suffix[rank[i]-1];
				while (string[j] == string[k] && j < string.length && k < string.length) {
					j++;
					k++;
					count++; 
				}
				h[i] = count;
			} else {
				int count = h[i-1]-1;
				int j = i+count;
				int k = suffix[rank[i]-1]+count;
				while (string[j] == string[k] && j < string.length && k < string.length) {
					j++;
					k++;
					count++;
				}
				h[i] = count;
			}
		}
		height = new int[h.length];
		for (int i = 0; i < height.length; i++) height[i] = h[suffix[i]];
	}
	
	public static void testCreate() {
		int n = 100;
		char[] test = new char[100];
		Random r = new Random();
		r.setSeed(new Date().getTime());
		for (int i = 0; i < n; i++) test[i] = (char)('a'+r.nextInt(3));
		createSuffixArray(test);
		for (int i = 0; i < n; i++) for (int j = i+1; j < n; j++){
			if (rank[i] < rank[j] && compareSuffix(test, i, j) > 0 
					|| rank[i] > rank[j] && compareSuffix(test, i, j) < 0) { 
				System.out.println("error:\t" + i + "\t" + j);
				System.out.println(new String(test));
				System.out.println(new String(test).substring(i));
				System.out.println(new String(test).substring(j));
				System.exit(1);
			}
		}
		for (int i = 1; i < n; i++) {
			int std = lcp(test, suffix[i-1], suffix[i]);
			if (height[i] != std) {
				System.out.println("height error:\t" + i);
				System.out.println(new String(test));
				System.out.println(new String(test).substring(suffix[i-1]));
				System.out.println(new String(test).substring(suffix[i]));
				System.out.println("std:\t" + std);
				System.out.println("height:\t" + height[i]);
				System.exit(1);
			}
		}
		for (int i = 0; i < n+1; i++) {
			System.out.println(new String(test).substring(suffix[i]) + "\t" + height[i]);
		}
	}
	
	public static int lcp(char[] test, int a, int b) {
		int ret = 0;
		while (a < test.length && b < test.length && test[a] == test[b]) {
			a++;
			b++;
			ret++;
		}
		return ret;
	}
	
	public static int compareSuffix(char[] test, int a, int b) {
		String suffixa = new String(test).substring(a);
		String suffixb = new String(test).substring(b);
		return suffixa.compareTo(suffixb);
	}
	
	public static void main(String[] args) {
		testCreate();
	}
}
