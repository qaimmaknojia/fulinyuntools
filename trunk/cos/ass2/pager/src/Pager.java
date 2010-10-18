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
		max1 = getW(workingSet, loop2end);
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
//		boolean[] dirty = new boolean[7];
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
	 * @param ref the reference string to process
	 * @param write the array indicating which pages in the reference string are dirty
	 * @return the number of page faults
	 */
	private static int getPracP(char[] workingSet, HashSet<Character> wset, String ref,
			boolean[] write) {
		int ret = 0;
		for (int i = 0; i < ref.length(); i++) ret += getPracP(workingSet, wset, ref.charAt(i), write, i);
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
		if (inSet(workingSet, ch) || write[index]) ret = 0;
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
			if (write[index-delta]) wset.add(tokick);
		}

	}

}
