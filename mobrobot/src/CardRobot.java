import java.io.File;
import java.util.Date;


public class CardRobot {

	public static String cardURL = "http://apps.xiaonei.com/boyaa_texas/index.php";
	public static String picFilePrefix = "E:\\cardtemp\\snapshot";

	public static void main(String[] args) {
		try {
			main1(args);
		} catch (Exception e) {
			new File(Common.workingSign).delete();
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main1(String[] args) {
		Common.initRobot();
		System.out.println("card");
//		long sleep = 16*60*60*1000;
//		System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
//		Thread.currentThread().sleep(sleep);
		Common.sleepUntil(2, 0, 0);
		while (true) {

			Date start = new Date();
			long startms = start.getTime();
			Common.notice("card", 300, 300);
			Common.enterGame(cardURL);
			Common.robot.delay(10000);
			Common.takePic(picFilePrefix + start.toString().replaceAll(":", "_") + ".jpg");
			Common.exitFirefox();
			
			Date end = new Date();
			long timeConsumption = end.getTime()-startms;
			System.out.println(end.toString() + " logged in card");
			Common.sleepUntil(2, 0, 0);
		}
	}
}
