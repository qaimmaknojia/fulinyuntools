package baseline;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import annotate.WebPageExp;

public class TFProcessor {
	
//	public static String projectFolder = "E:\\eclipse_workspace\\objectrank\\";
	public static String projectFolder = "E:\\users\\fulinyun\\objectrank\\";

	public static void main(String[] args) throws Exception {
//		String[] topnames = selectTop(projectFolder + "res\\CognitiveScience.htm", 10);
//		String[] topnames = selectTop("http://en.wikipedia.org/wiki/Cognitive_science", 10);
//		for (String s : topnames) System.out.println(s);
		File[] fn = new File(projectFolder+"res\\").listFiles(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().endsWith(".htm");
			}
		});
		PrintWriter pw = new PrintWriter(new FileWriter(projectFolder+"tuning\\baseline.txt", true));
		for (int i = 0; i < fn.length; i++) {
			File f = fn[i];
			String[] names = selectTop(f.getAbsolutePath(), 10);
			pw.println(f.getName());
			for (String s : names) pw.println(s);
			pw.println();
		}
		pw.close();
	}
	
	public static String[] selectTop(String url, int topNum) {
		String[] names = WebPageExp.recognize(url);
		HashMap<String, Integer> tf = new HashMap<String, Integer>();
		for (String s : names) {
			if (tf.containsKey(s)) tf.put(s, tf.get(s)+1);
			else tf.put(s, 1);
		}
		TreeMap<Integer, ArrayList<String>> sortMap = new TreeMap<Integer, ArrayList<String>>(new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				if (a > b) return -1;
				if (a == b) return 0;
				return 1;
			}
		});
		for (String s : tf.keySet()) {
			Integer key = tf.get(s);
			if (sortMap.containsKey(key)) sortMap.get(key).add(s);
			else {
				ArrayList<String> value = new ArrayList<String>();
				value.add(s);
				sortMap.put(key, value);
			}
		}
		String[] ret = new String[topNum];
		int count = 0;
		for (Integer i : sortMap.keySet()) {
			ArrayList<String> list = sortMap.get(i);
			for (String s : list) {
				ret[count] = s;
				count++;
				if (count == topNum) break;
			}
			if (count == topNum) break;
		}
		return ret;
	}
}
