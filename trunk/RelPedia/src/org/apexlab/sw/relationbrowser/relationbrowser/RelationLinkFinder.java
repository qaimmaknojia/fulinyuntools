package org.apexlab.sw.relationbrowser.relationbrowser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;


public class RelationLinkFinder {
	
	public static String catRepPrefix = "E:\\DBpediaData1\\catRep";
	public static String propRep = "E:\\DBpediaData1\\propRep\\";
	public static String insRep = "E:\\DBpediaData1\\relationInstance\\";
	public static String catSimCPrefix = "E:\\DBpediaData1\\catSimC";
	public static String catSimWPrefix = "E:\\DBpediaData1\\catSimW";
	public static String propSimC = "E:\\DBpediaData1\\propSimC\\";
	public static String propSimW = "E:\\DBpediaData1\\propSimW\\";
	public static String insSim = "E:\\DBpediaData1\\insSim\\";
//	public static String simPropEx = "E:\\DBPediaData\\simViaPropEx\\";
	public static String simPropEx = "\\\\ares\\e$\\DBpediaData\\simViaPropEx\\";

	public static int maxFileNameLength = 217;
	public static int topNum = 20;
	
	public static void main(String[] args) throws Exception {
//		int id = Translator.getRelationID("birthPlace");
//		for (int i = 0; i < 7; i++) {
//			System.out.println("catsimc level " + i);
//			ArrayList<StringDoublePair> sdp = getCatSimC(id, i);
//			for (StringDoublePair p : sdp) System.out.println(p.s + " " + p.d);
//			System.out.println();
//
//			System.out.println("catsimw level " + i);
//			sdp = getCatSimW(id, i);
//			for (StringDoublePair p : sdp) System.out.println(p.s + " " + p.d);
//			System.out.println();
//		}
//		System.out.println("propsimc");
//		ArrayList<StringDoublePair> sdp = getPropSimC(id);
//		for (StringDoublePair p : sdp) System.out.println(p.s + " " + p.d);
//		System.out.println();
//		
//		System.out.println("propsimw");
//		sdp = getPropSimW(id);
//		for (StringDoublePair p : sdp) System.out.println(p.s + " " + p.d);
//		System.out.println();

		ArrayList clist = new ArrayList();
		ArrayList wlist = new ArrayList();
		getPropExSim("birthPlace", clist, wlist);
		
		System.out.println("propexsimc");
		for (Iterator i = clist.iterator(); i.hasNext(); ) {
			StringDoublePair p = (StringDoublePair)i.next();
			System.out.println(p.s + " " + p.d);
		}
		System.out.println();
		
		System.out.println("propexsimw");
		for (Iterator i = wlist.iterator(); i.hasNext(); ) {
			StringDoublePair p = (StringDoublePair)i.next();
			System.out.println(p.s + " " + p.d);
		}
		System.out.println();
		
//		System.out.println("inssim");
//		sdp = getInsSim(id);
//		for (StringDoublePair p : sdp) System.out.println(p.s + " " + p.d);
//		System.out.println();

	}
	
