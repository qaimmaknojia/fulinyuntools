package org.ateam.xxplore.core.service.mappingA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class IOFactory {

	// get the buffered PrintWriter for the gz formatted file, encoded in UTF-8 
	public static PrintWriter getGzPrintWriter(String filename) throws Exception {
		return new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filename), "UTF-8")));
	}
	
	// get the buffered data source reader, the specific return type depends on the file name 
	public static IDataSourceReader getReader(String filename) throws Exception {
		String fn = filename.toLowerCase();
		if (fn.endsWith(".tar.gz")) return new TarGzReader(filename);
		else if (fn.endsWith(".gz")) return new GzReader(filename);
		else if (fn.endsWith(".warc")) return new WarcReader(filename);
		else if (fn.endsWith(".zip")) return new ZipReader(filename);
		else if (fn.endsWith(".bz2")) return new Bz2Reader(filename);
		else if (new File(filename).isDirectory()) return new DirZipReader(filename);
		else return new TxtReader(filename);
	}
}
