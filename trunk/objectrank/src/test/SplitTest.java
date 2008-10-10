package test;

public class SplitTest {

	public static void main(String[] args) {
		String line = "0-0-1-3$$$";
		String content = line.split("\\$\\$\\$")[0];
		System.out.println(line.endsWith("$$$"));
	}
}
