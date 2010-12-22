package game;
import java.util.Date;

import util.Common;


public class CockfightRobot {

	public static void main(String[] args) {
		while (true) {
			train();
		}
//		observe();
//		feed();	//running
	}
	
	public static void train() {
		System.out.println("train");
		Common.initRobot();
		Common.sleepUntil(20, 50, 0);
		for (int i = 0; i < 5; i++) {
			Common.sleep(2*60*60*1000);
			Common.enterSite("http://apps.xiaonei.com/cockfight");
			Common.robot.delay(30000);
			Common.moveAndClick(1270, 927);	//scroll down
			Common.robot.delay(2000);
			Common.moveAndClick(788, 164);	//flight training
			Common.exitFirefox();
		}
	}
	
	public static void feed() {
		System.out.println("feed");
		Common.initRobot();
		Common.sleepUntil(22, 40, 0);
		while (true) {
			Common.enterSite("http://apps.xiaonei.com/cockfight");
			Common.robot.delay(30000);
			Common.moveAndClick(367, 745);
			Common.exitFirefox();
			Common.sleep(24*60*60*1000);
		}
	}
	
	public static void observe() {
		Common.initRobot();
		for (int i = 0; i < 5; i++) {
			Common.sleep(2*60*60*1000);
			Common.enterSite("http://apps.xiaonei.com/cockfight");
			Common.robot.delay(30000);
			Common.takePic("e:\\cockfight " + new Date().toString().replaceAll(":", "_") + ".jpg");
			Common.exitFirefox();
		}
		System.exit(0);
	}
}
