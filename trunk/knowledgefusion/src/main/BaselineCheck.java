package main;

public class BaselineCheck {
	
	static String workFolder = ""; 
	
	public static void main(String[] args) throws Exception {
		Cheater.translateDocNum(Indexer.indexFolder+"keyInd.txt",
				Analyzer.countLines(Indexer.indexFolder+"keyInd.txt"),
				workFolder+"r0.5.txt", workFolder+"r0.5translated.txt");
		
	}
}
