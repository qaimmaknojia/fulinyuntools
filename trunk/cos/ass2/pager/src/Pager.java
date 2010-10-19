import java.util.Arrays;
import java.util.HashSet;


public class Pager {

	/**
	 * Page reference string for loop 1 initialization
	 * 
	 * R0 := ZERO	02
	 * R1 := n		03
	 */
	public static String loop1init = "0203";

	/**
	 * Page reference string for loop 1 
	 * 
	 * compare R0,R1			0
	 * branch greaterequal*+15	0
	 * R2 := n					03
	 * R2 := R0 * R2			0
	 * B[R0] := R2				05 - 5 dirty write[6] = true
	 * R2 := B[R0]				05
	 * R3 := m					03
	 * R2 := R2 + R3 			0
	 * A[R0] := R2				04 - 4 dirty write[13] = true
	 * R2 := A[R0] 				04
	 * R3 := B[R0] 				05
	 * R2 := R2 * R3 			0 
	 * C[R0] := R2 				06 - 6 dirty write[20] = true
	 * R2 := ONE 				02 
	 * R0 := R0 + R2 			0 
	 * branch*-15 				0
	 */
	public static String loop1 = "0003005050300404050060200";

	/**
	 * The page reference string at the end of loop 1 
	 * 
	 * compare R0,R1 			0
	 * branch greaterequal*+15 	0
	 */
	public static String loop1end = "00";
	
	/**
	 * The page reference string of initializing loop 2
	 * 
	 * R0 := ZERO 	02
	 * R1 := n 		03
	 */
	public static String loop2init = "110203";

	/**
	 * The page reference string before the inner loop inside loop 2
	 * 
	 * compare R0,R1 			0 
	 * branch greaterequal*+32 	0
	 * R2 := R0 				0 
	 * R3 := n 					03
	 */
	public static String beforeInnerLoop = "00003";

	/**
	 * The page reference string of the inner loop inside loop 2 
	 * 
	 * compare R2,R3 			0
	 * branch greaterequal*+16 	0 
	 * R4 := A[R0] 				04
	 * R5 := m 					03 
	 * R4 := R4 * R5 			0 
	 * R5 := B[R2] 				05 
	 * R4 := R5 + R4 			1 
	 * B[R2] := R4 				15 - 5 dirty write[11] = true
	 * R4 := A[R2] 				14 
	 * R5 := B[R1] 				15 
	 * R4 := R4 + R5 			1 
	 * R5 := C[R2] 				16 
	 * R4 := R5 - R4 			1 
	 * C[R2] := R4 				16 - 6 dirty write[21] = true
	 * R4 := ONE 				12 
	 * R2 := R2 + R4 			1 
	 * branch*-16 				1
	 */
	public static String innerLoop = "00040300511514151161161211";

	/**
	 * The page reference string after the inner loop inside loop 2
	 * 
	 * compare R2,R3 			0
	 * branch greaterequal*+16 	0
	 * 
	 * R2 := n 					13 
	 * R3 := ONE 				12 
	 * R2 := R2 - R3 			1 
	 * R2 := C[R2] 				16 
	 * R3 := A[R0]				14 
	 * R2 := R2 + R3 			1 
	 * R3 := C[R2] 				16 
	 * R2 := R3 + R2 			1 
	 * B[R0] := R2 				15 - 5 dirty write[16] = true
	 * R2 := ONE 				12 
	 * R0 := R0 + R2 			1 
	 * branch*-32 				1
	 */
	public static String afterInnerLoop = "001312116141161151211";
	
	/**
	 * The page reference string at the end of loop 2
	 * 
	 * compare R0,R1 			0
	 * branch greaterequal*+32 	0
	 */
	public static String loop2end = "00";
	
	/**
	 * Parameter n
	 */
	public static int n = 11;
	
