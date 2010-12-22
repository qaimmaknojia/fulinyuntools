package game;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import util.Common;


public class FarmlandRobot {

	public static String configFile = "configFarmland.prop";
	public static int startX;
	public static int startY;
	public static int firefoxX;
	public static int firefoxY;
	public static int addressX;
	public static int addressY;
	public static String url;
	public static int screenWidth;
	public static int screenHeight;
	public static int exitX;
	public static int exitY;
	
	public static int place1offsetX = -430;
	public static int place1offsetY = 180;
	public static Point[] place = null;
	public static Point shopPlace = null;
	public static int numPlace = 12;
	public static int numAuto = 12;
	//public static String farmlandURL = "http://apps.xiaonei.com/happyfarm";
	public static String picFilePrefix = "E:\\farmland\\snapshot ";
	
	static {
		try {
			File conf = new File(configFile);
			System.out.println("using configuration file: " + conf.getAbsolutePath());
			Properties prop = new Properties();
			InputStream is = new FileInputStream(conf);
			prop.load(is);
			startX = Integer.parseInt(prop.getProperty("startX"));
			startY = Integer.parseInt(prop.getProperty("startY"));
			firefoxX = Integer.parseInt(prop.getProperty("firefoxX"));
			firefoxY = Integer.parseInt(prop.getProperty("firefoxY"));
			addressX = Integer.parseInt(prop.getProperty("addressX"));
			addressY = Integer.parseInt(prop.getProperty("addressY"));
			url = prop.getProperty("url");
			screenWidth = Integer.parseInt(prop.getProperty("screenWidth"));
			screenHeight = Integer.parseInt(prop.getProperty("screenHeight"));
			exitX = Integer.parseInt(prop.getProperty("exitX"));
			exitY = Integer.parseInt(prop.getProperty("exitY"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		haste();			//running
		mainMaintain();		//running
//		browseShop();
//		mainObserve();
//		strawberryRush();
//		strawberryAt9();
//		mainHarvest(args);
//		simpleMaintain();
//		simpleHarvest();
//		simplePlant();
//		fullView();
//		testBuyManure();
//		simpleManure();
//		showBag();
//		initialize("test sell white");
//		sellWhite(10);
//		finalize("test finished");
//		System.exit(0);
//		initialize("test buy manure");
//		buyManure(10);
//		finalize("test finished");
//		System.exit(0);
//		Common.robot.delay(5000);
//		findAndClick("e:\\farmland\\whiteInBag.bmp", true);
//		finalize("test finished");
//		System.exit(0);
//		initialize("test");
//		for (int i = 1; i <= numPlace; i++) {
//			Common.robot.mouseMove(place[i].x, place[i].y);
//			Common.takePic("e:\\farmland\\observe"+i+".jpg");
//		}
//		finalize("test finished");
//		System.exit(0);

	}
	
	public static void haste12() {
		System.out.println("haste12");

		while (true) {
			Common.sleepUntil(1, 40, 0);
			initialize("haste12");
			harvest();
			scarify(12);
			findAndClick("e:\\farmland\\harvest.bmp", true);
			plant("e:\\farmland\\whiteInBag.bmp");
			finalize("haste completed");
			
			Common.sleep(10*60*60*1000);	//11:40:00
			initialize("haste");
			harvest();
			scarify(12);
			findAndClick("e:\\farmland\\harvest.bmp", true);
			plant("e:\\farmland\\cornInBag.bmp");
			finalize("haste completed");
			
			Common.sleep(20*60*60*1000);	//7:40:00
			initialize("haste");
			harvest();
			scarify(12);
			findAndClick("e:\\farmland\\harvest.bmp", true);
			plant("e:\\farmland\\whiteInBag.bmp");
			finalize("haste completed");

			Common.sleep(10*60*60*1000);	//17:40:00
			initialize("haste");
			harvest();
			scarify(12);
			findAndClick("e:\\farmland\\harvest.bmp", true);
			plant("e:\\farmland\\whiteInBag.bmp");
			finalize("haste completed");
			
			Common.sleep(10*60*60*1000);	//3:40:00
			initialize("haste");
			harvest();
			scarify(12);
			findAndClick("e:\\farmland\\harvest.bmp", true);
			plant("e:\\farmland\\cornInBag.bmp");
			finalize("haste completed");

			Common.sleep(20*60*60*1000);	//23:40:00
			initialize("haste");
			harvest();
			scarify(12);
			finalize("haste completed");

			//todo: sellAll, buy 3*numAuto whites and 2*numAuto corns
		}
	}

	public static void haste() {
		System.out.println("haste");
		
		while (true) {
			Common.sleepUntil(1, 30, 0);
			initialize("haste");
			harvest();
			scarify();
			plant("e:\\farmland\\whiteInBag.bmp");
			finalize("haste completed");
			
			Common.sleep(10*60*60*1000);	//11:30:00
			initialize("haste");
			harvest();
			scarify();
			plant("e:\\farmland\\cornInBag.bmp");
			finalize("haste completed");
			
			Common.sleep(20*60*60*1000);	//7:30:00
			initialize("haste");
			harvest();
			scarify();
			plant("e:\\farmland\\whiteInBag.bmp");
			finalize("haste completed");

			Common.sleep(10*60*60*1000);	//17:30:00
			initialize("haste");
			harvest();
			scarify();
			plant("e:\\farmland\\whiteInBag.bmp");
			finalize("haste completed");
			
			Common.sleep(10*60*60*1000);	//3:30:00
			initialize("haste");
			harvest();
			scarify();
			plant("e:\\farmland\\cornInBag.bmp");
			finalize("haste completed");

			Common.sleep(20*60*60*1000);	//23:30:00
			initialize("haste");
			harvest();
			scarify();
			finalize("haste completed");

			//todo: sellAll, buy 3*numAuto whites and 2*numAuto corns
		}
	}
	
	private static void newHarvest() {
		
		System.out.println("new harvest");
		Common.sleep(15*60*60*1000);
		initialize("new harvest");
		harvest();
		scarify();
		plant("e:\\farmland\\potatoInBag.bmp");
		finalize("new harvest finished");
		
		for (int i = 0; i < 3; i++) {
			Common.sleep(25*60*60*1000);
			initialize("new harvest");
			harvest();
			scarify();
			plant("e:\\farmland\\potatoInBag.bmp");
			finalize("new harvest finished");
		}
		
		haste();
	}

	private static void scarify(int i) {
		Common.robot.delay(5000);
		findAndClick("e:\\farmland\\scarify.bmp", true);
		Common.robot.delay(5000);
		Common.moveAndClick(place[i].x, place[i].y);
		Common.robot.delay(5000);
	}

	public static void browseShop()	 {
		Point begin = new Point(360, 500);
		initialize("browse shop");
		findAndClick("e:\\farmland\\shop.bmp", true);
//		for (int i = 0; i < 15; i++) {
//			Common.robot.delay(5000);
//			int x = begin.x+i%5*90;
//			int y = begin.y+i/5*100;
//			Common.moveAndClick(x, y);
//			Common.takePic("e:\\farmland\\info\\" + i + ".jpg");
//			findAndClick("e:\\farmland\\cancel.bmp", true);
//		}
		Common.robot.delay(5000);
		Common.moveAndClick(792, 702);
		for (int i = 15, j = 10; i < 20; i++, j++) {
			Common.robot.delay(5000);
			int x = begin.x+j%5*90;
			int y = begin.y+j/5*100-30;
			Common.moveAndClick(x, y);
			Common.takePic("e:\\farmland\\info\\" + i + ".jpg", screenWidth, screenHeight);
			findAndClick("e:\\farmland\\cancel.bmp", true);
		}
		finalize("browse shop completed");
		System.exit(0);
	}
	
	public static void mainMaintain() {
		System.out.println("maintain");
		while (true) {
			Common.sleepUntil(17, 30, 0);
			initialize("maintain");
			harvest();
			water();
			removeWeed();
			removeWorm();
			Common.robot.mouseMove(place[5].x, place[5].y);
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("maintain completed");
			
			Common.sleepUntil(23, 0, 0);
			initialize("maintain");
			harvest();
			water();
			removeWeed();
			removeWorm();
			Common.robot.mouseMove(place[5].x, place[5].y);
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("maintain completed");

			Common.sleepUntil(8, 0, 0);
			initialize("maintain");
			harvest();
			water();
			removeWeed();
			removeWorm();
			Common.robot.mouseMove(place[5].x, place[5].y);
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("maintain completed");

			Common.sleepUntil(11, 30, 0);
			initialize("maintain");
			harvest();
			water();
			removeWeed();
			removeWorm();
			Common.robot.mouseMove(place[5].x, place[5].y);
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("maintain completed");

		}
	}
	
	public static void strawberryRush() {
		System.out.println("strawberry rush");
		numAuto = 8;
		Common.sleepUntil(22, 57, 0);
		initialize("strawberry rush");
		harvest();
		scarify();
		plant("e:\\farmland\\strawberryInBag.bmp");
		finalize("strawberry planted");
		
		Common.sleep(57*60*60*1000);
		initialize("strawberry rush");
		harvest();
		finalize("strawberry season 1 completed");
		
		Common.sleep(25*60*60*1000);
		initialize("strawberry rush");
		harvest();
		finalize("strawberry season 2 completed");
		
		Common.sleep(25*60*60*1000);
		initialize("strawberry rush");
		harvest();
		scarify();
		finalize("strawberry rush completed");
	}
	
	public static void strawberryAt9() {
		System.out.println("strawberry@9");
		Common.sleepUntil(21, 52, 0);
		Common.sleep(26*60*60*1000);
		initialize("strawberry@9");
		harvest();
		finalize("strawberry@9 harvest banana season 2");
		
		Common.sleep(26*60*60*1000);
		initialize("strawberry@9");
		harvest();
		scarify();
		plant("e:\\farmland\\strawberryInBag.bmp");
		finalize("strawberry@9 planted");
		
		Common.sleep(57*60*60*1000);
		initialize("strawberry@9");
		harvest();
		finalize("strawberry@9 season 1 completed");
		
		Common.sleep(25*60*60*1000);
		initialize("strawberry@9");
		harvest();
		finalize("strawberry@9 season 2 completed");
		
		Common.sleep(25*60*60*1000);
		initialize("strawberry@9");
		harvest();
		finalize("strawberry@9 completed");
	}
	
	public static void mainHarvest(String[] args) {
		System.out.println("harvest");
		
//		Common.sleep(29*60*60*1000);
//		long plantTime = new Date().getTime();
//		initialize("farmland harvest");
//		harvest();
//		scarify();
//		sellAll();
//		buySeed("e:\\farmland\\white.bmp", numAuto);
//		plant("e:\\farmland\\whiteInBag.bmp");
//		buyManure(numAuto*2);
//		manure();
//		Common.takePic(getPicName());
//		finalize("stage 1 completed");
//		
//		Common.sleep(60*60*1000-360000);
//		initialize("farmland harvest");
//		water();
//		removeWeed();
//		removeWorm();
//		manure();
//		Common.takePic(getPicName());
//		finalize("stage 2 completed");
//		
//		Common.sleep(60*60*1000-360000);
//		initialize("farmland harvest");
//		water();
//		removeWeed();
//		removeWorm();
//		manure();
//		Common.takePic(getPicName());
//		finalize("stage 3 completed");
//		
//		Common.sleep(2*60*60*1000-360000);
//		initialize("farmland harvest");
//		water();
//		removeWeed();
//		removeWorm();
//		manure();
//		Common.takePic(getPicName());
//		finalize("stage 4 manure completed");
//
//		Common.sleep(60*60*1000-360000);
//		initialize("farmland harvest");
//		water();
//		removeWeed();
//		removeWorm();
//		Common.takePic(getPicName());
//		finalize("stage 4 maintain completed");
//		
//		Common.sleepUntil(6, 0, 0);
//		Common.sleep(24*60*60*1000);
		while (true) {

			initialize("farmland harvest");
			harvest();
			scarify();
			sellWhite(300);
			buySeed("e:\\farmland\\white.bmp", numAuto-1);	// 1 less than needed
			plant("e:\\farmland\\whiteInBag.bmp");
			long plantTime = new Date().getTime();
			finalize("plant completed");
			
			initialize("farmland harvest");
			buyManure(numAuto*4-1);	// 1 less than needed
			manure();
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("stage 1 completed");
			
			Common.sleep(plantTime+60*60*1000-new Date().getTime()+1000);
			initialize("farmland harvest");
			water();
			removeWeed();
			removeWorm();
			manure();
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("stage 2 completed");
			
			Common.sleep(60*60*1000);
			initialize("farmland harvest");
			water();
			removeWeed();
			removeWorm();
			manure();
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("stage 3 completed");
			
			Common.sleep(2*60*60*1000);
			initialize("farmland harvest");
			water();
			removeWeed();
			removeWorm();
			manure();
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("stage 4 manure completed");

			Common.sleep(60*60*1000);
			initialize("farmland harvest");
			water();
			removeWeed();
			removeWorm();
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("stage 4 maintain completed");
			
			Common.sleep(plantTime+6*60*60*1000-new Date().getTime());
		}
	}

	private static void sellWhite(int num) {
		findAndClick("e:\\farmland\\storeHouse.bmp", true);
		Common.robot.delay(7000);
		findAndClick("e:\\farmland\\whiteInStore.bmp", true);
		Common.robot.delay(3000);
		Point ra = Common.findLandmark("e:\\farmland\\rightArrow.bmp", 400, 300, true, screenWidth, screenHeight);
		for (int i = 0; i < num; i++) {
			Common.moveAndClick(ra.x, ra.y);
			Common.robot.delay(100);
		}
		findAndClick("e:\\farmland\\confirmSellWhite.bmp", true);
		Common.robot.delay(3000);
		findAndClick("e:\\farmland\\confirmSell2.bmp", true);
		Common.robot.delay(3000);
		findAndClick("e:\\farmland\\quitStoreHouse.bmp", true);
	}

	private static void buyManure(int num) {
		findAndClick("e:\\farmland\\shop.bmp", true);
		Common.robot.delay(7000);
		findAndClick("e:\\farmland\\toolTab.bmp", true);
		Common.robot.delay(3000);
		findAndClick("e:\\farmland\\manure.bmp", true);
		Common.robot.delay(3000);
		Point ra = Common.findLandmark("e:\\farmland\\rightArrow.bmp", 400, 300, true, screenWidth, screenHeight);
		for (int i = 0; i < num-1; i++) {
			Common.moveAndClick(ra.x, ra.y);
			Common.robot.delay(100);
		}
		findAndClick("e:\\farmland\\confirmBuyManure.bmp", true);
		Common.robot.delay(3000);
		findAndClick("e:\\farmland\\confirmBuyManure2.bmp", true);
		Common.robot.delay(3000);
		findAndClick("e:\\farmland\\confirmNoMoney.bmp", false);
		findAndClick("e:\\farmland\\quitShop.bmp", true);
	}
	
	public static void pumpkinAt9() {
		numPlace = numAuto = 9;
		System.out.println("pumpkin@9");
		long plantTime = new Date().getTime();
		for (int i = 4; i < 6; i++) {
			Common.sleep(8*60*60*1000);
			initialize("pumpkin@9");
			water();
			removeWeed();
			removeWorm();
			manure();
			Common.takePic(getPicName(), screenWidth, screenHeight);
			finalize("pumpkin stage "+i+" completed");
		}
		for (int i = 0; i < 7; i++) {
			Common.sleep(60*60*1000-300000);
			initialize("pumpkin@9");
			water();
			removeWeed();
			removeWorm();
			finalize("maintain "+(i+1)+" completed");
		}
		Common.sleep(plantTime+24*60*60*1000-new Date().getTime());
		initialize("pumpkin@9");
		harvest();
		sellAll();
		Common.takePic(getPicName(), screenWidth, screenHeight);
		finalize("pumpkin@9 finished");

	}
	
	public static void pumpkinRush() {
		System.out.println("pumpkin rush");
//		long plantTime = new Date().getTime();
//		for (int i = 2; i < 6; i++) {
//			Common.sleep(8*60*60*1000);
//			initialize("pumpkin rush");
//			water();
//			removeWeed();
//			removeWorm();
//			manure();
//			Common.takePic(getPicName());
//			finalize("pumpkin stage "+i+" completed");
//		}
//		for (int i = 0; i < 7; i++) {
//			Common.sleep(60*60*1000);
//			initialize("pumpkin rush");
//			water();
//			removeWeed();
//			removeWorm();
//			finalize("maintain "+(i+1)+" completed");
//		}
//		Common.sleep(plantTime+40*60*60*1000-new Date().getTime());
//		initialize("pumpkin rush");
//		harvest();
//		sellAll();
//		manure();
//		Common.takePic(getPicName());
//		finalize("pumpkin season 2 stage 1 completed");
		
		Common.sleepUntil(16, 47, 0);
		long plantTime = new Date().getTime();
		initialize("pumpkin rush");
		water();
		removeWeed();
		removeWorm();
		manure();
		Common.takePic(getPicName(), screenWidth, screenHeight);
		finalize("pumpkin season 2 stage 2 completed");
		
		for (int i = 0; i < 7; i++) {
			Common.sleep(60*60*1000-300000);
			initialize("pumpkin rush");
			water();
			removeWeed();
			removeWorm();
			finalize("maintain "+(i+1)+" completed");
		}
		Common.sleep(plantTime+8*60*60*1000-new Date().getTime());
		initialize("pumpkin rush");
		harvest();
		sellAll();
		manure();
		Common.takePic(getPicName(), screenWidth, screenHeight);
		finalize("pumpkin season 3 stage 1 completed");

		Common.sleep(8*60*60*1000-300000);
		initialize("pumpkin rush");
		water();
		removeWeed();
		removeWorm();
		manure();
		Common.takePic(getPicName(), screenWidth, screenHeight);
		finalize("pumpkin season 3 stage 2 completed");
		
		for (int i = 0; i < 7; i++) {
			Common.sleep(60*60*1000-300000);
			initialize("pumpkin rush");
			water();
			removeWeed();
			removeWorm();
			finalize("maintain "+(i+1)+" completed");
		}
		Common.sleep(plantTime+24*60*60*1000-new Date().getTime());
		initialize("pumpkin rush");
		harvest();
		sellAll();
		Common.takePic(getPicName(), screenWidth, screenHeight);
		finalize("pumpkin rush finished");

	}
	
	public static void showBag() {
		initialize("farmland show bag");
		findAndClick("e:\\farmland\\bag.bmp", true);
		Common.takePic(getPicName(), screenWidth, screenHeight);
		finalize("show bag completed");
		System.exit(0);
	}
	
	public static void fullView() {
		
		initialize("farmland full view");
		
		for (int i = 1; i < 9; i++) {
			Common.robot.mouseMove(place[i].x, place[i].y);
			String pic = "e:\\farmland\\fullview\\snapshot " + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic, screenWidth, screenHeight);
		}
		
		finalize("full view completed");

		System.exit(0);

	}
	
	private static String getPicName() {
		return picFilePrefix+new Date().toString().replaceAll(":", "_")+".jpg";
	}

	public static void initialize(String msg) {
		Common.notice(msg, 300, 300, 10);
		Common.initRobot();
		Common.enterSite(url, startX, startY, firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310, screenWidth, screenHeight);
		initPlace();
	}

	public static void finalize(String msg) {
		Common.exitFirefox(exitX, exitY);
		System.out.println(new Date().toString() + " " + msg);
	}
	
	public static void simpleManure() {
		initialize("farmland simple manure");
		manure();
		Common.takePic(getPicName(), screenWidth, screenHeight);
		finalize("simple manure completed");
		System.exit(0);
	}
	
	public static void testBuyManure() {
		Common.notice("farmland buy manure test", 300, 300);
		Common.initRobot();
		Common.enterSite(url, startX, startY, firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310, screenWidth, screenHeight);
		initPlace();
		
		buyManure(2);
	}
	
	public static void simpleMaintain() {
		
		initialize("simple maintain");
		water();
		removeWeed();
		removeWorm();
		Common.takePic(getPicName(), screenWidth, screenHeight);
		finalize("simple maintain completed");
		System.exit(0);
	}
	
	public static void simplePlant() {
		
		System.out.println("farmland simple plant");
		Common.sleepUntil(8, 10, 0);
		
		Common.notice("farmland simple plant", 300, 300);
		Common.initRobot();
		Common.enterSite(url, startX, startY, firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310, screenWidth, screenHeight);
		initPlace();
		
		buySeed("e:\\farmland\\white.bmp", 7);
		plant("e:\\farmland\\whiteInBag.bmp");
		
		String pic = getPicName();
		Common.takePic(pic, screenWidth, screenHeight);
		Common.exitFirefox(exitX, exitY);
		System.out.println(new Date().toString() + " simple plant completed");

		System.exit(0);
	}
	
	public static void simpleHarvest() {
		
		System.out.println("simple harvest");
		Common.sleepUntil(14, 57, 0);
		initialize("farmland simple harvest");
		harvest();
//		Common.takePic(getPicName());
		finalize("simple harvest completed");

		System.exit(0);
	}
	
	public static void mainObserve() {
		
		System.out.println("observe");
		initialize("farmland observe");
		
		for (int i = 1; i <= numPlace; i++) {
			Common.robot.mouseMove(place[i].x, place[i].y);
			String pic = "e:\\farmland\\observe\\snapshot " + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic, screenWidth, screenHeight);
		}
		finalize("observe completed");
		System.exit(0);
	}
	
	private static void preprocess() {
		
		Common.sleepUntil(17, 33, 0);

		Common.notice("farmland harvest", 300, 300);
		Common.initRobot();
		Common.enterSite(url, startX, startY, firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310, screenWidth, screenHeight);
		initPlace();
		harvest();
		Common.exitFirefox(exitX, exitY);
		
		Common.sleepUntil(18, 15, 0);
		
		Common.notice("farmland harvest", 300, 300);
		Common.initRobot();
		Common.enterSite(url, startX, startY, firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310, screenWidth, screenHeight);
		initPlace();
		harvest();
		Common.exitFirefox(exitX, exitY);
		
		Common.sleepUntil(20, 25, 0);
		
		Common.notice("farmland harvest", 300, 300);
		Common.initRobot();
		Common.enterSite(url, startX, startY, firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310, screenWidth, screenHeight);
		initPlace();
		harvest();
		Common.exitFirefox(exitX, exitY);

	}

	private static void manure() {
		plant("e:\\farmland\\manureInBag.bmp");
	}

	private static void removeWorm() {
		findAndClick("e:\\farmland\\removeWorm.bmp", true);
		traverseLand(numPlace, 1000);
		traverseLand(numPlace, 1000);
		traverseLand(numPlace, 1000);
	}

	private static void removeWeed() {
		findAndClick("e:\\farmland\\removeWeed.bmp", true);
		traverseLand(numPlace, 1000);
		traverseLand(numPlace, 1000);
		traverseLand(numPlace, 1000);
	}

	private static void water() {
		findAndClick("e:\\farmland\\water.bmp", true);
		traverseLand(numPlace, 1000);
	}

	private static void plant(String seed) {
		
		Common.robot.mouseMove(0, 0);
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\bag.bmp", true);
		Common.robot.delay(10000);
		if (!findAndClick(seed, true)) {
			Common.robot.mouseMove(0, 0);
			Common.robot.delay(1000);
			findAndClick("e:\\farmland\\bag.bmp", true);
			Common.robot.delay(1000);
			findAndClick(seed, true);
		}
		traverseLand(numAuto, 2000);
	}

	private static boolean findAndClick(String target, boolean shouldFind) {
		Common.robot.delay(1000);
		Common.robot.mouseMove(200, 300);
		Point tar = Common.findLandmark(target, 400, 300, shouldFind, screenWidth, screenHeight);
		if (tar.x != -1 && tar.y != -1) {
			Common.moveAndClick(tar.x, tar.y);
			return true;
		}
		return false;
	}

	private static void sellAll() {
		findAndClick("e:\\farmland\\storeHouse.bmp", true);
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\sell.bmp", true);
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\confirmSell.bmp", true);
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\confirmSell2.bmp", true);
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\quitStoreHouse.bmp", true);
	}

	private static void buySeed(String seed, int num) {
		findAndClick("e:\\farmland\\shop.bmp", true);
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\seedTab.bmp", false);
		Common.robot.delay(1000);
		Point ra = Common.findLandmark("e:\\farmland\\rightArrow.bmp", 400, 300, true, screenWidth, screenHeight);
		for (int i = 0; i < num-1; i++) {
			Common.moveAndClick(ra.x, ra.y);
			Common.robot.delay(100);
		}
		findAndClick("e:\\farmland\\confirmBuy.bmp", true);
		Common.robot.delay(2000);
		findAndClick("e:\\farmland\\confirmBuy2.bmp", true);
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\confirmNoMoney.bmp", false);
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\quitShop.bmp", true);
	}
	
	private static void harvest() {
		findAndClick("e:\\farmland\\harvest.bmp", true);
		traverseLand(numPlace, 10000);
	}
	
	private static void traverseLand(int np, int delay) {
		for (int i = 1; i <= np; i++) {
			Common.robot.delay(delay);
			Common.moveAndClick(place[i].x, place[i].y);
			findAndClick("e:\\farmland\\confirm.bmp", false);
		}
	}

	private static void scarify() {
		findAndClick("e:\\farmland\\scarify.bmp", true);
		
		for (int i = 1; i <= numAuto; i++) {
			Common.robot.delay(2000);
			Common.moveAndClick(place[i].x, place[i].y);
			Common.robot.delay(2000);
			findAndClick("e:\\farmland\\cancel.bmp", false);
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
		shopPlace = Common.findLandmark("e:\\farmland\\shop.bmp", 800, 310, true, screenWidth, screenHeight);
		place[1].x = shopPlace.x+place1offsetX;
		place[1].y = shopPlace.y+place1offsetY;
		Common.robot.mouseMove(place[1].x, place[1].y);
		Common.robot.mousePress(InputEvent.BUTTON1_MASK);
		Common.robot.mouseMove(450, 500);
		Common.robot.mouseRelease(InputEvent.BUTTON1_MASK);
		place[1].x = 450;
		place[1].y = 500;
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
