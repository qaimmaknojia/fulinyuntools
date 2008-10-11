import opennlp.tools.lang.english.SentenceDetector;


public class SentenceSegmenter {

	public static void main(String[] args) throws Exception {
		SentenceDetector sd = new SentenceDetector("EnglishSD.bin.gz");
		String[] sets = sd.sentDetect("first sentence. second sentence? Mr. Millman, third sentence - you got it?");
		for (String s : sets) System.out.println(s);
	}
}
