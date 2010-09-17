package basic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.tools.bzip2.CBZip2InputStream;

public class Bz2Reader implements IDataSourceReader {

	private BufferedReader br;
	
	public Bz2Reader(String fn) {
		try {
			init(fn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() throws Exception {
		br.close();
	}

	private void init(String filename) throws Exception {
		InputStream is = null;
		if (IOFactory.isURL(filename)) is = new URL(filename).openStream();
		else is = new FileInputStream(filename);
		//read the initial "BZ" mark
		is.read();
		is.read();
		
		br = new BufferedReader(new InputStreamReader(new CBZip2InputStream(is), "UTF-8"));
	}

	@Override
	public String readLine() throws Exception {
		return br.readLine();
	}

	public static void main(String[] args) throws Exception {
		IDataSourceReader idsr = new Bz2Reader(
				"\\\\poseidon\\team\\semantic search\\data\\musicbrainz\\Rdf data\\mball.bz2");
		for (int i = 0; i < 10; i++) System.out.println(idsr.readLine());
	}
}
