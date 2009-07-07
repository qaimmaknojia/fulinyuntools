package org.apexlab.sw.relationbrowser.relationbrowser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class RelationInfoRetriever {

	public static String catRepPrefix = "E:\\DBpediaData1\\catRep";
	public static int topNum = 30;
	
	public static ArrayList<SubObjNum> getCategoryPair(int level, int id) throws Exception {
		if (id == -1) return new ArrayList<SubObjNum>();
		BufferedReader br = new BufferedReader(new FileReader(catRepPrefix+level+"\\"+id));
		ArrayList<SubObjNum> ret = new ArrayList<SubObjNum>();
		int lineCount = 0;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] data = line.split("\t");
			ret.add(new SubObjNum(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
			lineCount++;
			if (lineCount >= topNum) break;
		}
		br.close();
		return ret;
	}
}
