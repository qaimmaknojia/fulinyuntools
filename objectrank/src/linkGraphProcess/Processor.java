package linkGraphProcess;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import annotate.WebPageExp;

public class Processor {

//	public static String linkFrom = "\\\\poseidon\\team\\Semantic Search\\data\\wikipedia data\\entityGraphLinkFrom";
	
//	public static String projectFolder = "E:\\eclipse_workspace\\objectrank\\";
	public static String projectFolder = "E:\\users\\fulinyun\\objectrank\\";
	
//	public static String linkFrom = "E:\\entityGraphLinkFrom";
	public static int maxLine = 1200000;
	public static String temp = "E:\\temp.txt";
	public static String idMap = projectFolder+"info\\name2id.txt";
	public static String idMatrix = projectFolder+"info\\idMatrix";
	public static String linkFromMatrixUnique = projectFolder+"info\\linkFromMatrixUnique";
	public static String outlinkNum = projectFolder+"info\\outlinkNum";
	public static String outlinkNumUnique = projectFolder+"info\\outlinkNumUnique";
	public static int numId = 1797355;
	public static String[] nameMap;
	public static int[][] matrix;
	public static int[] olinkNum;
	public static HashMap<String, Integer> idMapMap;
	public static String linkToMatrixUnique = projectFolder+"info\\linkToMatrixUnique";
	
	// PR(A) = (1-d)b + d(PR(t1)/C(t1) + ... + PR(tn)/C(tn))
	public static double[] calculatePR(double initialPR, int numIteration, 
			int[][] matrix, int[] olinkNum, HashSet<Integer> inPage, double d) {
		double[] pr = new double[olinkNum.length];
		Arrays.fill(pr, initialPR);
		for (int i = 0; i < numIteration; i++) {
			System.out.println("begin iteration #" + (i+1) + "\t" + new Date().toString());
			double[] pr1 = new double[pr.length];
			Arrays.fill(pr1, 0.0);
			for (int j = 0; j < matrix.length; j++) if (matrix[j] != null){
				for (int k = 0; k < matrix[j].length; k++) pr1[j] += pr[matrix[j][k]]/olinkNum[matrix[j][k]];
				pr1[j] *= d;
				if (inPage.contains(j)) pr1[j] += 1-d;
			}
			System.arraycopy(pr1, 0, pr, 0, pr.length);
		}
		return pr;
	}
	
	public static double[] calculatePR1moreRound(double[] lastRoundPr, int[][] matrix, int[] olinkNum, HashSet<Integer> inPage, double d) {
		double[] pr1 = new double[lastRoundPr.length];
		Arrays.fill(pr1, 0.0);
		for (int j = 0; j < matrix.length; j++) if (matrix[j] != null){
			for (int k = 0; k < matrix[j].length; k++) pr1[j] += lastRoundPr[matrix[j][k]]/olinkNum[matrix[j][k]];
			pr1[j] *= d;
			if (inPage.contains(j)) pr1[j] += 1-d;
		}
		return pr1;
	}
	
