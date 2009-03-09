import java.io.File;
import java.util.Scanner;


public class FileSearcher {

	public static String root = "d:\\files\\pay as you go matching\\";
	
	public static void mainSearch() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			searchFile(root, sc.nextLine());
			System.out.println("*****************************************");
			System.out.println();
		}
	}
	
	public static void searchFile(String root, String key) {
		File all = new File(root);
		File[] files = all.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				searchFile(f.getAbsolutePath(), key);
			} else if (f.getName().toLowerCase().contains(key.toLowerCase())) {
				System.out.println(f.getAbsolutePath());
			}
		}
	}
	
}
