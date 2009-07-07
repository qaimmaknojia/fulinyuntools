package org.apexlab.sw.relationbrowser.relationbrowser;

public class RelAndNum {

	public String RelName;
	public int RelNum;
	
	public RelAndNum(){
		RelName = new String();
		RelNum = 0;
	}
	
	public RelAndNum(String name, int num){
		RelName = name;
		RelNum = num;
	}
}
