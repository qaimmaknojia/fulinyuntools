import java.util.Arrays;
import java.util.Random;


public class MedianFinder {

	public static void main2(String[] args) {
		int[] t1 = {174, 217, 246, 399, 410, 519, 707, 712, 805, 988};
		int[] t2 = {49, 56, 261, 261, 365, 451, 490, 581, 682, 715, 788, 921, 930, 976, 984, 999};
		System.out.println("input:");
		for (int i = 0; i < t1.length; i++) System.out.print(t1[i]+" ");
		System.out.println();
		for (int i = 0; i < t2.length; i++) System.out.print(t2[i]+" ");
		System.out.println();
		find(t1, t2);
		check(t1, t2);
	}
	
	public static void main(String[] args) {
		Random r = new Random();
		for (int ii = 0; ii < 1000; ii++) {
			int l1 = r.nextInt(200)+1;
			int l2 = r.nextInt(200)+1;
			int[] t1 = new int[l1];
			int[] t2 = new int[l2];
			for (int i = 0; i < l1; i++) t1[i] = r.nextInt(10000);
			for (int i = 0; i < l2; i++) t2[i] = r.nextInt(10000);
			Arrays.sort(t1);
			Arrays.sort(t2);
			String f = find(t1, t2);
			String c = check(t1, t2);
			if (!f.equalsIgnoreCase(c)) {
				System.out.println("input:");
				for (int i = 0; i < l1; i++) System.out.print(t1[i]+" ");
				System.out.println();
				for (int i = 0; i < l2; i++) System.out.print(t2[i]+" ");
				System.out.println();
				find(t1, t2);
				check(t1, t2);
				break;
			}
		}
		System.out.println("all right");
	}
	
	public static void main1(String[] args) {
		Random r = new Random();
		int l1 = 3;
		int l2 = 19;
		int[] t1 = new int[l1];
		int[] t2 = new int[l2];
		for (int i = 0; i < l1; i++) t1[i] = r.nextInt(1000);
		for (int i = 0; i < l2; i++) t2[i] = r.nextInt(1000);
		Arrays.sort(t1);
		Arrays.sort(t2);
		System.out.println("input:");
		for (int i = 0; i < l1; i++) System.out.print(t1[i]+" ");
		System.out.println();
		for (int i = 0; i < l2; i++) System.out.print(t2[i]+" ");
		System.out.println();
		find(t1, t2);
		check(t1, t2);
	}
	
	public static String find(int[] a1, int[] a2) {
		int s1 = 0;
		int s2 = 0;
		int e1 = a1.length-1;
		int e2 = a2.length-1;
		if (a1.length > a2.length) {
			int trunk = (a1.length-a2.length)/2;
			s1 += trunk;
			e1 -= trunk;
		} else {
			int trunk = (a2.length-a1.length)/2;
			s2 += trunk;
			e2 -= trunk;
		}
		while (s1 < e1-1 && s2 < e2-1) {
			System.out.println(a1[s1] + "-" + a1[e1] + " , " + a2[s2] + "-" + a2[e2]);
			double m1 = med(a1, s1, e1);
			double m2 = med(a2, s2, e2);
//			System.out.println(m1 + " vs " + m2);
			int trunk = (e1-s1+1)/2;
			int trunk1 = (e2-s2+1)/2;
			if (trunk1 < trunk) trunk = trunk1;
			if (m1 > m2) {
				e1 -= trunk;
				s2 += trunk;
			} else {
				s1 += trunk;
				e2 -= trunk;
			}
//			System.out.println(a1[s1] + "-" + a1[e1] + " , " + a2[s2] + "-" + a2[e2]);
		}
		System.out.println("find:");
		int tl = e1-s1+1+e2-s2+1;
		int[] t = new int[tl];
		int j = 0;
		for (int i = s1; i <= e1; i++) {
			t[j] = a1[i];
			j++;
		}
		for (int i = s2; i <= e2; i++) {
			t[j] = a2[i];
			j++;
		}
		Arrays.sort(t);
		if (t.length%2 == 0) {
			int low = t[t.length/2-1], high = t[t.length/2];
			if (s1 > 0 && high == a1[s1] && a1[s1-1] > low) low = a1[s1-1];
			else if (e1 < a1.length-1 &&low == a1[e1] &&  a1[e1+1] < high) high = a1[e1+1];
			else if (s2 > 0 && high == a2[s2] && a2[s2-1] > low) low = a2[s2-1];
			else if (e2 < a2.length-1 && low == a2[e2] && a2[e2+1] < high) high = a2[e2+1];
			System.out.println(low + "," + high);
			return low+","+high;
		}
		else {
			System.out.println(t[t.length/2]);
			return t[t.length/2]+"";
		}
	}
	
	private static double med(int[] t, int s, int e) {
		if ((e-s+1)%2 == 0) return (t[(s+e)/2] + t[(s+e)/2+1] + 0.0)/2.0;
		else return t[(s+e)/2]+0.0;
	}

	public static String check(int[] a1, int[] a2) {
		int[] t = new int[a1.length+a2.length];
		int i = 0;
		for (int a : a1) {
			t[i] = a;
			i++;
		}
		for (int a : a2) {
			t[i] = a;
			i++;
		}
		Arrays.sort(t);
		System.out.println("check:");
		if (t.length%2 == 0) {
			System.out.println(t[t.length/2-1] + "," + t[t.length/2]);
			return t[t.length/2-1] + "," + t[t.length/2];
		}
		else {
			System.out.println(t[t.length/2]);
			return t[t.length/2]+"";
		}
	}
}