	/**
	 * 
	 * @param delta the window size
	 * @return the total number of page faults
	 */
	public static int getP(int delta) {
		char[] workingSet = new char[delta];
		Arrays.fill(workingSet, '#');
		int ret = getP(workingSet, loop1init);
		for (int i = 0; i < n; i++) ret += getP(workingSet, loop1);
		ret += getP(workingSet, loop1end);
		ret += getP(workingSet, loop2init);
		for (int i = 0; i < n; i++) {
			ret += getP(workingSet, beforeInnerLoop);
			for (int j = i; j < n; j++) ret += getP(workingSet, innerLoop);
			ret += getP(workingSet, afterInnerLoop);
		}
		ret += getP(workingSet, loop2end);
		return ret;
	}

	/**
	 * starting from an existing working set, process a string of page references and get 
	 * the number of page faults
	 * @param workingSet the initial working set, will change during the process
	 * @param ref the string of page references
	 * @return the number of page faults encountered through the string of page references
	 */
	private static int getP(char[] workingSet, String ref) {
		int ret = 0;
		for (int i = 0; i < ref.length(); i++) ret += getP(workingSet, ref.charAt(i));
		return ret;
	}

	/**
	 * starting from an existing working set, process a single page reference and get 
	 * the number of page faults (0 or 1)
	 * @param workingSet the initial working set, will be changed by the function 
	 * @param r the page reference
	 * @return the number of page faults encountered through processing 
	 * this page reference (either 0 or 1)
	 */
	private static int getP(char[] workingSet, char r) {
		int ret = 1;
		if (inSet(workingSet, r)) ret = 0;
		update(workingSet, r);
		return ret;
	}
	
	/**
	 * add a page reference to the end of the working set, delete the first one if 
	 * the working set is full
	 * @param workingSet
	 * @param ref
	 */
	private static void update(char[] workingSet, char ref) {
		int delta = workingSet.length;
		int size = 0;
		for (; size < delta && workingSet[size] != '#'; size++) ;
		if (size < delta) workingSet[size] = ref;
		else {
			for (int i = 0; i < delta-1; i++) workingSet[i] = workingSet[i+1];
			workingSet[delta-1] = ref;
		}

	}

	/**
	 * check if a set contains a character
	 * @param set the set
	 * @param ch the character
	 * @return true if it contains, false otherwise
	 */
	private static boolean inSet(char[] set, char ch) {
		for (int i = 0; i < set.length; i++) if (set[i] == ch) return true;
		return false;
	}

	/**
	 * get the average working set size
	 * @param delta the window size
	 * @return the average working set size
	 */
	public static double getAvgW(int delta) {
		char[] workingSet = new char[delta];
		Arrays.fill(workingSet, '#');
		int nsize = 0;
		int nref = 0;
		nsize += getW(workingSet, loop1init);
		nref += loop1init.length();
		for (int i = 0; i < n; i++) {
			nsize += getW(workingSet, loop1);
			nref += loop1.length();
		}
		nsize += getW(workingSet, loop1end);
		nref += loop1end.length();
		nsize += getW(workingSet, loop2init);
		nref += loop2init.length();
		for (int i = 0; i < n; i++) {
			nsize += getW(workingSet, beforeInnerLoop);
			nref += beforeInnerLoop.length();
			for (int j = i; j < n; j++) {
				nsize += getW(workingSet, innerLoop);
				nref += innerLoop.length();
			}
			nsize += getW(workingSet, afterInnerLoop);
			nref += afterInnerLoop.length();
		}
		nsize += getW(workingSet, loop2end);
		nref += loop2end.length();
		
		return (nsize+0.0)/nref;

	}

	/**
	 * get the maximum working set size
	 * @param delta the window size
	 * @return the maximum working set size
	 */
	public static double getMaxW(int delta) {
		char[] workingSet = new char[delta];
		Arrays.fill(workingSet, '#');
		int max = 0;
		int max1 = getMaxW(workingSet, loop1init);
		if (max1 > max) max = max1;
		for (int i = 0; i < n; i++) {
			max1 = getMaxW(workingSet, loop1);
			if (max1 > max) max = max1;
		}
		max1 = getMaxW(workingSet, loop1end);
		if (max1 > max) max = max1;
		max1 = getMaxW(workingSet, loop2init);
		if (max1 > max) max = max1;
		for (int i = 0; i < n; i++) {
			max1 = getMaxW(workingSet, beforeInnerLoop);
			if (max1 > max) max = max1;
			for (int j = i; j < n; j++) {
				max1 = getMaxW(workingSet, innerLoop);
				if (max1 > max) max = max1;
			}
			max1 = getMaxW(workingSet, afterInnerLoop);
			if (max1 > max) max = max1;
		}
		max1 = getMaxW(workingSet, loop2end);
		if (max1 > max) max = max1;
		
		return max;

	}

