import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class FileMover {

	public static long blockSize = 300000;
	
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
	
	public static void main(String[] args) throws Exception {
		
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
		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\h.rar"), 
				new File("E:\\wikipediaArticle\\h.rar"), true);
//		copy(new File("\\\\192.168.3.246\\d$\\wikipediaArticle_bak\\r.rar"), 
//				new File("E:\\wikipediaArticle\\r.rar"), true); done
		
	}
}
