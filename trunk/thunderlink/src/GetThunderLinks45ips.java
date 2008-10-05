import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;


public class GetThunderLinks45ips {

	public static String feature = "thunder://";
	public static String sgUrlPrefix = "http://www1.5ips.net/down.asp?id=123&num=";
	public static String sdxlUrlPrefix = "http://www1.5ips.net/down.asp?id=122&num=";
	public static String sdyxzUrlPrefix = "http://www1.5ips.net/down.asp?id=29&num=";
	public static String hlmUrlPrefix = "http://www1.5ips.net/down.asp?id=83&num=";

	public static String findThunderLink(String url) throws Exception {
		URL theUrl = new URL(url);
		InputStream is = theUrl.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String featureLine = null;
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			if (line.contains(feature)) {
				featureLine = line;
				break;
			}
		}
		int start = featureLine.indexOf(feature);
		int end = featureLine.indexOf("\"", start);
		br.close();
		return featureLine.substring(start, end);
	}
	
	public static void getThunderLinks(String urlPrefix, int from, int end, String output, int numLength) 
			throws Exception {
		PrintWriter pw = new PrintWriter(new FileWriter(output));
		for (int i = from; i < end; i++) {
			String temp = urlPrefix;
			if (from < 10) {
				for (int j = 0; j < numLength-1; j++) temp = temp+"0";
				pw.println(findThunderLink(temp+i));
			} else if (from < 100) {
				for (int j = 0; j < numLength-2; j++) temp = temp+"0";
				pw.println(findThunderLink(temp+i));
			} else {
				pw.println(findThunderLink(temp+i));
			}
			System.out.println(i);
		}
		pw.close();
		
	}
	
//	public static void refresh(String urlPrefix, int from, int end, int numLength) throws Exception {
//		for (int i = from; i < end; i++) {
//			String temp = urlPrefix;
//			int retry = 0;
//			if (from < 10) {
//				for (int j = 0; j < numLength-1; j++) temp = temp+"0";
//			} else if (from < 100) {
//				for (int j = 0; j < numLength-2; j++) temp = temp+"0";
//			}
//			while (true) {
//				try {
//					refresh(temp+i);
//					System.out.println(i + " refreshed");
//					break;
//				} catch (Exception e) {
//					e.printStackTrace();
//					retry++;
//					System.out.println("tried " + retry + " times");
//				}
//			}
//		}
//	}
	
//	public static void refresh(String url) throws Exception {
//		URL theUrl = new URL(url);
//		InputStream is = theUrl.openStream();
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		for (String line = br.readLine(); line != null; line = br.readLine()) if (line.contains(feature)) break;
//		br.close();
//		
//	}
	
//	public static void fakeTime(String lstFile, long lagSeconds, String output) throws Exception {
//		BufferedReader br = new BufferedReader(new FileReader(lstFile));
//		PrintWriter pw = new PrintWriter(new FileWriter(output));
//		int i = 0;
//		for (String line = br.readLine(); line != null; line = br.readLine(), i++) {
//			String base64code = line.substring(feature.length());
//			String raw = ThunderLinkAnalyzer.getOriginalString(base64code);
//			long time = Long.parseLong(ThunderLinkAnalyzer.getTFlag(raw));
//			System.out.println(time);
//			String uri = ThunderLinkAnalyzer.getURIpart(raw);
//			String second = ThunderLinkAnalyzer.getSecondPart(raw);
//			String opin = ThunderLinkAnalyzer.getOPIN(raw);
//			AAhttp://d26.52ps.cn/pingshu/袁阔成_三国演义/袁阔成_三国演义_276.mp3?61.170.176.21300002TFlag=1221982428&OPIN=@1439D59E66DA36C3C12D7036DCE6E0F48.mp3ZZ
//			String fakeLink = "AA" + uri + "?" + second + "TFlag=" + (time+i*lagSeconds) + "&OPIN=" + opin + "ZZ";
//			byte[] bytes = fakeLink.getBytes();
//			System.out.println(new BASE64Encoder().encode(bytes));
//			pw.println(feature+new BASE64Encoder().encode(bytes).replaceAll("\r\n", ""));
//		}
//		br.close();
//		pw.close();
//	}
	
//	public static void tryMd5(String str, String md5, String dump) throws Exception {
//		PrintWriter pw = new PrintWriter(new FileWriter(dump));
//		for (int i = 0; i < str.length(); i++) for (int j = i; j < str.length(); j++) {
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			byte[] result = md.digest(str.substring(i, j).getBytes());
//			StringWriter sw = new StringWriter();
//			PrintWriter pw1 = new PrintWriter(sw);
//			for (byte b : result) pw1.printf("%02X", b);
//			pw.println(str.substring(i, j) + "\n" + sw.toString());
//
//		}
//		pw.close();
//	}
	
	public static void main(String[] args) throws Exception {
//		getThunderLinks(sgUrlPrefix, 23, 29, "E:\\sgyylinks23-29.lst", 3);
//		getThunderLinks(sgUrlPrefix, 77, 100, "E:\\sgyylinks77-100.lst", 3);
//		getThunderLinks(sgUrlPrefix, 135, 150, "E:\\sgyylinks135-150.lst", 3);
//		getThunderLinks(sgUrlPrefix, 10, 11, "E:\\sgyylinks10.lst", 3);
//		getThunderLinks(sgUrlPrefix, 335, 366, "E:\\sgyylinks335.lst", 3);
//		fakeTime("E:\\sgyylinks334.lst", 60L, "E:\\sgyylinks3341.lst");
//		Thread.currentThread().sleep(15*60*1000);
//		while (true) {
//			System.out.println();
//			System.out.println(new Date().toString());
//			refresh(sgUrlPrefix, 234, 366, 3);
//			Thread.currentThread().sleep(10*60*1000);
//		}
//		getThunderLinks(sdxlUrlPrefix, 1, 10, "E:\\sdxllinks1.lst", 2);
//		getThunderLinks(sdxlUrlPrefix, 30, 41, "E:\\sdxllinks3.lst", 2);
//		getThunderLinks(sdyxzUrlPrefix, 1, 9, "E:\\sdyxzlinks1.lst", 2);
//		getThunderLinks(sdyxzUrlPrefix, 24, 41, "E:\\sdyxzlinks2.lst", 2);
//		for (int i = 0; i < 100; i++) {
//			System.out.println(findThunderLink(sgUrlPrefix+276));
//			Thread.currentThread().sleep(10*1000);
//		}
//		MessageDigest md = MessageDigest.getInstance("MD5");
//		byte[] result = md.digest("61.170.176.21300002TFlag=1221982299".getBytes());
//		for (byte b : result) System.out.printf("%X", b);
//		System.out.println();
//		tryMd5("AAhttp://d26.52ps.cn/pingshu/袁阔成_三国演义/袁阔成_三国演义_276.mp3" +
//				"?61.170.176.21300002TFlag=1221982299", "1C75886140DC3338B5B82FA721A4B45EF", 
//				"E:\\dumpMd5.txt");
//		getThunderLinks(hlmUrlPrefix, 1, 10, "E:\\hlmlinks1.lst", 3);
//		getThunderLinks(hlmUrlPrefix, 10, 50, "E:\\hlmlinks2.lst", 3);
//		getThunderLinks(hlmUrlPrefix, 50, 100, "E:\\hlmlinks3.lst", 3);
		getThunderLinks(hlmUrlPrefix, 100, 150, "E:\\hlmlinks4.lst", 3);
	}
}
