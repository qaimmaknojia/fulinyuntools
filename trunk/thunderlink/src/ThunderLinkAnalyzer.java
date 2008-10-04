import java.io.BufferedReader;
import java.io.FileReader;

import sun.misc.BASE64Decoder;


public class ThunderLinkAnalyzer {

//	AAhttp://d26.52ps.cn/pingshu/袁阔成_三国演义/袁阔成_三国演义_276.mp3?61.170.176.21300002TFlag=1221982428&OPIN=@1439D59E66DA36C3C12D7036DCE6E0F48.mp3ZZ
	public static String getOriginalString(String base64code) throws Exception {
		byte[] b = new BASE64Decoder().decodeBuffer(base64code);
		return new String(b);
	}
	
	public static String getURIpart(String rawStr) {
		if (rawStr.indexOf("?") == -1) return rawStr.substring(2, rawStr.length()-2);
		return rawStr.substring(2, rawStr.indexOf("?"));
	}
	
	public static String getSecondPart(String rawStr) {
		if (rawStr.indexOf("?") == -1) return null;
		if (rawStr.indexOf("TFlag=") == -1) return rawStr.substring(rawStr.indexOf("?")+1, rawStr.length()-2);
		return rawStr.substring(rawStr.indexOf("?")+1, rawStr.indexOf("TFlag="));
	}
	
	public static String getTFlag(String rawStr) {
		if (rawStr.indexOf("TFlag=") == -1) return null;
		if (rawStr.indexOf("&") == -1) 
			return rawStr.substring(rawStr.indexOf("TFlag=")+"TFlag=".length(), rawStr.length()-2);
		return rawStr.substring(rawStr.indexOf("TFlag=")+"TFlag=".length(), rawStr.indexOf("&"));
	}
	
	public static String getOPIN(String rawStr) {
		if (rawStr.indexOf("OPIN=") == -1) return null;
		return rawStr.substring(rawStr.indexOf("OPIN=")+"OPIN=".length(), rawStr.length()-2);
	}
	
	public static void main(String[] args) throws Exception {
		String testFile = "E:\\test.lst";
		BufferedReader br = new BufferedReader(new FileReader(testFile));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String base64code = line.substring(GetThunderLinks45ips.feature.length());
			String raw = getOriginalString(base64code);
			System.out.println(raw);
			
		}
//		String testStr = "QUFodHRwOi8vd2gubXlkb3duLmNvbS9zb2Z0LzIwMDgwOC9jb29sX3ZpZGVvX2NvbnZlcnRlLmV4ZVpa";
//		String raw = getOriginalString(testStr);
//		System.out.println(raw);
//		System.out.println(getSecondPart(raw));
//		System.out.println(new Date(1221982516000L).toString());
		
//		long f = Long.parseLong("1C75886140DC3338B5B82FA721A4B45EF", 16);
//		System.out.println(f);
	}
}
