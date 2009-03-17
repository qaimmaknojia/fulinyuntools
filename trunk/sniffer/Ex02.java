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
	
//	POST /api.php?mod=farmlandstatus&act=planting&farmKey=f05a54bb0414ba7f452114ba49946094&farmTime=1234953513 HTTP/1.1
//			Host: happyfarm.fivminutes.com
//			User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.6) Gecko/2009011913 Firefox/3.0.6
//			Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//			Accept-Language: zh-cn
//			Accept-Encoding: gzip,deflate
//			Accept-Charset: gb2312,utf-8;q=0.7,*;q=0.7
//			Keep-Alive: 300
//			Connection: keep-alive
//			Cookie: __utma=47175358.1516047794638148900.1234661515.1234879203.1234952649.9; __utmz=47175358.1234952649.9.9.utmcsr=apps.xiaonei.com|utmccn=(referral)|utmcmd=referral|utmcct=/happyfarm; PHPSESSID=qjovg2jhv6pdv5c54iln8r3fj2; __utmc=47175358; __utmb=47175358.1.10.1234952649
//			Referer: http://xnimg.cn/xcube/app/23163/xn30/grange.swf?v=inu29
//			Content-type: application/x-www-form-urlencoded
//			Content-length: 31
//
//			ownerId=223741527&place=0&cId=2
	public static void plant(int place, int seed) {
		
	}
	
//	POST /api.php?mod=item&act=buy&farmKey=775428c25c77a053370c34ca9b282cc1&farmTime=1234953574 HTTP/1.1
//	Host: happyfarm.fivminutes.com
//	User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.6) Gecko/2009011913 Firefox/3.0.6
//	Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//	Accept-Language: zh-cn
//	Accept-Encoding: gzip,deflate
//	Accept-Charset: gb2312,utf-8;q=0.7,*;q=0.7
//	Keep-Alive: 300
//	Connection: keep-alive
//	Cookie: __utma=47175358.1516047794638148900.1234661515.1234879203.1234952649.9; __utmz=47175358.1234952649.9.9.utmcsr=apps.xiaonei.com|utmccn=(referral)|utmcmd=referral|utmcct=/happyfarm; PHPSESSID=qjovg2jhv6pdv5c54iln8r3fj2; __utmc=47175358; __utmb=47175358.1.10.1234952649
//	Referer: http://xnimg.cn/xcube/app/23163/xn30/grange.swf?v=inu29
//	Content-type: application/x-www-form-urlencoded
//	Content-length: 9
//
//	itemId=22
	public static void buyManure() {
		
	}
	
//	POST /api.php?mod=repertory&act=buySeed&farmKey=458794a9915eb98faa7506974ff3897c&farmTime=1234953505 HTTP/1.1
//	Host: happyfarm.fivminutes.com
//	User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.6) Gecko/2009011913 Firefox/3.0.6
//	Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//	Accept-Language: zh-cn
//	Accept-Encoding: gzip,deflate
//	Accept-Charset: gb2312,utf-8;q=0.7,*;q=0.7
//	Keep-Alive: 300
//	Connection: keep-alive
//	Cookie: __utma=47175358.1516047794638148900.1234661515.1234879203.1234952649.9; __utmz=47175358.1234952649.9.9.utmcsr=apps.xiaonei.com|utmccn=(referral)|utmcmd=referral|utmcct=/happyfarm; PHPSESSID=qjovg2jhv6pdv5c54iln8r3fj2; __utmc=47175358; __utmb=47175358.1.10.1234952649
//	Referer: http://xnimg.cn/xcube/app/23163/xn30/grange.swf?v=inu29
//	Content-type: application/x-www-form-urlencoded
//	Content-length: 14
//
//	cId=8&number=1
	public static void buySeed(int seed) {
		
	}
	
//	POST /api.php?mod=farmlandstatus&act=scarify&farmKey=d4eff0de96b2c1f8fbd9fd7d15ae80ca&farmTime=1234953454 HTTP/1.1
//			Host: happyfarm.fivminutes.com
//			User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.6) Gecko/2009011913 Firefox/3.0.6
//			Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//			Accept-Language: zh-cn
//			Accept-Encoding: gzip,deflate
//			Accept-Charset: gb2312,utf-8;q=0.7,*;q=0.7
//			Keep-Alive: 300
//			Connection: keep-alive
//			Cookie: __utma=47175358.1516047794638148900.1234661515.1234879203.1234952649.9; __utmz=47175358.1234952649.9.9.utmcsr=apps.xiaonei.com|utmccn=(referral)|utmcmd=referral|utmcct=/happyfarm; PHPSESSID=qjovg2jhv6pdv5c54iln8r3fj2; __utmc=47175358; __utmb=47175358.1.10.1234952649
//			Referer: http://xnimg.cn/xcube/app/23163/xn30/grange.swf?v=inu29
//			Content-type: application/x-www-form-urlencoded
//			Content-length: 25
//
//			ownerId=223741527&place=6
	public static void scarify(int place) {
		
	}
	
//	POST /api.php?mod=farmlandstatus&act=harvest&farmKey=98a43606811ad4a4f7719941d4cf9bba&farmTime=1234953441 HTTP/1.1
//			Host: happyfarm.fivminutes.com
//			User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.6) Gecko/2009011913 Firefox/3.0.6
//			Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//			Accept-Language: zh-cn
//			Accept-Encoding: gzip,deflate
//			Accept-Charset: gb2312,utf-8;q=0.7,*;q=0.7
//			Keep-Alive: 300
//			Connection: keep-alive
//			Cookie: __utma=47175358.1516047794638148900.1234661515.1234879203.1234952649.9; __utmz=47175358.1234952649.9.9.utmcsr=apps.xiaonei.com|utmccn=(referral)|utmcmd=referral|utmcct=/happyfarm; PHPSESSID=qjovg2jhv6pdv5c54iln8r3fj2; __utmc=47175358; __utmb=47175358.1.10.1234952649
//			Referer: http://xnimg.cn/xcube/app/23163/xn30/grange.swf?v=inu29
//			Content-type: application/x-www-form-urlencoded
//			Content-length: 25
//
//			ownerId=223741527&place=6
	public static void harvest(int place) {
		
	}
}
