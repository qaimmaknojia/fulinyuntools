import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class PhdcomicsExtractor {

	public static void main(String[] args) throws Exception {
		extract("D:\\files\\phdcomics\\", "http://www.phdcomics.com/comics/archive/phd", "D:\\files\\phdcomics\\gifurls.txt");
	}
	
	public static void extract(String inputFolder, String startsignal, String outputFile) throws Exception {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
		for (int i = 1; i < 63; i++) {
			BufferedReader br = new BufferedReader(new FileReader(inputFolder+"archive("+i+").php"));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				int s = line.indexOf(startsignal);
				if (s != -1) {
					int e = line.indexOf(".gif", s);
					pw.println(line.substring(s, e+4));
					break;
				}
			}
			br.close();
		}
		pw.close();
	}
}