	public static String[] selectTop(final double[] pr, int topNum, double[] score) {
		Integer[] ret = new Integer[pr.length];
		for (int i = 0; i < ret.length; i++) ret[i] = i;
		Arrays.sort(ret, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				if (pr[a] > pr[b]) return -1;
				if (pr[a] < pr[b]) return 1;
				return 0;
			}
		});
		String[] retStr = new String[topNum];
		for (int i = 0; i < topNum; i++) {
			retStr[i] = nameMap[ret[i]];
			score[i] = pr[ret[i].intValue()];
		}
		return retStr;
	}
	
	private static int[] getOutlinkNum(String outlinkNum2) throws Exception {
		System.out.println("begin loading outlink num");
//		long length = new File(outlinkNum2).length();
//		FileChannel fc = new RandomAccessFile(outlinkNum2, "r").getChannel();
//		MappedByteBuffer content = fc.map(FileChannel.MapMode.READ_ONLY, 0, length);
//		int num = content.getInt();
//		int[] ret = new int[numId];
//		for (int i = 0; i < num; i++) {
//			int key = content.getInt();
//			int value = content.getInt();
//			ret[key] = value;
//		}
//		System.out.println(num + " entries read");
//		fc.close();
		int[] ret = new int[numId];
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(outlinkNum2)));
		int num = dis.readInt();
		for (int i = 0; i < num; i++) {
			int key = dis.readInt();
			int value = dis.readInt();
			ret[key] = value;
		}
		dis.close();
		System.out.println(num + " entries read");
		return ret;
	}

	private static int[] getOutlinkNumFast(String outlinkNum2) throws Exception {
		System.out.println("begin loading outlink num");
		long length = new File(outlinkNum2).length();
		FileChannel fc = new RandomAccessFile(outlinkNum2, "r").getChannel();
		MappedByteBuffer content = fc.map(FileChannel.MapMode.READ_ONLY, 0, length);
		int num = content.getInt();
		int[] ret = new int[numId];
		for (int i = 0; i < num; i++) {
			int key = content.getInt();
			int value = content.getInt();
			ret[key] = value;
		}
		System.out.println(num + " entries read");
		fc.close();
//		int[] ret = new int[numId];
//		DataInputStream dis = new DataInputStream(new FileInputStream(outlinkNum2));
//		int num = dis.readInt();
//		for (int i = 0; i < num; i++) {
//			int key = dis.readInt();
//			int value = dis.readInt();
//			ret[key] = value;
//		}
//		dis.close();
//		System.out.println(num + " entries read");
		return ret;
	}
	
	private static int[][] loadLinkFromMatrix(String linkFromMatrix) throws Exception {
		System.out.println("begin loading id matrix");
//		long length = new File(linkFromMatrix).length();
//		FileChannel fc = new RandomAccessFile(linkFromMatrix, "r").getChannel();
//		MappedByteBuffer matrix = fc.map(FileChannel.MapMode.READ_ONLY, 0, length);
//		int num = matrix.getInt();
//		int[][] ret = new int[numId][];
//		for (int i = 0; i < num; i++) {
//			int key = matrix.getInt();
//			int numFrom = matrix.getInt();
//			ret[key] = new int[numFrom];
//			for (int j = 0; j < numFrom; j++) ret[key][j] = matrix.getInt();
//		}
//		System.out.println(num + " lines read");
//		fc.close();
		int[][] ret = new int[numId][];
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(linkFromMatrix)));
		int num = dis.readInt();
		for (int i = 0; i < num; i++) {
			int key = dis.readInt();
			int numFrom = dis.readInt();
			ret[key] = new int[numFrom];
			for (int j = 0; j < numFrom; j++) ret[key][j] = dis.readInt();
		}
		dis.close();
		System.out.println(num + " lines read");
		return ret;
	}

	private static int[][] loadIdMatrixFast(String linkFromMatrix) throws Exception {
		System.out.println("begin loading id matrix");
		long length = new File(linkFromMatrix).length();
		FileChannel fc = new RandomAccessFile(linkFromMatrix, "r").getChannel();
		MappedByteBuffer matrix = fc.map(FileChannel.MapMode.READ_ONLY, 0, length);
		int num = matrix.getInt();
		int[][] ret = new int[numId][];
		for (int i = 0; i < num; i++) {
			int key = matrix.getInt();
			int numFrom = matrix.getInt();
			ret[key] = new int[numFrom];
			for (int j = 0; j < numFrom; j++) ret[key][j] = matrix.getInt();
		}
		System.out.println(num + " lines read");
		fc.close();
//		int[][] ret = new int[numId][];
//		DataInputStream dis = new DataInputStream(new FileInputStream(linkFromMatrix));
//		int num = dis.readInt();
//		for (int i = 0; i < num; i++) {
//			int key = dis.readInt();
//			int numFrom = dis.readInt();
//			ret[key] = new int[numFrom];
//			for (int j = 0; j < numFrom; j++) ret[key][j] = dis.readInt();
//		}
//		dis.close();
//		System.out.println(num + " lines read");
		return ret;
	}

	public static void getOutlinkNum(String linkFromMatrix, String outlinkNum) throws Exception {
		System.out.println("begin reading linkFromMatrix");
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
			if ((i+1)%100000 == 0) System.out.println(i+1);
		}
		fc.close();
		System.out.println("begin writing outlinkNum");
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(outlinkNum));
		dos.writeInt(outlink.size());
		int lineCount = 0;
		for (Integer i : outlink.keySet()) {
			dos.writeInt(i.intValue());
			dos.writeInt(outlink.get(i).intValue());
			lineCount++;
			if (lineCount%100000 == 0) System.out.println(lineCount);
		}
		dos.close();
	}
	
	public static void getOutlinkNumUnique(String linkFromMatrix, String outlinkNum) throws Exception {
		System.out.println("begin reading linkFromMatrix");
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
			if ((i+1)%100000 == 0) System.out.println(i+1);
		}
		fc.close();
		System.out.println("begin writing outlinkNum");
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(outlinkNum));
		dos.writeInt(outlink.size());
		int lineCount = 0;
		for (Integer i : outlink.keySet()) {
			dos.writeInt(i.intValue());
			dos.writeInt(outlink.get(i).intValue());
			lineCount++;
			if (lineCount%100000 == 0) System.out.println(lineCount);
		}
		dos.close();
	}

	public static void createLinkFromMatrix(String input, String map, String output) throws Exception {
		HashMap<String, Integer> idmap = loadIdMap(map);
		System.out.println("begin getting id matrix");
		BufferedReader br = new BufferedReader(new FileReader(input));
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(output));
		int lineCount = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) lineCount++;
		br.close();
		dos.writeInt(lineCount);
		br = new BufferedReader(new FileReader(input));
		for (int l = 0; l < lineCount; l++) {
			String line = br.readLine();
			String content = line.substring(0, line.length()-3);
			String[] part = content.split("\t");
			dos.writeInt(idmap.get(part[0]).intValue());
			dos.writeInt(part.length-1);
			for (int i = 1; i < part.length; i++) dos.writeInt(idmap.get(part[i]));
			if ((l+1)%100000 == 0) System.out.println(l+1);
		}
		System.out.println(lineCount + " lines read");
		dos.close();
		br.close();
	}
	
	public static void createLinkFromMatrixUnique(String input, String map, String output) throws Exception {
		HashMap<String, Integer> idmap = loadIdMap(map);
		System.out.println("begin getting id matrix");
		BufferedReader br = new BufferedReader(new FileReader(input));
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(output));
		int lineCount = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) lineCount++;
		br.close();
		dos.writeInt(lineCount);
		br = new BufferedReader(new FileReader(input));
		for (int l = 0; l < lineCount; l++) {
			String line = br.readLine();
			String content = line.substring(0, line.length()-3);
			String[] part = content.split("\t");
			dos.writeInt(idmap.get(part[0]).intValue());
			HashSet<Integer> linkFromSet = new HashSet<Integer>();
			for (int i = 1; i < part.length; i++) if (idmap.containsKey(part[i])) linkFromSet.add(idmap.get(part[i]));
			dos.writeInt(linkFromSet.size());
			for (Integer i : linkFromSet) dos.writeInt(i.intValue());
			if ((l+1)%100000 == 0) System.out.println(l+1);
		}
		System.out.println(lineCount + " lines read");
		dos.close();
		br.close();
	}

	public static void getMissingNames(String nameMatrix, String map, String output) throws Exception {
		HashMap<String, Integer> idmap = loadIdMap(map);
		System.out.println("begin reading name matrix");
		BufferedReader br = new BufferedReader(new FileReader(nameMatrix));
		PrintWriter pw = new PrintWriter(new FileWriter(output));
		int lineCount = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String content = line.substring(0, line.length()-3);
			String[] part = content.split("\t");
			for (String name : part) if (!idmap.keySet().contains(name)) pw.println(name);
			lineCount++;
			if (lineCount%100000 == 0) System.out.println(lineCount);
		}
		br.close();
		pw.close();
	}
	
	public static void uniqueMissingNames(String input, String output) throws Exception {
		HashSet<String> nameSet = new HashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(input));
		for (String line = br.readLine(); line != null; line = br.readLine()) nameSet.add(line);
		br.close();
		PrintWriter pw = new PrintWriter(new FileWriter(output));
		for (String s : nameSet) pw.println(s);
		pw.close();
	}
	
	public static void addId2missingNames(String input, String output) throws Exception {
		int startId = 1729528;
		BufferedReader br = new BufferedReader(new FileReader(input));
		PrintWriter pw = new PrintWriter(output);
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			pw.println(line + "\t" + startId);
			startId++;
		}
		br.close();
		pw.close();
	}
	
	private static HashMap<String, Integer> loadIdMap(String map) throws Exception {
		System.out.println("begin loading id map");
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(map));
		int lineCount = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] part = line.split("\t");
			ret.put(part[0], Integer.parseInt(part[1]));
			lineCount++;
		}
		br.close();
		System.out.println(lineCount + " entries loaded");
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
			String content = line.substring(0, line.length()-3);
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
		for (String line = br.readLine(); line != null && lineCount < maxLine; line = br.readLine()) {
			String content = line.substring(0, line.length()-3);
			String[] part = content.split("\t");
			for (String entity : part) entityName.add(entity);
			lineCount++;
			if (lineCount % 100000 == 0) System.out.println(lineCount);
		}
		System.out.println(lineCount);
		System.out.println();
		br.close();
	}
	
