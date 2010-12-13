import java.util.Arrays;

public class FindFourSame {

	public static boolean can24(int n) {
		int[] operon = new int[4];
		Arrays.fill(operon, n);
		return can24(operon, 4);
	}

	private static boolean can24(int[] operon, int r) {
		if (r == 1) return (operon[0] == 24);

		int[] temp = Arrays.copyOf(operon, r);

		for (int i = 0; i < r; i++)	for (int j = i + 1; j < r; j++) {
			operon = Arrays.copyOf(temp, r);
			operon[i] += operon[j];
			for (int k = j; k < r-1; k++) operon[k] = operon[k+1];
			if (can24(operon, r - 1)) {
				for (int k = 0; k < r - 1; k++)
					System.out.print(" " + operon[k]);
				System.out.println();
				System.out.println(temp[i] + " + " + temp[j] + " = "
						+ operon[i]);
				return true;
			}

			operon = Arrays.copyOf(temp, r);
			operon[i] -= operon[j];
			for (int k = j; k < r-1; k++) operon[k] = operon[k+1];
			if (can24(operon, r - 1)) {
				for (int k = 0; k < r - 1; k++)
					System.out.print(" " + operon[k]);
				System.out.println();
				System.out.println(temp[i] + " - " + temp[j] + " = "
						+ operon[i]);
				return true;
			}

			operon = Arrays.copyOf(temp, r);
			operon[i] = operon[j] - operon[i];
			for (int k = j; k < r-1; k++) operon[k] = operon[k+1];
			if (can24(operon, r - 1)) {
				for (int k = 0; k < r - 1; k++)
					System.out.print(" " + operon[k]);
				System.out.println();
				System.out.println(temp[j] + " - " + temp[i] + " = "
						+ operon[i]);
				return true;
			}

			operon = Arrays.copyOf(temp, r);
			operon[i] *= operon[j];
			for (int k = j; k < r-1; k++) operon[k] = operon[k+1];
			if (can24(operon, r - 1)) {
				for (int k = 0; k < r - 1; k++)
					System.out.print(" " + operon[k]);
				System.out.println();
				System.out.println(temp[i] + " * " + temp[j] + " = "
						+ operon[i]);
				return true;
			}

			operon = Arrays.copyOf(temp, r);
			if (operon[j] != 0 && operon[i] % operon[j] == 0) {
				operon[i] /= operon[j];
				for (int k = j; k < r-1; k++) operon[k] = operon[k+1];
				if (can24(operon, r - 1)) {
					for (int k = 0; k < r - 1; k++)
						System.out.print(" " + operon[k]);
					System.out.println();
					System.out.println(temp[i] + " / " + temp[j] + " = "
							+ operon[i]);
					return true;
				}
			}
			
			operon = Arrays.copyOf(temp, r);
			if (operon[i] != 0 && operon[j] % operon[i] == 0) {
				operon[i] = operon[j]/operon[i];
				for (int k = j; k < r-1; k++) operon[k] = operon[k+1];
				if (can24(operon, r - 1)) {
					for (int k = 0; k < r - 1; k++)
						System.out.print(" " + operon[k]);
					System.out.println();
					System.out.println(temp[j] + " / " + temp[i] + " = "
							+ operon[i]);
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		int[] todo = new int[]{1,2,3,4};
		can24(todo, 4);
		for (int i = 0; i < 4; i++) System.out.print(" " + todo[i]);
		System.out.println();
	}

	public static void find4same() {
		for (int i = 1; i < 49; i++) {
			System.out.println(i + " : ");
			if (can24(i)) System.out.println(" " + i + " " + i + " " + i + " " + i);
			System.out.println("\n***********\n");
		}

	}
	
	public static void testArraycopy() {
		int[] origin = { 1, 2, 3, 4, 5 };
		for (int i = 0; i < origin.length; i++) {
			int[] temp = Arrays.copyOf(origin, i + 1);
			for (int j = 0; j < temp.length; j++) {
				System.out.print(" " + temp[j]);
			}
			System.out.println();
		}
	}
}
