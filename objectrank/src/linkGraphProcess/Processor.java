package linkGraphProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;

public class Processor {

	public static String linkFrom = "\\\\poseidon\\team\\Semantic Search\\data\\wikipedia data\\entityGraphLinkFrom";
	public static String linkTo = "\\\\poseidon\\team\\Semantic Search\\data\\wikipedia data\\entityGraphLinkTo";
	public static int maxLine = 1200000;
	public static String temp = "E:\\temp.txt";
	
	public static void toID(String input, String map) throws Exception {
		HashSet<String> entityName = new HashSet<String>();
		addToMap(entityName, input, maxLine);
		PrintWriter pw = new PrintWriter(new FileWriter(map));
		int id = 0;
		for (String e : entityName) {
			pw.println(e + "\t" + id);
			id++;
		}
		pw.close();
		addToFile(entityName, input, maxLine, temp, map, id);
	}
	
	public static void addToFile(HashSet<String> entityName, String input, int startLine, String temp, String map, int startId) throws Exception {
		new File(temp).createNewFile();
		BufferedReader br = new BufferedReader(new FileReader(input));
		PrintWriter mapPw = new PrintWriter(new FileWriter(map, true));
		for (int i = 0; i < startLine; i++) br.readLine();
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String content = line.split("\\$\\$\\$")[0];
			String[] part = content.split("\t");
			for (String entity : part) if (!entityName.contains(entity) && !containedInFile(temp, entity)) {
				PrintWriter pw = new PrintWriter(new FileWriter(temp, true));
				pw.println(entity);
				pw.close();
				mapPw.println(entity + "\t" + startId);
				startId++;
			}
		}
		mapPw.close();
	}
	
	public static boolean containedInFile(String filename, String str) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			if (line.equals(str)) {
				br.close();
				return true;
			}
		}
		return false;
	}
	
	public static void addToMap(HashSet<String> entityName, String input, int maxLine) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(input));
		int lineCount = 0;
		for (String line = br.readLine(); line != null && lineCount <= maxLine; line = br.readLine()) {
			while (!line.endsWith("$$$")) line += br.readLine();
			String content = line.split("\\$\\$\\$")[0];
			String[] part = content.split("\t");
			for (String entity : part) entityName.add(entity);
			lineCount++;
			if (lineCount % 100000 == 0) System.out.println(lineCount);
		}
		System.out.println(lineCount);
		System.out.println();
		br.close();
	}
	
	public static void main(String[] args) throws Exception {
//		toID(linkFrom, linkTo, "E:\\users\\fulinyun\\objectrank\\info\\name2id.txt");
//		System.out.println("link from");
//		checkFile(linkFrom);
//		System.out.println("link to");
//		checkFile(linkTo);
		toID(linkFrom, "E:\\users\\fulinyun\\objectrank\\info\\name2id.txt");
	}
	
	public static void checkFile(String input) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(input));
		int lineCount = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			lineCount++;
			if (!line.endsWith("$$$")) {
				System.out.println(lineCount);
				System.out.println(line);
			}
		}
		br.close();
	}
}