	public static ArrayList<StringDoublePair> getSimTop(int id, String fold) throws Exception {
		if (id == -1) return new ArrayList<StringDoublePair>();
		int count = 0;
		BufferedReader br = new BufferedReader(new FileReader(fold+id));
		ArrayList<StringDoublePair> ret = new ArrayList<StringDoublePair>();
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] data = line.split("\t");
			ret.add(new StringDoublePair(Translator.getRelationName(Integer.parseInt(data[0])), Double.parseDouble(data[1])));
			count++;
			if (count >= topNum) break;
		}
		br.close();
		return ret;
	}
	
	public static ArrayList<StringDoublePair> getCatSimC(int id, int level) throws Exception {
		return getSimTop(id, catSimCPrefix+level+"\\");
	}
	
	public static ArrayList<StringDoublePair> getCatSimW(int id, int level) throws Exception {
		return getSimTop(id, catSimWPrefix+level+"\\");
	}
	
	public static ArrayList<StringDoublePair> getPropSimC(int id) throws Exception {
		return getSimTop(id, propSimC);
	}
	
	public static ArrayList<StringDoublePair> getPropSimW(int id) throws Exception {
		return getSimTop(id, propSimW);
	}
	
	public static ArrayList<StringDoublePair> getInsSim(int id) throws Exception {
		return getSimTop(id, insSim);
	}
	
	public static void getPropExSim(String name, ArrayList<StringDoublePair> simc, ArrayList<StringDoublePair> simw) throws Exception {
		name = URLEncoder.encode(name, "utf-8").replaceAll("%", "_percent_");
		String filename = preprocessFileName(name);
		File f = new File( simPropEx+filename );
		if (!f.exists()) return;
		TreeMap<Double, ArrayList<String>> sortTMc = new TreeMap<Double, ArrayList<String>>(new Comparator<Double>() {
			public int compare(Double a, Double b) {
				if (a > b) return -1;
				if (a < b) return 1;
				return 0;
			}
		});
		TreeMap<Double, ArrayList<String>> sortTMw = new TreeMap<Double, ArrayList<String>>(new Comparator<Double>() {
			public int compare(Double a, Double b) {
				if (a > b) return -1;
				if (a < b) return 1;
				return 0;
			}
		});

		BufferedReader br = new BufferedReader(new FileReader( simPropEx+filename ));
		int countc = 0;
		int countw = 0;
		br.readLine();
		String a = br.readLine();
		while ( a != null ) {
			String[] b = a.split("\t");
			if ( b[2].indexOf(".") + 5 < b[2].length() )
				b[2] = b[2].substring(0, b[2].indexOf(".")+5);
			if ( b[4].indexOf(".") + 5 < b[4].length() )
				b[4] = b[4].substring(0, b[4].indexOf(".")+5);
			try {
				b[0] = URLDecoder.decode(b[0].replaceAll("_percent_", "%"), "utf-8");
			} catch (Exception e) {
				
			}
			Double c = Double.parseDouble(b[2]);
			Double w = Double.parseDouble(b[4]);
			if (sortTMc.containsKey(c)) {
				sortTMc.get(c).add(b[0]);
			} else {
				ArrayList<String> list = new ArrayList<String>();
				list.add(b[0]);
				sortTMc.put(c, list);
			}
			countc++;
			int loss = sortTMc.get(sortTMc.lastKey()).size();
			if (countc-loss >= topNum) {
				sortTMc.remove(sortTMc.lastKey());
				countc -= loss;
			}
			
			if (sortTMw.containsKey(w)) {
				sortTMw.get(w).add(b[0]);
			} else {
				ArrayList<String> list = new ArrayList<String>();
				list.add(b[0]);
				sortTMw.put(w, list);
			}
			countw++;
			loss = sortTMw.get(sortTMw.lastKey()).size();
			if (countw-loss >= topNum) {
				sortTMw.remove(sortTMw.lastKey());
				countw -= loss;
			}
			
			a = br.readLine();
		}
		br.close();
		int count = 0;
		for (Double d : sortTMc.keySet()) {
			for (String s : sortTMc.get(d)) {
				simc.add(new StringDoublePair(s, d));
				count++;
			}
			if (count >= topNum) break;
		}
		count = 0;
		for (Double d : sortTMw.keySet()) {
			for (String s : sortTMw.get(d)) {
				simw.add(new StringDoublePair(s, d));
				count++;
			}
			if (count >= topNum) break;
		}
	}
	
	public static String preprocessFileName(String filename) {
		if (filename.length() > maxFileNameLength) filename = filename.substring(0, maxFileNameLength);
		if (filename.equalsIgnoreCase("con") || filename.equalsIgnoreCase("prn")
				|| filename.equalsIgnoreCase("aux") || filename.endsWith("."))
			filename += "1";
		return filename;
	}

	
	public static int common(String[] a, String[] b) {
		int ret = 0;
		for (int i = 0, j = 0; i < a.length && j < b.length; ) {
			if (a[i].compareTo(b[j]) < 0) i++;
			else if (a[i].compareTo(b[j]) > 0) j++;
			else {
				i++;
				j++;
				ret++;
			}
		}
		return ret;
	}
	
}
