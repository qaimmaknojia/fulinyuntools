import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class Converter {

	public static void convert(String inputfilename, 
			String inputencoding, String outputfilename,
			String outputencoding) throws Exception {
		InputStreamReader isr = new InputStreamReader(
				new FileInputStream(inputfilename), inputencoding);
		BufferedReader br = new BufferedReader(isr);
		System.out.println(isr.getEncoding());
		System.out.println();
		OutputStreamWriter osw = new OutputStreamWriter(
				new FileOutputStream(outputfilename), outputencoding);
		PrintWriter pw = new PrintWriter(osw);
		System.out.println(osw.getEncoding());
		System.out.println();
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			pw.println(line);
		}
		pw.close();
		br.close();
	}
}