	/**
	 * starting from an existing working set, process a reference string and count 
	 * the maximum working set size during the process
	 * @param workingSet the initial working set
	 * @param ref the reference string
	 * @return the maximum working set size
	 */
	private static int getMaxW(char[] workingSet, String ref) {
		int max = 0;
		for (int i = 0; i < ref.length(); i++) {
			int max1 = getW(workingSet, ref.charAt(i));
			if (max1 > max) max = max1;
		}
		return max;
	}

	/**
	 * starting from an existing working set, process a reference string and count 
	 * the total working set size, this number is intended to be divided by the 
	 * reference string length
	 * to get the average working set size during the process
	 * @param workingSet the initial working set
	 * @param ref the reference string
	 * @return the accumulated working set size, which is intended to be divided by 
	 * the reference string length later
	 */
	private static int getW(char[] workingSet, String ref) {
		int ret = 0;
		for (int i = 0; i < ref.length(); i++) ret += getW(workingSet, ref.charAt(i));
		return ret;
	}

	/**
	 * starting from an existing working set, process a single page reference
	 * and count the resulting working set size
	 * @param workingSet the initial working set
	 * @param ch the reference to process
	 * @return the size of the resulting working set
	 */
	private static int getW(char[] workingSet, char ch) {
		update(workingSet, ch);
		HashSet<Character> set = new HashSet<Character>();
		for (int i = 0; i < workingSet.length; i++) if (workingSet[i] != '#') set.add(workingSet[i]);
		if (set.size() > 7) {
			for (int i = 0; i < workingSet.length; i++) System.out.print(workingSet[i]);
			System.out.println();
			System.exit(0);
		}
		return set.size();
	}
	
	/**
	 * get the total number of page faults under the practical working set policy
	 * @param delta the window size
	 * @return the total number of page faults
	 */
	public static int getPracP(int delta) {
		char[] workingSet = new char[delta];
		Arrays.fill(workingSet, '#');
		HashSet<Character> wset = new HashSet<Character>();
//		HashMap<Character, Boolean> dirty = new boolean[7];
//		Arrays.fill(dirty, false);
		boolean[] write = new boolean[loop1init.length()];
		Arrays.fill(write, false);
		int ret = getPracP(workingSet, wset, loop1init, write);
		write = new boolean[loop1.length()];
		Arrays.fill(write, false);
		write[6] = write[13] = write[20] = true;
		for (int i = 0; i < n; i++) {
			ret += getPracP(workingSet, wset, loop1, write);
		}
		write = new boolean[loop1end.length()];
		Arrays.fill(write, false);
		ret += getPracP(workingSet, wset, loop1end, write);
		write = new boolean[loop2init.length()];
		Arrays.fill(write, false);
		ret += getPracP(workingSet, wset, loop2init, write);
		for (int i = 0; i < n; i++) {
			write = new boolean[beforeInnerLoop.length()];
			Arrays.fill(write, false);
			ret += getPracP(workingSet, wset, beforeInnerLoop, write);
			write = new boolean[innerLoop.length()];
			Arrays.fill(write, false);
			write[11] = write[21] = true;
			for (int j = i; j < n; j++) ret += getPracP(workingSet, wset, innerLoop, write);
			write = new boolean[afterInnerLoop.length()];
			Arrays.fill(write, false);
			write[16] = true;
			ret += getPracP(workingSet, wset, afterInnerLoop, write);
		}
		write = new boolean[loop2end.length()];
		Arrays.fill(write, false);
		ret += getPracP(workingSet, wset, loop2end, write);
		return ret;
		
	}

