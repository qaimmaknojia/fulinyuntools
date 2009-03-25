import java.awt.Point;
import java.util.Date;


public class FarmlandRobot {

	public static int place1offsetX = -450;
	public static int place1offsetY = 160;
	public static Point[] place = null;
	public static Point shopPlace = null;
	public static int numPlace = 10;
	public static int numAuto = 10;
	public static String farmlandURL = "http://apps.xiaonei.com/happyfarm";
	public static String picFilePrefix = "E:\\farmland\\snapshot ";

	public static void main(String[] args) {
//		mainMaintain();		//running		
//		strawberryRush();	
//		strawberryAt9();	
//		mainHarvest(args);
		simpleMaintain();
//		simpleHarvest();
//		simplePlant();
//		mainObserve();
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
//		browseShop();
//		harvestSchedule();	//running
//		initialize("test");
//		findAndClick("e:\\farmland\\water.bmp", true);
//		findAndClick("e:\\farmland\\removeWeed.bmp", true);
//		findAndClick("e:\\farmland\\removeWorm.bmp", true);
//		Common.robot.delay(5000);
//		findAndClick("e:\\farmland\\whiteInBag.bmp", true);
//		finalize("test finished");
//		System.exit(0);
	}
	
	private static void harvestSchedule() {
		System.out.println("harvest");
		
		Common.sleepUntil(13, 14, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");
		
		Common.sleepUntil(14, 23, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");
		
		Common.sleepUntil(15, 7, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");

		Common.sleepUntil(15, 14, 0);
		Common.sleep(24*60*60*1000);
		initialize("harvest");
		harvest();
		scarify(6);
		scarify(10);
		plant("e:\\farmland\\whiteInBag.bmp");
		finalize("harvest finished");

		Common.sleepUntil(20, 23, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");
		
		Common.sleepUntil(22, 7, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");

		Common.sleepUntil(15, 7, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");

		Common.sleepUntil(17, 7, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");

		Common.sleepUntil(19, 14, 0);
		initialize("harvest");
		harvest();
		scarify(7);
		scarify(8);
		plant("e:\\farmland\\whiteInBag.bmp");
		finalize("harvest finished");

		Common.sleepUntil(23, 7, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");

		Common.sleepUntil(2, 23, 0);
		initialize("harvest");
		harvest();
		scarify(9);
		plant("e:\\farmland\\whiteInBag.bmp");
		finalize("harvest finished");

		Common.sleepUntil(5, 14, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");
		
		Common.sleepUntil(12, 23, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");

		Common.sleepUntil(21, 7, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");

		Common.sleepUntil(23, 7, 0);
		initialize("harvest");
		harvest();
		finalize("harvest finished");

		Common.sleepUntil(0, 7, 0);
		initialize("harvest");
		harvest();
		scarify(4);
		plant("e:\\farmland\\whiteInBag.bmp");
		finalize("harvest finished");
		
		Common.sleepUntil(3, 7, 0);
		Common.sleep(24*60*60*1000);
		initialize("harvest");
		harvest();
		scarify(5);
		plant("e:\\farmland\\whiteInBag.bmp");
		finalize("harvest finished");

		Common.sleepUntil(5, 7, 0);
		initialize("harvest");
		harvest();
		scarify(1);
		scarify(2);
		scarify(3);
		plant("e:\\farmland\\whiteInBag.bmp");
		finalize("harvest finished");

		System.exit(0);
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
		Common.moveAndClick(792, 742);
		for (int i = 15, j = 10; i < 20; i++, j++) {
			Common.robot.delay(5000);
			int x = begin.x+j%5*90;
			int y = begin.y+j/5*100;
			Common.moveAndClick(x, y);
			Common.takePic("e:\\farmland\\info\\" + i + ".jpg");
			findAndClick("e:\\farmland\\cancel.bmp", true);
		}
		finalize("browse shop completed");
		System.exit(0);
	}
	
	public static void mainMaintain() {
		System.out.println("maintain");
		while (true) {
			Common.sleepUntil(8, 0, 0);
			initialize("maintain");		
			water();
			removeWeed();
			removeWorm();
			Common.takePic(getPicName());
			finalize("maintain completed");
			Common.sleepUntil(11, 30, 0);
			initialize("maintain");		
			water();
			removeWeed();
			removeWorm();
			Common.takePic(getPicName());
			finalize("maintain completed");
			Common.sleepUntil(17, 30, 0);
			initialize("maintain");		
			water();
			removeWeed();
			removeWorm();
			Common.takePic(getPicName());
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
			Common.takePic(getPicName());
			finalize("stage 1 completed");
			
			Common.sleep(plantTime+60*60*1000-new Date().getTime()+1000);
			initialize("farmland harvest");
			water();
			removeWeed();
			removeWorm();
			manure();
			Common.takePic(getPicName());
			finalize("stage 2 completed");
			
			Common.sleep(60*60*1000);
			initialize("farmland harvest");
			water();
			removeWeed();
			removeWorm();
			manure();
			Common.takePic(getPicName());
			finalize("stage 3 completed");
			
			Common.sleep(2*60*60*1000);
			initialize("farmland harvest");
			water();
			removeWeed();
			removeWorm();
			manure();
			Common.takePic(getPicName());
			finalize("stage 4 manure completed");

			Common.sleep(60*60*1000);
			initialize("farmland harvest");
			water();
			removeWeed();
			removeWorm();
			Common.takePic(getPicName());
			finalize("stage 4 maintain completed");
			
			Common.sleep(plantTime+6*60*60*1000-new Date().getTime());
		}
	}

	private static void sellWhite(int num) {
		findAndClick("e:\\farmland\\storeHouse.bmp", true);
		Common.robot.delay(7000);
		findAndClick("e:\\farmland\\whiteInStore.bmp", true);
		Common.robot.delay(3000);
		Point ra = Common.findLandmark("e:\\farmland\\rightArrow.bmp", 400, 300, true);
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
		Point ra = Common.findLandmark("e:\\farmland\\rightArrow.bmp", 400, 300, true);
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
			Common.takePic(getPicName());
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
		Common.takePic(getPicName());
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
		Common.takePic(getPicName());
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
		Common.takePic(getPicName());
		finalize("pumpkin season 3 stage 1 completed");

		Common.sleep(8*60*60*1000-300000);
		initialize("pumpkin rush");
		water();
		removeWeed();
		removeWorm();
		manure();
		Common.takePic(getPicName());
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
		Common.takePic(getPicName());
		finalize("pumpkin rush finished");

	}
	
	public static void showBag() {
		initialize("farmland show bag");
		findAndClick("e:\\farmland\\bag.bmp", true);
		Common.takePic(getPicName());
		finalize("show bag completed");
		System.exit(0);
	}
	
	public static void fullView() {
		
		initialize("farmland full view");
		
		for (int i = 1; i < 9; i++) {
			Common.robot.mouseMove(place[i].x, place[i].y);
			String pic = "e:\\farmland\\fullview\\snapshot " + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic);
		}
		
		finalize("full view completed");

		System.exit(0);

	}
	
	private static String getPicName() {
		return picFilePrefix+new Date().toString().replaceAll(":", "_")+".jpg";
	}

	public static void initialize(String msg) {
		Common.notice(msg, 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
	}

	public static void finalize(String msg) {
		Common.exitFirefox();
		System.out.println(new Date().toString() + " " + msg);
	}
	
	public static void simpleManure() {
		initialize("farmland simple manure");
		manure();
		Common.takePic(getPicName());
		finalize("simple manure completed");
		System.exit(0);
	}
	
	public static void testBuyManure() {
		Common.notice("farmland buy manure test", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		Common.waitForLandmark("e:\\farmland\\shop.bmp", 800, 310);
		initPlace();
		
		buyManure(2);
	}
	
	public static void simpleMaintain() {
		
		initialize("simple maintain");
		water();
		removeWeed();
		removeWorm();
		Common.takePic(getPicName());
		finalize("simple maintain completed");
		System.exit(0);
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
		
		String pic = getPicName();
		Common.takePic(pic);
		Common.exitFirefox();
		System.out.println(new Date().toString() + " simple plant completed");

		System.exit(0);
	}
	
	public static void simpleHarvest() {
		
		System.out.println("simple harvest");
		
		initialize("farmland simple harvest");
		harvest();
		Common.takePic(getPicName());
		finalize("simple harvest completed");

		System.exit(0);
	}
	
	public static void mainObserve() {
		
		System.out.println("observe");
		initialize("farmland observe");
		
		for (int i = 1; i <= numPlace; i++) {
			Common.robot.mouseMove(place[i].x, place[i].y);
			String pic = "e:\\farmland\\observe\\snapshot " + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic);
		}
		finalize("observe completed");
		System.exit(0);
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
		Point tar = Common.findLandmark(target, 400, 300, shouldFind);
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
		Point ra = Common.findLandmark("e:\\farmland\\rightArrow.bmp", 400, 300, true);
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
		shopPlace = Common.findLandmark("e:\\farmland\\shop.bmp", 800, 310, true);
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
