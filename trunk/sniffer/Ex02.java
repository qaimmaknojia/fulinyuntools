import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class Ex02 {

	public Ex02() {
	}

	public static void main(String[] args) throws Exception {

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod("http://happyfarm.fivminutes.com/api.php?mod=farmlandstatus" +
				"&act=scarify&farmKey=f00210a319ab66eb5af37773bb1da01c&farmTime=1234953447");
		method.setRequestHeader("Host", "happyfarm.fivminutes.com");
		method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.6) Gecko/2009011913 Firefox/3.0.6");
		method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		method.setRequestHeader("Accept-Language", "zh-cn");	
		method.setRequestHeader("Accept-Encoding", "gzip, deflate");
		method.setRequestHeader("Accept-Charset", "gb2312,utf-8;q=0.7,*;q=0.7");
		method.setRequestHeader("Keep-Alive", "300");
		method.setRequestHeader("Connection", "keep-alive");
		method.setRequestHeader("Cookie", "__utma=47175358.1516047794638148900.1234661515.1234879203.1234952649.9; " +
				"__utmz=47175358.1234952649.9.9.utmcsr=apps.xiaonei.com|utmccn=(referral)|utmcmd=referral|utmcct=/happyfarm; " +
				"PHPSESSID=qjovg2jhv6pdv5c54iln8r3fj2; __utmc=47175358; __utmb=47175358.1.10.1234952649");
		method.setRequestHeader("Referer", "http://xnimg.cn/xcube/app/23163/xn30/grange.swf?v=inu29");
		method.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		method.setRequestHeader("Content-length", "25");
		
		NameValuePair[] data = { 
				new NameValuePair("ownerId", "223741527"),
				new NameValuePair("place", "0")
				};
		method.setRequestBody(data);
//		method.setRequestBody("9f\n"
//		+ "<?xml version=\"1.0\"?>\n"
//		+ "<toplevel>\n"
//		+ "<email data=\"fulinyun@gmail.com\"/>" +
//				"<id data=\"aedf313041aad334444e82c941d92b67\"/>" +
//				"<persistent_google_user data=\"1\"/>\n"
//		+ "</toplevel>\n\n0");

		client.executeMethod(method);
//		Header[] responseHeaders = method.getResponseHeaders();
//		for (int i = 0; i < responseHeaders.length; i++) {
//			System.out.println(responseHeaders[i].getName() + " = "
//					+ responseHeaders[i].getValue());
//		}
//		System.out.println("----------- end of header -------------");
//		//			byte[] responseBody = method.getResponseBody();
//		//			System.out.println(new String(responseBody));
//		System.out.println(method.getResponseBodyAsString());
	}
}


