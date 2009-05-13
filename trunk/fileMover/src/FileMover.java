import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class FileMover {

	public static long blockSize = 3000000;
	
	/**
	 * copy file from VPN
	 * @param src
	 * @param dst
	 * @param rename
	 * @throws Exception
	 */
	public static void copy(File src, File dst, boolean rename) throws Exception {
		if (!src.exists()) {
			System.out.println(src.getName() + " does not exist.");
			return;
		}
		if (dst.exists() && dst.length() == src.length()) {
			if (rename && !src.getName().contains("_ok")) src.renameTo(new File(src.getParent() + "\\" 
					+ src.getName().replaceAll("\\.", "_ok.")));
			return;
		}
		long length = src.length();
		long blockNumber = length/blockSize;
		long lastBlockSize = length%blockSize;
		
		System.out.println(new Date().toString() + " File name: " + src.getName() 
				+ " file length: " + length
				+ " blockSize: " + blockSize + " \nblockNumber: " + blockNumber
				+ " lastBlockSize: " + lastBlockSize);
		dst.createNewFile();
		long copied = dst.length()/blockSize;
		System.out.println("copied block numer: " + copied);
		RandomAccessFile srcr = new RandomAccessFile(src, "r");
		RandomAccessFile dstr = new RandomAccessFile(dst, "rw");
		srcr.seek(copied*blockSize);
		dstr.seek(copied*blockSize);
		FileChannel fcsrc = srcr.getChannel();
		FileChannel fcdst = dstr.getChannel();
		for (long i = copied; i < blockNumber; i++) {
			ByteBuffer bb = ByteBuffer.allocate((int)blockSize);
			fcsrc.read(bb);
			bb.position(0);
			fcdst.write(bb);
			fcdst.force(false);
			System.out.println(new Date().toString() + " block #" + i + " copied," 
					+ " dst file length: " + dst.length());
		}
		ByteBuffer bb = ByteBuffer.allocate((int)lastBlockSize);
		fcsrc.read(bb);
		bb.position(0);
		fcdst.write(bb);
		fcdst.force(false);
		System.out.println(new Date().toString() 
				+ " last block copied, dst file length: " + dst.length());
		fcsrc.close();
		srcr.close();
		fcdst.close();
		dstr.close();
		if (rename) src.renameTo(new File(src.getParent() + "\\" 
				+ src.getName().replaceAll("\\.", "_ok.")));
	}
	
	/**
	 * download file larger than 4G from network
	 * @param netfile
	 * @param dst
	 * @throws Exception
	 */
	public static void download(String netfile, File dst) throws Exception {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(
				new URL(netfile).openStream()));
		dst.createNewFile();
		long downloaded = dst.length();
		System.out.println("downloaded: " + downloaded + " bytes");
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(dst, true)));
		for (int i = 0; i < downloaded; i++) dis.read();
		int byteRead;
		for (byteRead = dis.read(); byteRead != -1; byteRead = dis.read()) {
			dos.write(byteRead);
			if (dos.size() % 10000000 == 0) {
				dos.flush();
				System.out.println(new Date().toString() + " 10000000 new bytes downloaded," 
					+ " dst file length: " + dst.length());
			}
		}
		System.out.println(new Date().toString() + " download completed," 
				+ " dst file length: " + dst.length());
		dos.close();
		dis.close();
	}
	
	public static void main(String[] args) throws Exception {
		download("http://static.wikipedia.org/downloads/2008-06/en/wikipedia-en-html.tar.7z", 
			new File("\\\\poseidon\\team\\semantic search\\data\\wikipedia-en-html-08-june.tar.7z") 
			); // running
//		download("http://apache.etoak.com/lucene/java/lucene-2.4.1-src.zip", 
//				new File("d:\\javalib\\lucene-2.4.1-src.zip"));
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\b.rar"), 
//				new File("E:\\wikipediaArticle\\b.rar"), true); done
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\c.rar"), 
//				new File("E:\\wikipediaArticle\\c.rar"), true); done
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\j_ok.rar"), 
//				new File("E:\\wikipediaArticle\\j.rar"), false); done
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\s.rar"), 
//				new File("E:\\wikipediaArticle\\s.rar"), true); done
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\i_ok.rar"), 
//				new File("E:\\wikipediaArticle\\i.rar"), false); done
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\d.rar"), 
//				new File("E:\\wikipediaArticle\\d.rar"), true); done
//		copy(new File("\\\\192.168.3.246\\d$\\WikipediaCategory.rar"), 
//				new File("E:\\WikipediaCategory.rar"), true); done
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\e.rar"), 
//				new File("E:\\wikipediaArticle\\e.rar"), true);
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\f.rar"), 
//				new File("E:\\wikipediaArticle\\f.rar"), true);
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\g.rar"), 
//				new File("E:\\wikipediaArticle\\g.rar"), true); done
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\h.rar"), 
//				new File("E:\\wikipediaArticle\\h.rar"), true);
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\r.rar"), 
//				new File("E:\\wikipediaArticle\\r.rar"), true); done
		
	}
}
