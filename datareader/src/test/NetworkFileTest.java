package test;

import basic.IDataSourceReader;
import basic.IOFactory;

public class NetworkFileTest {

	public static String url = "http://data-gov.tw.rpi.edu/raw/1146/data-1146.nt.gz";
	public static String file = "c:\\temp\\data-1146.nt.gz";
	
	public static void main(String[] args) throws Exception {
//		IDataSourceReader reader = IOFactory.getReader(file);
		IDataSourceReader reader = IOFactory.getReader(url);
		for (int i = 0; i < 20; i++) System.out.println(reader.readLine());
	}
}
