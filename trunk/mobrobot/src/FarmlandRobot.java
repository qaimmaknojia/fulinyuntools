import java.awt.Point;
import java.util.Date;


public class FarmlandRobot {

	public static int place1offsetX = -390;
	public static int place1offsetY = 160;
	public static Point[] place = null;
	public static Point shopPlace = null;
	public static int numPlace = 8;
	public static int numAuto = 8;
	public static String farmlandURL = "http://apps.xiaonei.com/happyfarm";
	public static String picFilePrefix = "E:\\farmland\\snapshot ";

	public static void main(String[] args) {
		mainHarvest(args);
//		mainMaintain(args);
//		simpleHarvest();
//		simplePlant();
//		mainObserve();
//		fullView();
//		simpleMaintain();
	}
	
	public static void fullView() {
		
		Common.notice("farmland full view", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
		
		for (int i = 1; i < 9; i++) {
			Common.robot.mouseMove(place[i].x, place[i].y);
			String pic = "e:\\farmland\\fullview\\snapshot " + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic);
		}
		
		Common.exitFirefox();
		System.out.println(new Date().toString() + " full view completed");

		System.exit(0);

	}
	
	public static void simpleMaintain() {
		
		Common.notice("farmland maintain", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
	
		water();
		removeWeed();
		removeWorm();
		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		Common.takePic(pic);
		Common.exitFirefox();
		System.out.println(new Date().toString() + " maintain completed");

	}
	
	public static void simplePlant() {
		
		System.out.println("farmland simple plant");
		Common.sleepUntil(8, 10, 0);
		
		Common.notice("farmland simple plant", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
		
		buySeed("e:\\farmland\\white.bmp", 7);
		plant("e:\\farmland\\whiteInBag.bmp");
		
		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		Common.takePic(pic);
		Common.exitFirefox();
		System.out.println(new Date().toString() + " simple plant completed");

		System.exit(0);
	}
	
	public static void mainMaintain(String[] args) {
		System.out.println("maintain");
//		try {
//			long sleep = 3600*1000;
//			System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
//			Thread.currentThread().sleep(sleep);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		Common.sleepUntil(17, 30, 0);
		for (int i = 0; i < 3; i++) {
			Common.notice("farmland maintain", 300, 300);
			Common.initRobot();
			Common.enterGame(farmlandURL);
			Common.robot.delay(30000);
			Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
			initPlace();
		
			water();
			removeWeed();
			removeWorm();
			String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic);
			Common.exitFirefox();
			System.out.println(new Date().toString() + " maintain completed");
			try {
				long sleep = 3600*1000;
				System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
				Thread.currentThread().sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	public static void simpleHarvest() {
		
		System.out.println("simple harvest");
		Common.sleepUntil(8, 0, 0);
		
		Common.notice("farmland simple harvest", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();

		harvest();
		scarify();
		sellAll();
		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		Common.takePic(pic);
		Common.exitFirefox();
		System.out.println(new Date().toString() + " simple harvest completed");

		System.exit(0);
	}
	
	public static void mainObserve() {
		
		System.out.println("observe");
//		Common.sleepUntil(0, 30, 0);
		
		Common.notice("farmland observe", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
//		harvest();
//		scarify();
//		sellAll();
//		buySeed("e:\\farmland\\white.bmp", 3);
//		plant("e:\\farmland\\whiteInBag.bmp");
//		String pic = picFilePrefix + "observe\\" + new Date().toString().replaceAll(":", "_")+".jpg";
//		Common.takePic(pic);
//		Common.exitFirefox();
		
		for (int i = 0; i < 8; i++) {
			
			Common.robot.mouseMove(place[1].x, place[1].y);
			String pic = "e:\\farmland\\observe\\snapshot " + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic);
			Common.exitFirefox();
			System.out.println(new Date().toString() + " observe completed");
			
			try {
				long sleep = 60*60*1000;
				System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
				Thread.currentThread().sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Common.notice("farmland observe", 300, 300);
			Common.initRobot();
			Common.enterGame(farmlandURL);
			Common.robot.delay(30000);
			Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
			initPlace();

		}
		
		Common.exitFirefox();
		
		System.exit(0);
	}
	
	public static void mainHarvest(String[] args) {
		System.out.println("harvest");
//		try {
//			long sleep = 9*60*60*1000;
//			System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
//			Thread.currentThread().sleep(sleep);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		preprocess();
		
		while (true) {
	
			Common.notice("farmland harvest", 300, 300);
			Common.initRobot();
			Common.enterGame(farmlandURL);
			Common.robot.delay(30000);
			Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
			initPlace();

			harvest();
			scarify();
			sellAll();
			buySeed("e:\\farmland\\white.bmp", numAuto);//white carrot, actually
			buyMature(numAuto*4);
			plant("e:\\farmland\\whiteInBag.bmp");
			mature();
			String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic);
			Common.exitFirefox();
			System.out.println(new Date().toString() + " harvest completed");
			
			try {
				long sleep = 60*60*1000;
				System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
				Thread.currentThread().sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Common.notice("farmland harvest", 300, 300);
			Common.initRobot();
			Common.enterGame(farmlandURL);
			Common.robot.delay(30000);
			Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
			initPlace();
			water();
			removeWeed();
			removeWorm();
			mature();
			harvest();

			try {
				long sleep = 60*60*1000;
				System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
				Thread.currentThread().sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Common.notice("farmland harvest", 300, 300);
			Common.initRobot();
			Common.enterGame(farmlandURL);
			Common.robot.delay(30000);
			Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
			initPlace();
			water();
			removeWeed();
			removeWorm();
			mature();

			try {
				long sleep = 120*60*1000;
				System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
				Thread.currentThread().sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Common.notice("farmland harvest", 300, 300);
			Common.initRobot();
			Common.enterGame(farmlandURL);
			Common.robot.delay(30000);
			Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
			initPlace();
			water();
			removeWeed();
			removeWorm();
			mature();

			try {
				long sleep = 120*60*1000;
				System.out.println("sleep until " + new Date(new Date().getTime()+sleep).toString());
				Thread.currentThread().sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void preprocess() {
		
		Common.sleepUntil(17, 33, 0);

		Common.notice("farmland harvest", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
		harvest();
		Common.exitFirefox();
		
		Common.sleepUntil(18, 15, 0);
		
		Common.notice("farmland harvest", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
		harvest();
		Common.exitFirefox();
		
		Common.sleepUntil(20, 25, 0);
		
		Common.notice("farmland harvest", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
		harvest();
		Common.exitFirefox();

	}

	private static void mature() {
		plant("e:\\farmland\\matureInBag.bmp");
	}

	private static void removeWorm() {
		findAndClick("e:\\farmland\\removeWorm.bmp");
		traverseLand(numPlace);
	}

	private static void removeWeed() {
		findAndClick("e:\\farmland\\removeWeed.bmp");
		traverseLand(numPlace);
	}

	private static void water() {
		findAndClick("e:\\farmland\\water.bmp");
		traverseLand(numPlace);
	}

	private static void plant(String seed) {
		findAndClick("e:\\farmland\\bag.bmp");
		Common.robot.delay(1000);
		findAndClick(seed);
		traverseLand(numAuto);
	}

	private static boolean findAndClick(String target) {
		Point tar = Common.findLandmark(target, 400, 300);
		if (tar.x != -1 && tar.y != -1) {
			Common.moveAndClick(tar.x, tar.y);
			return true;
		}
		return false;
	}

	private static void sellAll() {
		findAndClick("e:\\farmland\\storeHouse.bmp");
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\sell.bmp");
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\confirmSell.bmp");
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\confirmSell2.bmp");
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\quitStoreHouse.bmp");
	}

	private static void buySeed(String seed, int num) {
		findAndClick("e:\\farmland\\shop.bmp");
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\seedTab.bmp");
		Common.robot.delay(1000);
		for (int i = 0; i < num; i++) {
			findAndClick(seed);
			Common.robot.delay(1000);
			findAndClick("e:\\farmland\\confirmBuy.bmp");
			Common.robot.delay(2000);
			findAndClick("e:\\farmland\\confirmBuy2.bmp");
			Common.robot.delay(1000);
			findAndClick("e:\\farmland\\confirmNoMoney.bmp");
		}
		findAndClick("e:\\farmland\\quitShop.bmp");
	}
	
	private static void buyMature(int num) {
		findAndClick("e:\\farmland\\shop.bmp");
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\toolTab.bmp");
		Common.robot.delay(1000);
		for (int i = 0; i < num; i++) {
			findAndClick("e:\\farmland\\mature.bmp");
			Common.robot.delay(1000);
			findAndClick("e:\\farmland\\confirmBuy.bmp");
			Common.robot.delay(2000);
			findAndClick("e:\\farmland\\confirmBuy2.bmp");
			Common.robot.delay(1000);
			findAndClick("e:\\farmland\\confirmNoMoney.bmp");
		}
		findAndClick("e:\\farmland\\quitShop.bmp");
	}
	
	private static void harvest() {
		findAndClick("e:\\farmland\\harvest.bmp");
		traverseLand(numPlace);
	}
	
	private static void traverseLand(int np) {
		for (int i = 1; i <= np; i++) {
			Common.robot.delay(3000);
			Common.moveAndClick(place[i].x, place[i].y);
			findAndClick("e:\\farmland\\confirmUpgrade.bmp");
		}
	}

	private static void scarify() {
		findAndClick("e:\\farmland\\scarify.bmp");
		
		for (int i = 1; i <= numAuto; i++) {
			Common.robot.delay(2000);
			Common.moveAndClick(place[i].x, place[i].y);
			Common.robot.delay(2000);
			findAndClick("e:\\farmland\\cancelScarify.bmp");
		}

	}
	
	private static void testPlacePos() {
		for (int i = 1; i < 19; i++) {
			Common.robot.mouseMove(place[i].x, place[i].y);
			Common.robot.delay(2000);
		}
	}

	public static void initPlace() {
		place = new Point[20];
		for (int i = 0; i < 20; i++) place[i] = new Point();
		shopPlace = Common.findLandmark("e:\\farmland\\shop.bmp", 800, 310);
		place[1].x = shopPlace.x+place1offsetX;
		place[1].y = shopPlace.y+place1offsetY;
		for (int i = 2; i < 19; i++) {
			place[i].x = place[1].x+(i-1)/3*100-(i-1)%3*100;
			place[i].y = place[1].y+(i-1)/3*50+(i-1)%3*50;
		}
	}
	
//	private static void initDialog() {
//		JButton findHarvest = createJButton("find harvest", "e:\\farmland\\harvest.bmp");
//		JButton findScarify = createJButton("find scarify", "e:\\farmland\\scarify.bmp");
//		JButton findDeadLeaf = createJButton("find dead leaf", "e:\\farmland\\deadleaf.bmp");
//		JButton findBag = createJButton("find bag", "e:\\farmland\\bag.bmp");
//		JButton findShop = createJButton("find shop", "e:\\farmland\\shop.bmp");
//		JButton findStoreHouse = createJButton("find store house", "e:\\farmland\\storeHouse.bmp");
//		JButton findCarrot = createJButton("find carrot", "e:\\farmland\\carrot.bmp");
//		JButton findConfirm = createJButton("find confirm", "e:\\farmland\\confirmBuy.bmp");
//		
//		noticeDialog.setLayout(new FlowLayout());
//		noticeDialog.add(findHarvest);
//		noticeDialog.add(findScarify);
//		noticeDialog.add(findDeadLeaf);
//		noticeDialog.add(findBag);
//		noticeDialog.add(findShop);
//		noticeDialog.add(findStoreHouse);
//		noticeDialog.add(findCarrot);
//		noticeDialog.add(findConfirm);
//		
//		noticeDialog.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
//		noticeDialog.setLocation(new Point(10, 650));
//		noticeDialog.setSize(1000, 70);
//		noticeDialog.setAlwaysOnTop(true);
//		noticeDialog.setVisible(true);
//	}

//	private static JButton createJButton(String title, final String target) {
//		JButton ret = new JButton(title);
//		ret.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				robot.delay(500);
//				Point p = Common.findLandmark(target, mouseX, mouseY);
//				robot.mouseMove(p.x, p.y);
//			}
//		});
//		return ret;
//	}
}
