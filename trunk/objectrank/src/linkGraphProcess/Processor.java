package linkGraphProcess;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class Processor {

	public static String linkFrom = "\\\\poseidon\\team\\Semantic Search\\data\\wikipedia data\\entityGraphLinkFrom";
	public static int maxLine = 1200000;
	public static String temp = "E:\\temp.txt";
	
	// PR(A) = (1-d)b + d(PR(t1)/C(t1) + ... + PR(tn)/C(tn))
	public static TreeMap<Integer, Double> calculatePR(double initialPR, int numIteration, 
			String linkFromMatrix, String outlinkNum, HashSet<Integer> inPage) throws Exception {
		
	}
	
	public static void getOutlinkNum(String linkFromMatrix, String outlinkNum) throws Exception {
		long length = new File(linkFromMatrix).length();
		FileChannel fc = new RandomAccessFile(linkFromMatrix, "r").getChannel();
		MappedByteBuffer matrix = fc.map(FileChannel.MapMode.READ_ONLY, 0, length);
		HashMap<Integer, Integer> outlink = new HashMap<Integer, Integer>();
		int num = matrix.getInt();
		for (int i = 0; i < num; i++) {
			matrix.getInt();
			int numFrom = matrix.getInt();
			for (int j = 0; j < numFrom; j++) {
				int id = matrix.getInt();
				if (!outlink.keySet().contains(id)) outlink.put(id, 1);
				else outlink.put(id, outlink.get(id)+1);
			}
		}
		fc.close();
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(outlinkNum));
		dos.writeInt(num);
		for (Integer i : outlink.keySet()) {
			dos.writeInt(i.intValue());
			dos.write(outlink.get(i).intValue());
		}
		dos.close();
	}
	
	public static void getIdMatrix(String input, String map, String output) throws Exception {
		HashMap<String, Integer> idmap = loadIdMap(map);
		BufferedReader br = new BufferedReader(new FileReader(input));
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(output));
		int lineCount = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			lineCount++;
		}
		br.close();
		dos.writeInt(lineCount);
		br = new BufferedReader(new FileReader(input));
		for (int l = 0; l < lineCount; l++) {
			String line = br.readLine();
			String content = line.split("\\$\\$\\$")[0];
			String[] part = content.split("\t");
			dos.writeInt(idmap.get(part[0]));
			dos.writeInt(part.length-1);
			for (int i = 1; i < part.length; i++) dos.writeInt(idmap.get(part[i]));
		}
		dos.close();
		br.close();
	}
	
	private static HashMap<String, Integer> loadIdMap(String map) throws Exception {
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(map));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] part = line.split("\t");
			ret.put(part[0], Integer.parseInt(part[1]));
		}
		br.close();
		return ret;
	}

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
//		toID(linkFrom, "E:\\users\\fulinyun\\objectrank\\info\\name2id.txt");
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
