import java.security.MessageDigest;


public class MD5Calc {

	public static void main(String[] args) {
		System.out.println(md5("1234953425inu"));
	}
	
	public static String md5(String input) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(input.getBytes());
			byte[] output = md5.digest();
			String ret = "";
			for (int i = 0; i < output.length; i++) {
				int v = output[i] & 0xFF;
				if (v < 16)
					ret += "0";
				ret += Integer.toString(v, 16).toLowerCase();
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getKey(String time) {
		return md5(time+"inu");
	}
}
