import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class BallFinder {

	public static int LIGHT = 1; // maybe light or good
	public static int HEAVY = 2; // maybe heavy or good
	public static int GOOD = 3; // the ball is good
	public static int BAD = 4; // maybe bad or good

	public static long count = 0;
	
	public static void main(String[] args) {
		// testPermutation(13, 8);
		find(4, 2);
	}

	public static void testPermutation(int n, int k) {
		int[] onbalance = new int[k];
		for (int i = 0; i < k; i++)
			onbalance[i] = i;
		Scanner sc = new Scanner(System.in);
		do {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < onbalance.length; j++)
					System.out.print(onbalance[j] + " ");
				System.out.println();
				add1(onbalance, n);
			}
		} while (!sc.nextLine().startsWith("exit"));
	}

	/**
	 * among n balls, there is one bad ball, either lighter or heavier than all
	 * the other balls, use the balance for at most k times to find the bad
	 * ball.
	 * 
	 * @param n
	 * @param k
	 */
	public static void find(int n, int k) {
		int[] p = new int[n];
		Arrays.fill(p, BAD);
		Exp root = new Exp();
		if (find(n, k, p, n / 2, root)) {
			printTree(root, 0);
		}
	}

	private static void printTree(Exp node, int layer) {
		for (int i = 0; i < layer; i++) System.out.print("\t");
		if (node == null) System.out.println("impossible");
		else if (node.onbalance.length == 1) System.out.println(node.onbalance[0]);
		else {
			printbalance(node.onbalance);
			for (int i = 0; i < layer; i++) System.out.print("\t");
			System.out.println("if left heavier:");
			printTree(node.left, layer+1);
			for (int i = 0; i < layer; i++) System.out.print("\t");
			System.out.println("if even:");
			printTree(node.even, layer+1);
			for (int i = 0; i < layer; i++) System.out.print("\t");
			System.out.println("if right heavier:");
			printTree(node.right, layer+1);
		}
	}

	/**
	 * 
	 * @param n
	 * @param k
	 * @param p
	 *            current status of the balls
	 * @param max
	 *            max number of balls on one side of the balance
	 * @return
	 */
	private static boolean find(int n, int k, int[] p, int max, Exp current) { 
		if (found(p)) { // only one bad ball left, store it in onbalance[0]
			current.onbalance = new int[1];
			for (int i = 0; i < p.length; i++) if (p[i] != GOOD) {
				current.onbalance[0] = i;
				break;
			}
			return true;
		}
		if (k == 0) // no more experiment, failed
			return false;
		for (int i = 1; i <= max; i++) {
			current.onbalance = new int[2 * i];
			for (int j = 0; j < 2 * i; j++)
				current.onbalance[j] = j;
			while (true) {
				int[] temp = new int[p.length];
				for (int l = 0; l < p.length; l++)
					temp[l] = p[l];

				if (experiment(current.onbalance, p, -1, k)) {
					current.left = new Exp();
					if (!find(n, k - 1, p, i, current.left)) {
						for (int l = 0; l < p.length; l++)
							p[l] = temp[l];
						if (!add1(current.onbalance, n))
							break;
						else
							continue;
					} else {
						for (int l = 0; l < p.length; l++)
							p[l] = temp[l];
					}
				} else {
					for (int l = 0; l < p.length; l++)
						p[l] = temp[l];					
				}
				
				if (experiment(current.onbalance, p, 0, k)) {
					current.even = new Exp();
					if (!find(n, k - 1, p, i, current.even)) {
						for (int l = 0; l < p.length; l++)
							p[l] = temp[l];
						if (!add1(current.onbalance, n))
							break;
						else
							continue;
					} else {
						for (int l = 0; l < p.length; l++)
							p[l] = temp[l];
					}
				} else {
					for (int l = 0; l < p.length; l++)
						p[l] = temp[l];					
				}

				if (experiment(current.onbalance, p, 1, k)) { 
					current.right = new Exp();
					if (!find(n, k - 1, p, i, current.right)) {
						for (int l = 0; l < p.length; l++)
							p[l] = temp[l];
						if (!add1(current.onbalance, n))
							break;
						else
							continue;						
					} else {
						for (int l = 0; l < p.length; l++)
							p[l] = temp[l];
					}
				} else {
					for (int l = 0; l < p.length; l++)
						p[l] = temp[l];
				}

				// if reach here, we have found a solution!
//				printbalance(onbalance, k);
				return true;
			}
		}
//		System.out.println("enumerated all at " + k);
		return false;
	}

	private static void printbalance(int[] onbalance) {
		int side = onbalance.length/2;
		for (int i = 0; i < side; i++) {
			System.out.print(onbalance[i] + " ");
		}
		System.out.print("vs. ");
		for (int i = side; i < side*2; i++) {
			System.out.print(onbalance[i] + " ");
		}
		System.out.println();
//		System.out.println("-- " + layer);
	}

	/**
	 * enumerate the permutation on the balance
	 * 
	 * @param onbalance
	 * @return
	 */
	private static boolean add1(int[] onbalance, int n) {
		count++;
		if (count%100000000 == 0) System.out.println(new Date().toString() + " : " + count);
		int[] temp = new int[n];
		boolean[] used = new boolean[n];
		Arrays.fill(used, false);
		for (int i = 0; i < onbalance.length; i++) {
			temp[i] = onbalance[i];
			used[temp[i]] = true;
		}
		int j = onbalance.length;
		for (int i = n - 1; i >= 0; i--)
			if (!used[i])
				temp[j++] = i;
		for (j = n - 1; j >= 1; j--)
			if (temp[j - 1] < temp[j])
				break;
		if (j == 0)
			return false;
		for (int i = n - 1; i >= 0; i--) {
			if (temp[i] > temp[j - 1]) {
				int t = temp[i];
				temp[i] = temp[j - 1];
				temp[j - 1] = t;
				break;
			}
		}
		for (int i = j, k = n - 1; i < k; i++, k--) {
			int t = temp[i];
			temp[i] = temp[k];
			temp[k] = t;
		}
		for (int i = 0; i < onbalance.length; i++)
			onbalance[i] = temp[i];
		return true;
	}

	/**
	 * do one experiment, change status according to the result
	 * 
	 * @param onbalance
	 * @param p
	 * @param result
	 *            : -1 - left heavier; 0 - even; 1 - right heavier
	 * @return if the experimental result is possible
	 */
	private static boolean experiment(int[] onbalance, int[] p, int result, int layer) {
//		System.out.print("performing: ");
//		printbalance(onbalance, layer);
//		System.out.println("result is " + result);
		
		int side = onbalance.length / 2;
		boolean[] exp = new boolean[p.length];
		switch (result) {
		case -1: // left heavier
			for (int i = 0; i < side; i++) { // left side
				if (p[onbalance[i]] == LIGHT)
					p[onbalance[i]] = GOOD;
				else if (p[onbalance[i]] == BAD)
					p[onbalance[i]] = HEAVY;
			}
			for (int i = side; i < side * 2; i++) { // right side
				if (p[onbalance[i]] == HEAVY)
					p[onbalance[i]] = GOOD;
				else if (p[onbalance[i]] == BAD)
					p[onbalance[i]] = LIGHT;
			}
			// others
			Arrays.fill(exp, false);
			for (int i = 0; i < side*2; i++) exp[onbalance[i]] = true;
			for (int i = 0; i < p.length; i++) if (!exp[i]) p[i] = GOOD;
			
			for (int i = 0; i < side * 2; i++)
				if (p[onbalance[i]] != GOOD)
					return true;
//			System.out.println("impossible");
			return false;
		case 0: // even
			for (int i = 0; i < side*2; i++)
				p[onbalance[i]] = GOOD;
			for (int i = 0; i < p.length; i++)
				if (p[i] != GOOD)
					return true;
//			System.out.println("impossible");
			return false;
		case 1: // right heavier
			for (int i = 0; i < side; i++) { // left side
				if (p[onbalance[i]] == HEAVY)
					p[onbalance[i]] = GOOD;
				else if (p[onbalance[i]] == BAD)
					p[onbalance[i]] = LIGHT;
			}
			for (int i = side; i < side * 2; i++) { // right side
				if (p[onbalance[i]] == LIGHT)
					p[onbalance[i]] = GOOD;
				else if (p[onbalance[i]] == BAD)
					p[onbalance[i]] = HEAVY;
			}
			// others
			Arrays.fill(exp, false);
			for (int i = 0; i < side*2; i++) exp[onbalance[i]] = true;
			for (int i = 0; i < p.length; i++) if (!exp[i]) p[i] = GOOD;

			for (int i = 0; i < side * 2; i++)
				if (p[onbalance[i]] != GOOD)
					return true;
//			System.out.println("impossible");
			return false;
		}
		System.err.println("!!!Error: should never reach here!!!");
		System.exit(1);
		return true;
	}

	/**
	 * check if the bad ball has been found
	 * 
	 * @param p
	 * @return
	 */
	private static boolean found(int[] p) {
		int unknown = 0;
		for (int i = 0; i < p.length; i++)
			if (p[i] != GOOD)
				unknown++;
		return unknown == 1;
	}

}

class Exp {
	public int[] onbalance;
	Exp parent; // the last experimental result
	Exp left; // left heavier branch
	Exp even; // even branch
	Exp right; // right heavier branch
}