//	public static void getNameMatrix(String output) throws Exception {
//		HashMap<Integer, String> nameMap = loadNameMap(idMap);
//		System.out.println("begin loading id matrix");
//		long length = new File(idMatrix).length();
//		FileChannel fc = new RandomAccessFile(idMatrix, "r").getChannel();
//		MappedByteBuffer matrix = fc.map(FileChannel.MapMode.READ_ONLY, 0, length);
//		PrintWriter pw = new PrintWriter(new FileWriter(output));
//		int num = matrix.getInt();
//		for (int i = 0; i < num; i++) {
//			int key = matrix.getInt();
//			pw.print(nameMap.get(key));
//			int numFrom = matrix.getInt();
//			for (int j = 0; j < numFrom; j++) pw.print("\t" + nameMap.get(matrix.getInt()));
//			pw.println("$$$");
//		}
//		System.out.println(num + " lines read");
//		fc.close();
//		pw.close();
//	}
	
//	public static void compareFile(String f1, String f2) throws Exception {
//		BufferedReader br1 = new BufferedReader(new FileReader(f1));
//		BufferedReader br2 = new BufferedReader(new FileReader(f2));
//		int lineCount = 1;
//		for (String line1 = br1.readLine(), line2 = br2.readLine(); line1 != null && line2 != null;
//			line1 = br1.readLine(), line2 = br2.readLine()) {
//			if (!line1.equals(line2)) {
//				System.out.println("line " + lineCount + " differ!");
//				System.out.println(f1 + ":");
//				System.out.println(line1);
//				System.out.println(f2 + ":");
//				System.out.println(line2);
//			}
//			lineCount++;
//		}
//		br1.close();
//		br2.close();
//	}
	
	private static String[] loadNameMap(String idMap2) throws Exception {
		System.out.println("begin loading name map");
		BufferedReader br = new BufferedReader(new FileReader(idMap2));
		String[] ret = new String[numId];
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] part = line.split("\t");
			ret[Integer.parseInt(part[1])] = part[0];
		}
		br.close();
		System.out.println(ret.length + " entries read");
		return ret;
	}

	public static HashSet<Integer> getInPageId(String[] names) {
		HashSet<Integer> ret = new HashSet<Integer>();
		for (String name : names) if (idMapMap.containsKey(name)) ret.add(idMapMap.get(name));
		return ret;
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
	
	public static void createLinkToMatrix(String linkFromMatrix, String output) throws Exception {
		System.out.println("begin reading link from matrix");
		DataInputStream dis = new DataInputStream(new FileInputStream(linkFromMatrix));
		HashMap<Integer, ArrayList<Integer>> linkToMatrix = new HashMap<Integer, ArrayList<Integer>>();
		int num = dis.readInt();
		for (int i = 0; i < num; i++) {
			int id = dis.readInt();
			int n = dis.readInt();
			for (int j = 0; j < n; j++) {
				int key = dis.readInt();
				if (linkToMatrix.containsKey(key)) linkToMatrix.get(key).add(id);
				else {
					ArrayList<Integer> value = new ArrayList<Integer>();
					value.add(id);
					linkToMatrix.put(key, value);
				}
			}
			if ((i+1)%1000000 == 0) System.out.println(i+1);
		}
		dis.close();
		System.out.println("begin writing link to matrix");
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(output));
		int lineCount = 0;
		dos.writeInt(linkToMatrix.size());
		for (Integer i : linkToMatrix.keySet()) {
			dos.writeInt(i.intValue());
			ArrayList<Integer> list = linkToMatrix.get(i);
			dos.writeInt(list.size());
			for (Integer j : list) dos.writeInt(j.intValue());
			lineCount++;
			if (lineCount%1000000 == 0) System.out.println(lineCount);
		}
		dos.close();
	}
	
	public static void tunePara(String url, String output) throws Exception {
		PrintWriter pw = new PrintWriter(new FileWriter(output));
		pw.println(url);
		String[] recognizedNames = WebPageExp.recognize(url);
		HashSet<Integer> inPage = Processor.getInPageId(recognizedNames);
		for (Integer i : inPage) pw.println(Processor.nameMap[i.intValue()]);
//		for (Integer i : inPage) System.out.println(Processor.nameMap[i.intValue()]);
		int topNum = 10;
		for (int i = 5; i < 51; i += 5) {
			System.out.println("i = " + i + " ; j = 3");
			System.gc();
			double d = (i+0.0)/100.0;
			double[] pr = calculatePR(1.0, 3, matrix, olinkNum, inPage, d);
			double[] score = new double[topNum];
			String[] list = selectTop(pr, topNum, score);
			pw.println("iteration number = " + 3 + " ; d = " + d);
//			pw.flush();
			for (int j = 0; j < topNum; j++) {
				pw.println(list[j] + "\t" + score[j]);
//				pw.flush();
			}
			for (int j = 4; j < 15; j++) {
				System.out.println("i = " + i + " ; j = " + j + " " + new Date().toString());
				System.gc();
				pr = calculatePR1moreRound(pr, matrix, olinkNum, inPage, d);
				list = selectTop(pr, 10, score);
				pw.println("iteration number = " + j + " ; d = " + d);
//				pw.flush();
				for (int k = 0; k < topNum; k++) {
					pw.println(list[k] + "\t" + score[k]);
//					pw.flush();
				}
			}
		}
		pw.close();

	}
	
	public static void main(String[] args) throws Exception {
//		checkFile(linkFrom);
//		toID(linkFrom, idMap);
//		getMissingNames(linkFrom, idMap, "E:\\missingNames");
//		uniqueMissingNames("E:\\missingNames", "E:\\missingNamesUnique");
//		addId2missingNames("E:\\missingNamesUnique", "E:\\missingNamesWithId");
//		createLinkFromMatrix(linkFrom, idMap, idMatrix);
//		getOutlinkNum(idMatrix, outlinkNum);

//		createLinkFromMatrixUnique(linkFrom, idMap, linkFromMatrixUnique);	//running
//		getOutlinkNumUnique(linkFromMatrixUnique, outlinkNumUnique);	//to run

//		getNameMatrix("E:\\nameMatrix.txt");
//		compareFile("E:\\nameMatrix.txt", linkFrom);
		
//		createLinkToMatrix(linkFromMatrixUnique, linkToMatrixUnique);

		File[] fn = new File(projectFolder+"res\\").listFiles(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().endsWith(".htm");
			}
		});
		for (int i = 1; i < fn.length; i++) {
			File f = fn[i];
			System.out.println("tuning " + f.getName());
			tunePara(f.getAbsolutePath(), projectFolder+"tuning\\" + f.getName() + ".txt");
		}
	}
	
	static {
		try {
			System.out.println(new Date().toString());
			matrix = loadLinkFromMatrix(linkFromMatrixUnique);
			System.out.println(new Date().toString());
			olinkNum = getOutlinkNum(outlinkNumUnique);
			System.out.println(new Date().toString());
			nameMap = loadNameMap(idMap);
			System.out.println(new Date().toString());
			idMapMap = loadIdMap(idMap);
			System.out.println(new Date().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
