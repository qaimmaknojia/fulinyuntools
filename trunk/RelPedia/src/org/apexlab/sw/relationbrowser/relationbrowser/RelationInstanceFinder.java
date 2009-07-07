package org.apexlab.sw.relationbrowser.relationbrowser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class RelationInstanceFinder {

	public static String insFold = "E:\\DBpediaData1\\relationInstance\\";
		
	public static ArrayList<SubObjNum> findInstance(int id, int start, int end) throws Exception {
		if (id == -1) return new ArrayList<SubObjNum>();
		BufferedReader br = new BufferedReader(new FileReader(insFold+id));
		ArrayList<SubObjNum> ret = new ArrayList<SubObjNum>();
		for (int i = 0; i < start; i++) br.readLine();
		int i = start;
		for (String line = br.readLine(); line != null && i < end; line = br.readLine(), i++) {
			String[] data = line.split("\t");
			int obj = -1;
			if (data.length >= 2) obj = Integer.parseInt(data[1]);
			ret.add(new SubObjNum(Integer.parseInt(data[0]), obj, 0));
		}
		br.close();
		return ret;
	}
}
