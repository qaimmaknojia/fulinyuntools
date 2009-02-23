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
	
	public static void main1(String[] args) throws Exception {
		Common.initRobot();
		System.out.println("card");
		Thread.currentThread().sleep(6*60*60*1000);
		while (true) {
			while (new File(Common.workingSign).exists()) {
				System.out.println(new Date().toString() + " another robot working, waiting for 40 seconds");
				Thread.currentThread().sleep(40*1000);
			}
			new File(Common.workingSign).createNewFile();

			Date start = new Date();
			long startms = start.getTime();
			Common.notice(new Date().toString(), 300, 300);
			Common.enterGame(cardURL);
			Common.robot.delay(10000);
			Common.takePic(picFilePrefix + start.toString().replaceAll(":", "_") + ".jpg");
			Common.exitFirefox();
			
			new File(Common.workingSign).delete();
			
			Date end = new Date();
			long timeConsumption = end.getTime()-startms;
			System.out.println(end.toString() + " logged in card");
			Thread.currentThread().sleep(24*60*60*1000-timeConsumption);
		}
	}
}
