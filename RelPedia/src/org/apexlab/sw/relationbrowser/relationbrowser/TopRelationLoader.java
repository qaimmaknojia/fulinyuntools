package org.apexlab.sw.relationbrowser.relationbrowser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TopRelationLoader {

	public static ArrayList<RelAndNum> relationLoader(String fileName) {
		ArrayList<RelAndNum> topRelation = new ArrayList<RelAndNum>();
		try {
			BufferedReader relationReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(fileName)));
			String currentLine = null;
			
			while((currentLine = relationReader.readLine())!=null){
				String[] nameAndNum = currentLine.split(",");
				//String relName = nameAndNum[0].substring(1, nameAndNum[0].length()-1);
				String relName = nameAndNum[0];
				int relNum = Integer.parseInt(nameAndNum[1]);
				
				topRelation.add(new RelAndNum(relName, relNum));
			}
			
		} 
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return topRelation;
	}
	
	public static void main(String[] args){
		ArrayList<RelAndNum> topRel = TopRelationLoader.relationLoader("C:\\top100relations.csv");
		
		for(int i=0; i<topRel.size(); i++){
			System.out.println(topRel.get(i).RelName+" "+topRel.get(i).RelNum);
		}
	}
}
