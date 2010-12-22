package game;
import java.io.File;
import java.util.Date;

import util.Common;


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

			Common.notice("card", 300, 300);
			Common.enterSite(cardURL);
			Common.robot.delay(10000);
			Common.takePic(picFilePrefix + new Date().toString().replaceAll(":", "_") + ".jpg");
			Common.exitFirefox();
			
			System.out.println(new Date().toString() + " logged in card");
			Common.sleepUntil(2, 0, 0);
		}
	}
}