	/**
	 * get the number of page faults under the practical policy
	 * @param workingSet the initial working set
	 * @param wset the set of pages in the main memory
	 * 
	 * @param ref the reference string to process
	 * @param write the array indicating which pages in the reference string are dirty
	 * @return the number of page faults
	 */
	private static int getPracP(char[] workingSet, HashSet<Character> wset, String ref,
			boolean[] write) {
		int ret = 0;
		for (int i = 0; i < ref.length(); i++) 
			ret += getPracP(workingSet, wset, ref.charAt(i), write, i);
		return ret;

	}

	/**
	 * get the number of page faults under the practical policy for one page reference
	 * @param workingSet the initial working set
	 * @param wset the set of pages in the main memory
	 * @param ch the page reference
	 * @param write the page writing history
	 * @param index the index of the page reference within the string of consideration
	 * @return
	 */
	private static int getPracP(char[] workingSet, HashSet<Character> wset, char ch, boolean[] write, int index) {
		int ret = 1;
		if (inSet(workingSet, ch) || wset.contains(ch)) ret = 0;
		else wset.clear();
		updatePrac(workingSet, wset, ch, write, index);
		return ret;
	}

	/**
	 * update the working set under the practical policy
	 * @param workingSet the initial working set
	 * @param wset the set of pages in the main memory
	 * @param ch the page reference to process
	 * @param write the writing history of the string of page reference
	 * @param index the index of the current page reference within the page reference string
	 */
	private static void updatePrac(char[] workingSet, HashSet<Character> wset, char ch, boolean[] write, int index) {
		int delta = workingSet.length;
		int size = 0;
		for (; size < delta && workingSet[size] != '#'; size++) ;
		if (size < delta) workingSet[size] = ch;
		else {
			char tokick = workingSet[0];
			for (int i = 0; i < delta-1; i++) workingSet[i] = workingSet[i+1];
			workingSet[delta-1] = ch;
			if (index >= delta && write[index-delta]) wset.add(tokick);
		}

	}
	
	/**
	 * solve the problems with the implemented functions
	 * @param args
	 */
	public static void main(String[] args) {
//		generateTable1tex();
//		generateTable1csv();
//		trackWSsize();

//		int[] pf1 = new int[3000];
//		int[] pf2 = new int[3000];

//		trackP(25, pf1);
//		trackP(26, pf2);
//		for (int i = 0; i < 2291; i++) if (pf1[i] > pf2[i]) System.out.println(i);

//		trackP(26, pf1);
//		trackP(27, pf2);
//		for (int i = 0; i < 2291; i++) if (pf1[i] > pf2[i]) System.out.println(i);
//
//		trackP(30, pf1);
//		trackP(31, pf2);
//		for (int i = 0; i < 2291; i++) if (pf1[i] > pf2[i]) System.out.println(i);
//		
//		trackP(35, pf1);
//		trackP(36, pf2);
//		for (int i = 0; i < 2291; i++) if (pf1[i] > pf2[i]) System.out.println(i);
		
//		generateTable2tex();
//		generateTable3tex();
//		generateTable3csv();
		
		String whole = buildWholeString();
//		generateTable4tex(whole);
		generateTable4csv(whole);
//		getOptP(0, 7, whole);
	}

	/**
	 * generate the excel data for table 4's corresponding figure in the paper
	 * @param whole
	 */
	private static void generateTable4csv(String whole) {
		int omega = loop1init.length() + loop1.length()*n + loop1end.length() + loop2init.length();
		for (int i = 0; i < n; i++) {
			omega += beforeInnerLoop.length();
			for (int j = i; j < n; j++) omega += innerLoop.length();
			omega += afterInnerLoop.length();
		}
		omega += loop2end.length();

		System.out.println("delta,n,fs,fp,fopt");
		for (int delta = 1; delta < 65; delta++) {
			double fs = (getP(delta)+0.0)/omega;
			double fp = (getPracP(delta)+0.0)/omega;
			int n = getPracMaxW(delta);
			double fopt = (getOptP(delta, n, whole)+0.0)/omega;
			System.out.printf("%d,%d,%f,%f,%f\n", 
					delta, n, fs, fp, fopt);
		}
	}

