import java.util.Date;
import java.util.Random;


public class LongestParenSubstring {

	public static char[] lps(char[] input) {
		char[] str = new char[input.length*2+1];
		for (int i = 0; i < input.length; i++) str[i] = input[i];
		str[input.length] = 1;
		for (int i = input.length+1, j = input.length-1; i < str.length; i++, j--) str[i] = input[j];
		SuffixArray.createSuffixArray(str);
		RMQ.rmqPreprocess(SuffixArray.height);
		int max1 = 0;
		int center1 = -1;
		int max2 = 0;
		int center2 = -1;
		for (int i = 0; i < input.length; i++) {
			int start = SuffixArray.rank[i];
			int end = SuffixArray.rank[2*input.length-i];
			if (start > end) {
				int temp = start;
				start = end;
				end = temp;
			}
			int pos = RMQ.rmq(SuffixArray.height, start+1, end+1);
			int len = SuffixArray.height[pos];
			if (len > max1) {
				max1 = len;
				center1 = i;
			}
			if (i > 0) {
				start = SuffixArray.rank[i];
				end = SuffixArray.rank[2*input.length-i+1];
				if (start > end) {
					int temp = start;
					start = end;
					end = temp;
				}
				pos = RMQ.rmq(SuffixArray.height, start+1, end+1);
				len = SuffixArray.height[pos];
				if (len > max2) {
					max2 = len;
					center2 = i;
				}
			}
		}
		if (max1*2-1 > max2*2) {
			char[] ret = new char[max1*2-1];
			ret[max1-1] = input[center1];
			for (int i = 1; i < max1; i++) ret[max1-1+i] = ret[max1-1-i] = input[center1+i];
			return ret;
		} else {
			char[] ret = new char[max2*2];
			for (int i = 0; i < max2; i++) ret[max2+i] = ret[max2-i-1] = input[center2+i];
			return ret;
		}
	}
	
	public static char[] naiveLps(char[] input) {
		int max = 0;
		int start = -1;
		for (int i = 0; i < input.length; i++) {
			int count = 1;
			for (int j = 1; i+j < input.length && i-j >= 0; j++) {
				if (input[i+j] == input[i-j]) count += 2;
				else break;
			}
			if (count > max) {
				max = count;
				start = i-count/2;
			}
			count = 0;
			for (int j = 0; i+j < input.length && i-j-1 >= 0; j++) {
				if (input[i+j] == input[i-j-1]) count += 2;
				else break;
			}
			if (count > max) {
				max = count;
				start = i-count/2;
			}
		}
		char[] ret = new char[max];
		for (int i = 0, j = start; i < max; i++, j++) ret[i] = input[j];
		return ret;
	}
	
	public static void testParen() {
		int n = 100;
		char[] test = new char[n];
		Random r = new Random();
		r.setSeed(new Date().getTime());
		for (int i = 0; i < n; i++) test[i] = (char)('a'+r.nextInt(2));
		char[] lres = lps(test);
		char[] nres = naiveLps(test);
		if (!new String(lres).equals(new String(nres))) {
			System.out.println("error:");
			System.out.println(new String(test));
			System.out.println("std:\t" + new String(nres));
			System.out.println("lps:\t" + new String(lres));
		}
		System.out.println(new String(test));
		System.out.println(new String(lres));
	}
	
	public static void main(String[] args) {
		testParen();
	}
}
