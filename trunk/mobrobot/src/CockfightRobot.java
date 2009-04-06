
public class CockfightRobot {

	public static void main(String[] args) {
		while (true) {
			train();
		}
	}
	
	public static void train() {
		Common.initRobot();
		Common.sleepUntil(23, 0, 0);
		Common.enterGame("http://apps.xiaonei.com/cockfight");
		Common.robot.delay(30000);
		Common.moveAndClick(1270, 927);	//scroll down
		Common.robot.delay(2000);
		Common.moveAndClick(788, 164);	//flight training
		Common.exitFirefox();
		for (int i = 25; i < 33; i += 2) {
			Common.sleep(2*60*60*1000);
			Common.enterGame("http://apps.xiaonei.com/cockfight");
			Common.robot.delay(30000);
			Common.moveAndClick(1270, 927);	//scroll down
			Common.robot.delay(2000);
			Common.moveAndClick(788, 164);	//flight training
			Common.exitFirefox();
		}
	}
}