	/**
	 * generate the tex source code for table 4 in the paper
	 * @param whole
	 */
	private static void generateTable4tex(String whole) {
		int omega = loop1init.length() + loop1.length()*n + loop1end.length() + loop2init.length();
		for (int i = 0; i < n; i++) {
			omega += beforeInnerLoop.length();
			for (int j = i; j < n; j++) omega += innerLoop.length();
			omega += afterInnerLoop.length();
		}
		omega += loop2end.length();

		for (int delta = 1; delta < 65; delta++) {
			double fs = (getP(delta)+0.0)/omega;
			double fp = (getPracP(delta)+0.0)/omega;
			int n = getPracMaxW(delta);
			double fopt = (getOptP(delta, n, whole)+0.0)/omega;
			System.out.printf("%d & %d & %.2f & %.2f & %.2f\\\\\n", 
					delta, n, fs, fp, fopt);
		}

	}

	/**
	 * build the entire reference string
	 * @return
	 */
	private static String buildWholeString() {
		String ret = loop1init;
		for (int i = 0; i < n; i++) ret += loop1;
		ret += loop1end;
		ret += loop2init;
		for (int i = 0; i < n; i++) {
			ret += beforeInnerLoop;
			for (int j = i; j < n; j++) ret += innerLoop;
			ret += afterInnerLoop;
		}
		ret += loop2end;
		return ret;

	}

	/**
	 * generate the excel data for table 3's corresponding figure in the paper
	 */
	private static void generateTable3csv() {
		int omega = loop1init.length() + loop1.length()*n + loop1end.length() + loop2init.length();
		for (int i = 0; i < n; i++) {
			omega += beforeInnerLoop.length();
			for (int j = i; j < n; j++) omega += innerLoop.length();
			omega += afterInnerLoop.length();
		}
		omega += loop2end.length();

		System.out.println("delta,fs,fp");
		for (int delta = 1; delta < 65; delta++) {
			double fs = (getP(delta)+0.0)/omega;
			double fp = (getPracP(delta)+0.0)/omega;
			System.out.printf("%d,%f,%f\n", 
					delta, fs, fp);

		}

	}

	/**
	 * generate the tex source code for table 3 in the paper
	 */
	private static void generateTable3tex() {
		int omega = loop1init.length() + loop1.length()*n + loop1end.length() + loop2init.length();
		for (int i = 0; i < n; i++) {
			omega += beforeInnerLoop.length();
			for (int j = i; j < n; j++) omega += innerLoop.length();
			omega += afterInnerLoop.length();
		}
		omega += loop2end.length();

		for (int delta = 1; delta < 65; delta++) {
			double fs = (getP(delta)+0.0)/omega;
			double fp = (getPracP(delta)+0.0)/omega;
			double wsmax = getMaxW(delta);
			double wpmax = getPracMaxW(delta);
			double wsavg = getAvgW(delta);
			double wpavg = getPracAvgW(delta);
			System.out.printf("%d & %.2f & %.2f & %.2f & %.2f & %.2f & %.2f\\\\\n", 
					delta, fs, fp, wsmax, wpmax, wsavg, wpavg);

		}
	}

	/**
	 * generate the tex source code for table 2 in the paper - avg and max frames assigned to the program
	 */
	private static void generateTable2tex() {
		System.out.println("$\\Delta$ & $W_{avg}$ & $W_{max}$\\\\");
		System.out.println("\\hline");
		for (int delta = 1; delta < 65; delta++) {
			double wavg = getAvgW(delta);
			double wmax = getMaxW(delta);
			System.out.printf("%d & %.2f & %.2f\\\\\n", delta, wavg, wmax);
		}

	}

	/**
	 * track the page faults to explain the knees
	 * @param delta the window size
	 */
	private static void trackP(int delta, int[] pf) {
		char[] workingSet = new char[delta];
		Arrays.fill(workingSet, '#');
		int start = 0;
		trackP(workingSet, pf, start, loop1init);
		start += loop1init.length();
		for (int i = 0; i < n; i++) {
			trackP(workingSet, pf, start, loop1);
			start += loop1.length();
		}
		trackP(workingSet, pf, start, loop1end);
		start += loop1end.length();
		trackP(workingSet, pf, start, loop2init);
		start += loop2init.length();
		for (int i = 0; i < n; i++) {
			trackP(workingSet, pf, start, beforeInnerLoop);
			start += beforeInnerLoop.length();
			for (int j = i; j < n; j++) {
				trackP(workingSet, pf, start, innerLoop);
				start += innerLoop.length();
			}
			trackP(workingSet, pf, start, afterInnerLoop);
			start += afterInnerLoop.length();
		}
		trackP(workingSet, pf, start, loop2end);
		start += loop2end.length();

	}

