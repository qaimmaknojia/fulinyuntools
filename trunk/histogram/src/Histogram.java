import java.util.Date;
import java.util.Random;


public class Histogram {

	public static int maxrect(int[] histogram) {
		TmpRect[] stack = new TmpRect[histogram.length];
		stack[0] = new TmpRect(0, 0, histogram[0]);
		int top = 0;
		int maxarea = 0;
		for (int p = 1; p < histogram.length; p++) {
//			System.out.println("p = " + p);
			if (histogram[p] > stack[top].height) {
				stack[top+1] = new TmpRect(p, p, histogram[p]);
				top++;
			} else {
				int i;
				for (i = top; i >= 0 && stack[i].height >= histogram[p]; i--) {
					int area = (p-stack[i].start)*stack[i].height;
//					System.out.println((p-stack[i].start) + " * " + stack[i].height + " = " + area);
					if (area > maxarea) maxarea = area;
				}
				top = i+1;
				if (top != 0) stack[top] = new TmpRect(stack[top-1].pos+1, p, histogram[p]);
				else stack[top] = new TmpRect(0, p, histogram[p]);
			}
//			for (int i = 0; i <= top; i++) System.out.println(stack[i].start + ", " + stack[i].pos + ", " + stack[i].height);
//			System.out.println("***********");
		}
		for (int p = 0; p < top; p++) {
			int area = (histogram.length-stack[p].start)*stack[p].height;
			if (area > maxarea) maxarea = area;
		}
		return maxarea;
	}
	
	public static void testMaxRect() {
		Random r = new Random();
		long seed = new Date().getTime();
//		long seed = 1224906638115L;
		System.out.println("seed: " + seed);
		r.setSeed(seed);
		int n = 100;
		int h = 10000;
		int[] hist = new int[n];
		for (int i = 0; i < hist.length; i++) hist[i] = r.nextInt(h);
		int result = maxrect(hist);
		int std = 0;
		int start = -1;
		int end = -1;
		for (int i = 0; i < hist.length; i++) for (int j = i; j < hist.length; j++) {
			int minh = h;
			for (int k = i; k <= j; k++) if (hist[k] < minh) minh = hist[k];
			int area = minh*(j-i+1);
			if (area > std) {
				std = area;
				start = i;
				end = j;
			}
		}
		if (std != result) {
			System.out.println("error!");
			for (int i = 0; i < hist.length; i++) System.out.print(" " + hist[i]);
			System.out.println();
			System.out.println("std: " + std + " start: " + start + " end: " + end);
			System.out.println("res: " + result);
		}
		else System.out.println("correct!");
	}
	
	public static void main(String[] args) {
		testMaxRect();
	}
}

class TmpRect {
	int start;
	int pos;
	int height;
	
	public TmpRect(int s, int p, int h) {
		start = s;
		pos = p;
		height = h;
	}
}
