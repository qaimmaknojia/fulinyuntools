import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class Ex02 {

	public Ex02() {
	}

	public static void main(String[] args) throws Exception {

		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod("http://hk-in-f99.google.com/bookmarks/?output=xml&num=10000&zx=7186");
		method.setRequestHeader("Accept", "*/*");
		method.setRequestHeader("Accept-Encoding", "gzip, deflate");
		method.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; POTU(RR:27011715:0); InfoPath.2)");
		method.setRequestHeader("Host", "www.google.com");
		method.setRequestHeader("Connection", "Keep-Alive");
		method.setRequestHeader("Cookie", "PREF=ID=ba43e3277f6d3a20:TB=2:LD=en:NW=1:CR=2:TM=1203824609:LM=1204441623:DV=AA:GM=1:S=b0FaCd4xfAEcMaxs; SID=DQAAAHgAAAB2raUwW4_Z8MwBnZH-4aEmyXVMGphMG0N6IE8rpSul3ZXSHNf2S5vdr61cJx2DyPU--zXdZPOuiQ0mmvsLM2_E1xe7k9ldiDDLj0cMfWTVPKwHix9SgZE5CYhfe5xC_Wj9KrHBNMBks-BmL-u8mPDEmNOu8_a178nIj4y6mK0q8w");
//		NameValuePair[] data = { new NameValuePair("email", "fulinyun@gmail.com"),
//				new NameValuePair("id", "aedf313041aad334444e82c941d92b67"),
//				new NameValuePair("persistent_google_user", "1") };
//		method.setRequestBody(data);
//		method.setRequestBody("9f\n"
//		+ "<?xml version=\"1.0\"?>\n"
//		+ "<toplevel>\n"
//		+ "<email data=\"fulinyun@gmail.com\"/>" +
//				"<id data=\"aedf313041aad334444e82c941d92b67\"/>" +
//				"<persistent_google_user data=\"1\"/>\n"
//		+ "</toplevel>\n\n0");

		client.executeMethod(method);
		Header[] responseHeaders = method.getResponseHeaders();
		for (int i = 0; i < responseHeaders.length; i++) {
			System.out.println(responseHeaders[i].getName() + " = "
					+ responseHeaders[i].getValue());
		}
		System.out.println("----------- end of header -------------");
		//			byte[] responseBody = method.getResponseBody();
		//			System.out.println(new String(responseBody));
		System.out.println(method.getResponseBodyAsString());
	}
}