	/**
	 * track page faults, record them in an array
	 * @param workingSet the initial working set
	 * @param pf page fault array
	 * @param start starting point of recording
	 * @param ref reference string to process
	 */
	private static void trackP(char[] workingSet, int[] pf, int start,
			String ref) {
		for (int i = 0; i < ref.length(); i++) trackP(workingSet, pf, start+i, ref.charAt(i));
	}

	/**
	 * track page fault record it in the array
	 * @param workingSet te initial working set
	 * @param pf page fault array
	 * @param start the recording point
	 * @param ch the current page reference
	 */
	private static void trackP(char[] workingSet, int[] pf, int start,
			char ch) {
		if (inSet(workingSet, ch)) pf[start] = 0;
		else pf[start] = 1;
		update(workingSet, ch);

	}

	/**
	 * track the size change of the working set to explain the major knees
	 */
	private static void trackWSsize() {
		char[] workingSet = new char[64];
		Arrays.fill(workingSet, '#');
		printW(workingSet, loop1init);
		for (int i = 0; i < n; i++) printW(workingSet, loop1);
		printW(workingSet, loop1end);
		printW(workingSet, loop2init);
		for (int i = 0; i < n; i++) {
			printW(workingSet, beforeInnerLoop);
			for (int j = i; j < n; j++) printW(workingSet, innerLoop);
			printW(workingSet, afterInnerLoop);
		}
		printW(workingSet, loop2end);
	}

	/**
	 * print the working set size change during the process of a reference string
	 * @param workingSet initial working set
	 * @param ref reference string
	 */
	private static void printW(char[] workingSet, String ref) {
		for (int i = 0; i < ref.length(); i++) printW(workingSet, ref.charAt(i));
	}

	/**
	 * print the working set size after a new page comes
	 * @param workingSet the initial working set
	 * @param ch the new page reference
	 */
	private static void printW(char[] workingSet, char ch) {
		update(workingSet, ch);
		HashSet<Character> set = new HashSet<Character>();
		for (int i = 0; i < workingSet.length; i++) if (workingSet[i] != '#') set.add(workingSet[i]);
		System.out.println(set.size());
	}

	/**
	 * generate the content for excel file
	 */
	private static void generateTable1csv() {
		System.out.println("delta,p,w,1/f");
		int omega = loop1init.length() + loop1.length()*n + loop1end.length() + loop2init.length();
//		System.out.println(omega);
		for (int i = 0; i < n; i++) {
			omega += beforeInnerLoop.length();
			for (int j = i; j < n; j++) omega += innerLoop.length();
			omega += afterInnerLoop.length();
//			System.out.println(omega);
		}
		omega += loop2end.length();
//		System.out.println(omega); // omega: 2291
		for (int delta = 1; delta < 65; delta++) {
			int p = getP(delta);
			System.out.println(delta + "," + p + "," + getAvgW(delta) + "," + (omega+0.0)/p);
		}
	}

	/**
	 * generate the table content for question 2 for tex file
	 */
	private static void generateTable1tex() {
		System.out.println("$\\Delta$ & $P(\\Delta)$ & $W(\\Delta)$ & $1/F(\\Delta)$\\\\");
		System.out.println("\\hline");
		int omega = loop1init.length() + loop1.length()*n + loop1end.length() + loop2init.length();
		for (int i = 0; i < n; i++) {
			omega += beforeInnerLoop.length();
			for (int j = i; j < n; j++) omega += innerLoop.length();
			omega += afterInnerLoop.length();
		}
		omega += loop2end.length();
					
		for (int delta = 1; delta < 65; delta++) {
			int p = getP(delta);
			System.out.printf("%d & %d & %.2f & %.2f\\\\\n", delta, p, getAvgW(delta), 
					(omega+0.0)/p);
		}
	}
	
	/**
	 * get the maximum number of assigned frames under the practical working set policy
	 * @param delta the window size
	 * @return the maximum number of page faults
	 */
	public static int getPracMaxW(int delta) {
		char[] workingSet = new char[delta];
		Arrays.fill(workingSet, '#');
		HashSet<Character> wset = new HashSet<Character>();
//		HashMap<Character, Boolean> dirty = new boolean[7];
//		Arrays.fill(dirty, false);
		int max = 0;
		boolean[] write = new boolean[loop1init.length()];
		Arrays.fill(write, false);
		int max1 = getPracMaxW(workingSet, wset, loop1init, write);
		if (max1 > max) max = max1;
		write = new boolean[loop1.length()];
		Arrays.fill(write, false);
		write[6] = write[13] = write[20] = true;
		for (int i = 0; i < n; i++) {
			max1 = getPracMaxW(workingSet, wset, loop1, write);
			if (max1 > max) max = max1;
		}
		write = new boolean[loop1end.length()];
		Arrays.fill(write, false);
		max1 = getPracMaxW(workingSet, wset, loop1end, write);
		if (max1 > max) max = max1;
		write = new boolean[loop2init.length()];
		Arrays.fill(write, false);
		max1 = getPracMaxW(workingSet, wset, loop2init, write);
		if (max1 > max) max = max1;
		for (int i = 0; i < n; i++) {
			write = new boolean[beforeInnerLoop.length()];
			Arrays.fill(write, false);
			max1 = getPracMaxW(workingSet, wset, beforeInnerLoop, write);
			if (max1 > max) max = max1;
			write = new boolean[innerLoop.length()];
			Arrays.fill(write, false);
			write[11] = write[21] = true;
			for (int j = i; j < n; j++) {
				max1 = getPracMaxW(workingSet, wset, innerLoop, write);
				if (max1 > max) max = max1;
			}
			write = new boolean[afterInnerLoop.length()];
			Arrays.fill(write, false);
			write[16] = true;
			max1 = getPracMaxW(workingSet, wset, afterInnerLoop, write);
			if (max1 > max) max = max1;
		}
		write = new boolean[loop2end.length()];
		Arrays.fill(write, false);
		max1 = getPracMaxW(workingSet, wset, loop2end, write);
		if (max1 > max) max = max1;
		return max;
	}

	/**
	 * get the maximum number of frames assigned to a program under the practical policy after handling 
	 * a snippet of page reference string
	 * @param workingSet
	 * @param wset
	 * @param ref
	 * @param write
	 * @return
	 */
	private static int getPracMaxW(char[] workingSet, HashSet<Character> wset, String ref, boolean[] write) {
		int max = 0;
		for (int i = 0; i < ref.length(); i++) {
			int max1 = getPracW(workingSet, wset, ref.charAt(i), write, i);
			if (max1 > max) max = max1;
		}
		return max;
	}

	/**
	 * get the number of frames assigned to a program under the practical policy after handling a page reference
	 * @param workingSet
	 * @param wset
	 * @param ch
	 * @param write
	 * @param index
	 * @return
	 */
	private static int getPracW(char[] workingSet, HashSet<Character> wset, char ch, boolean[] write, int index) {
		updatePrac(workingSet, wset, ch, write, index);
		HashSet<Character> set = new HashSet<Character>();
		for (int i = 0; i < workingSet.length; i++) if (workingSet[i] != '#') set.add(workingSet[i]);
		set.addAll(wset);
		return set.size();

	}

	/**
	 * get average number of assigned frames under the practical policy
	 * @param delta
	 * @return
	 */
	public static double getPracAvgW(int delta) {
		char[] workingSet = new char[delta];
		Arrays.fill(workingSet, '#');
		HashSet<Character> wset = new HashSet<Character>();
//		HashMap<Character, Boolean> dirty = new boolean[7];
//		Arrays.fill(dirty, false);
		int sum = 0;
		int nref = 0;
		boolean[] write = new boolean[loop1init.length()];
		Arrays.fill(write, false);
		sum += getPracW(workingSet, wset, loop1init, write);
		nref += loop1init.length();
		write = new boolean[loop1.length()];
		Arrays.fill(write, false);
		write[6] = write[13] = write[20] = true;
		for (int i = 0; i < n; i++) {
			sum += getPracW(workingSet, wset, loop1, write);
			nref += loop1.length();
		}
		write = new boolean[loop1end.length()];
		Arrays.fill(write, false);
		sum += getPracW(workingSet, wset, loop1end, write);
		nref += loop1end.length();
		write = new boolean[loop2init.length()];
		Arrays.fill(write, false);
		sum += getPracW(workingSet, wset, loop2init, write);
		nref += loop2init.length();
		for (int i = 0; i < n; i++) {
			write = new boolean[beforeInnerLoop.length()];
			Arrays.fill(write, false);
			sum += getPracW(workingSet, wset, beforeInnerLoop, write);
			nref += beforeInnerLoop.length();
			write = new boolean[innerLoop.length()];
			Arrays.fill(write, false);
			write[11] = write[21] = true;
			for (int j = i; j < n; j++) {
				sum += getPracW(workingSet, wset, innerLoop, write);
				nref += innerLoop.length();
			}
			write = new boolean[afterInnerLoop.length()];
			Arrays.fill(write, false);
			write[16] = true;
			sum += getPracW(workingSet, wset, afterInnerLoop, write);
			nref += afterInnerLoop.length();
		}
		write = new boolean[loop2end.length()];
		Arrays.fill(write, false);
		sum += getPracW(workingSet, wset, loop2end, write);
		nref += loop2end.length();
		return (sum+0.0)/nref;
	}

	/**
	 * get the accumulated working set size for a snippet of the reference string
	 * @param workingSet
	 * @param wset
	 * @param ref
	 * @param write
	 * @return
	 */
	private static int getPracW(char[] workingSet, HashSet<Character> wset,
			String ref, boolean[] write) {
		int sum = 0;
		for (int i = 0; i < ref.length(); i++) sum += getPracW(workingSet, wset, ref.charAt(i), write, i);
		return sum;
	}
	
	/**
	 * get the total page fault number for the OPT policy
	 * @param delta used to calculate the memory size
	 * @param n the memory size
	 * @param ref the reference string
	 * @return
	 */
	public static int getOptP(int delta, int n, String ref) {
//		if (n == 7) return 7;
		HashSet<Character> ws = new HashSet<Character>();
		int ret = 0;
		for (int i = 0; i < ref.length(); i++) ret += getOptP(ws, n, ref, i);
		return ret;

	}

	/**
	 * decide whether a page fault will happen at a certain time
	 * @param workingSet
	 * @param n
	 * @param ref
	 * @param current the current character index
	 * @return
	 */
	private static int getOptP(HashSet<Character> workingSet, int n, String ref, int current) {
//		if (current >= 282) {
//			System.out.println("start debug");
//		}
		int ret = 1;
		int size = workingSet.size();
		char c = ref.charAt(current);
		boolean b = workingSet.contains(c);
		if (b) ret = 0;
//		else System.out.println(current);
		updateOpt(workingSet, n, ref, current);
		return ret;
	}

	/**
	 * update memory content with OPT policy
	 * @param workingSet
	 * @param n
	 * @param ref
	 * @param current
	 */
	private static void updateOpt(HashSet<Character> workingSet, int n, String ref, int current) {
		int size = workingSet.size();
		if (size < n || workingSet.contains(ref.charAt(current))) {
			char c = ref.charAt(current);
			workingSet.add(c);
			size = workingSet.size();
		}
		else {
			char tokick = '#';
			int distance = 0;
			for (char c : workingSet) {
				int d = getDistance(ref, current, c);
				if (d > distance) {
					distance = d;
					tokick = c;
				}
			}
			if (tokick != '#') {
				workingSet.remove(tokick);
				workingSet.add(ref.charAt(current));
			}
		}
	}

	/**
	 * get how far away the next reference of a page will happen
	 * @param ref
	 * @param current
	 * @param c
	 * @return
	 */
	private static int getDistance(String ref, int current, char c) {
		int ret;
		for (ret = 1; ret < ref.length()-current; ret++) {
			if (ref.charAt(current+ret) == c) return ret;
		}
		return 3000;
	}
}
