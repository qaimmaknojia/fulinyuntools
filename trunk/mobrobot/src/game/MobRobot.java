package game;
import java.awt.Point;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import util.Common;

public class MobRobot {

	public static String configFile = "configMob.prop";
	public static int startX = 30;
	public static int startY = 1006;
	public static int firefoxX = 78;
	public static int firefoxY = 294;
	public static int addressX = 765;
	public static int addressY = 74;
	public static int taskX = 251;
	public static int taskY = 313;
	public static int doX = 878;
	public static int doY = 786;
	public static int exitX = 1267;
	public static int exitY = 11;
	public static int scrollX;
	public static int scrollY;
	public static int screenWidth;
	public static int screenHeight;
	public static int brotherBeginX;
	public static int brotherBeginY;
	public static int brotherEndX;
	public static int storeX;
	public static int storeY;
	public static int gunBeginX;
	public static int gunBeginY;
	public static int gunEndX;
	public static int helmetBeginX;
	public static int helmetBeginY;
	public static int helmetEndX;
	public static int carBeginX;
	public static int carBeginY;
	public static int carEndX;
	public static int buyGunX;
	public static int buyGunY;
	public static int buyHelmetX;
	public static int buyHelmetY;
	public static int buyCarX;
	public static int buyCarY;
	public static int moneyBeginX;
	public static int moneyBeginY;
	public static int moneyEndX;
	public static int sellCarX;
	public static int sellCarY;
	public static int buyCar1X;
	public static int buyCar1Y;
	public static int car1BeginX;
	public static int car1BeginY;
	public static int car1EndX;
	public static int levelBeginX;
	public static int levelBeginY;
	public static int levelEndX;
	public static int fightX;
	public static int fightY;
	public static int doFightX;
	public static int doFightY;
	public static int healthBeginX;
	public static int healthBeginY;
	public static int healthEndX;
	public static int richTaskX;
	public static int richTaskY;
	public static int prepareX;
	public static int prepareY;
	public static int veryRichTaskX;
	public static int veryRichTaskY;
	public static int scrollupX;
	public static int scrollupY;
	public static int hurtBeginX;
	public static int hurtBeginY;
	public static int hurtEndX;
	public static int lawyerX;
	public static int lawyerY;
	public static int staminaBeginX;
	public static int staminaBeginY;
	public static int staminaEndX;
	
	public static String mobURL = "http://mob.xiaonei.com/home.do";
	public static String picFilePrefix = "E:\\mobtemp\\snapshot ";
	
	public static String enterLandmark = "E:\\mobtemp\\enter.bmp";
	public static String taskLandmark = "E:\\mobtemp\\task.bmp";
	public static String fightLandmark = "E:\\mobtemp\\fight.bmp";
	public static String jailLandmark = "E:\\mobtemp\\jail.bmp";
	
	static {
		try {
			
			Properties prop = new Properties();
			InputStream is = MobRobot.class.getResourceAsStream(configFile);
			prop.load(is);
			startX = Integer.parseInt(prop.getProperty("startX"));
			startY = Integer.parseInt(prop.getProperty("startY"));
			firefoxX = Integer.parseInt(prop.getProperty("firefoxX"));
			firefoxY = Integer.parseInt(prop.getProperty("firefoxY"));
			addressX = Integer.parseInt(prop.getProperty("addressX"));
			addressY = Integer.parseInt(prop.getProperty("addressY"));
			taskX = Integer.parseInt(prop.getProperty("taskX"));
			taskY = Integer.parseInt(prop.getProperty("taskY"));
			doX = Integer.parseInt(prop.getProperty("doX"));
			doY = Integer.parseInt(prop.getProperty("doY"));
			scrollX = Integer.parseInt(prop.getProperty("scrollX"));
			scrollY = Integer.parseInt(prop.getProperty("scrollY"));
			exitX = Integer.parseInt(prop.getProperty("exitX"));
			exitY = Integer.parseInt(prop.getProperty("exitY"));
			screenWidth = Integer.parseInt(prop.getProperty("screenWidth"));
			screenHeight = Integer.parseInt(prop.getProperty("screenHeight"));
			brotherBeginX = Integer.parseInt(prop.getProperty("brotherBeginX"));
			brotherBeginY = Integer.parseInt(prop.getProperty("brotherBeginY"));
			brotherEndX = Integer.parseInt(prop.getProperty("brotherEndX"));
			storeX = Integer.parseInt(prop.getProperty("storeX"));
			storeY = Integer.parseInt(prop.getProperty("storeY"));
			gunBeginX = Integer.parseInt(prop.getProperty("gunBeginX"));
			gunBeginY = Integer.parseInt(prop.getProperty("gunBeginY"));
			gunEndX = Integer.parseInt(prop.getProperty("gunEndX"));
			helmetBeginX = Integer.parseInt(prop.getProperty("helmetBeginX"));
			helmetBeginY = Integer.parseInt(prop.getProperty("helmetBeginY"));
			helmetEndX = Integer.parseInt(prop.getProperty("helmetEndX"));
			carBeginX = Integer.parseInt(prop.getProperty("carBeginX"));
			carBeginY = Integer.parseInt(prop.getProperty("carBeginY"));
			carEndX = Integer.parseInt(prop.getProperty("carEndX"));
			buyGunX = Integer.parseInt(prop.getProperty("buyGunX"));
			buyGunY = Integer.parseInt(prop.getProperty("buyGunY"));
			buyHelmetX = Integer.parseInt(prop.getProperty("buyHelmetX"));
			buyHelmetY = Integer.parseInt(prop.getProperty("buyHelmetY"));
			buyCarX = Integer.parseInt(prop.getProperty("buyCarX"));
			buyCarY = Integer.parseInt(prop.getProperty("buyCarY"));
			moneyBeginX = Integer.parseInt(prop.getProperty("moneyBeginX"));
			moneyBeginY = Integer.parseInt(prop.getProperty("moneyBeginY"));
			moneyEndX = Integer.parseInt(prop.getProperty("moneyEndX"));
			sellCarX = Integer.parseInt(prop.getProperty("sellCarX"));
			sellCarY = Integer.parseInt(prop.getProperty("sellCarY"));
			buyCar1X = Integer.parseInt(prop.getProperty("buyCar1X"));
			buyCar1Y = Integer.parseInt(prop.getProperty("buyCar1Y"));
			car1BeginX = Integer.parseInt(prop.getProperty("car1BeginX"));
			car1BeginY = Integer.parseInt(prop.getProperty("car1BeginY"));
			car1EndX = Integer.parseInt(prop.getProperty("car1EndX"));
			levelBeginX = Integer.parseInt(prop.getProperty("levelBeginX"));
			levelBeginY = Integer.parseInt(prop.getProperty("levelBeginY"));
			levelEndX = Integer.parseInt(prop.getProperty("levelEndX"));
			fightX = Integer.parseInt(prop.getProperty("fightX"));
			fightY = Integer.parseInt(prop.getProperty("fightY"));
			doFightX = Integer.parseInt(prop.getProperty("doFightX"));
			doFightY = Integer.parseInt(prop.getProperty("doFightY"));
			healthBeginX = Integer.parseInt(prop.getProperty("healthBeginX"));
			healthBeginY = Integer.parseInt(prop.getProperty("healthBeginY"));
			healthEndX = Integer.parseInt(prop.getProperty("healthEndX"));
			richTaskX = Integer.parseInt(prop.getProperty("richTaskX"));
			richTaskY = Integer.parseInt(prop.getProperty("richTaskY"));
			prepareX = Integer.parseInt(prop.getProperty("prepareX"));
			prepareY = Integer.parseInt(prop.getProperty("prepareY"));
			veryRichTaskX = Integer.parseInt(prop.getProperty("veryRichTaskX"));
			veryRichTaskY = Integer.parseInt(prop.getProperty("veryRichTaskY"));
			scrollupX = Integer.parseInt(prop.getProperty("scrollupX"));
			scrollupY = Integer.parseInt(prop.getProperty("scrollupY"));
			hurtBeginX = Integer.parseInt(prop.getProperty("hurtBeginX"));
			hurtBeginY = Integer.parseInt(prop.getProperty("hurtBeginY"));
			hurtEndX = Integer.parseInt(prop.getProperty("hurtEndX"));
			lawyerX = Integer.parseInt(prop.getProperty("lawyerX"));
			lawyerY = Integer.parseInt(prop.getProperty("lawyerY"));
			staminaBeginX = Integer.parseInt(prop.getProperty("staminaBeginX"));
			staminaBeginY = Integer.parseInt(prop.getProperty("staminaBeginY"));
			staminaEndX = Integer.parseInt(prop.getProperty("staminaEndX"));
			
//			System.out
//					.println("startX = " + startX + "; startY = " + startY + "; firefoxX = "
//							+ firefoxX + "; firefoxY = " + firefoxY + "; addressX = " + addressX
//							+ "; addressY = " + addressY + "; taskX = " + taskX + "; taskY = "
//							+ taskY + "; scrollX = " + scrollX + "; scrollY = " + scrollY
//							+ "; doX = " + doX + "; doY = " + doY + "; exitX = " + exitX
//							+ "; exitY = " + exitY + "; screenWidth = " + screenWidth
//							+ "; screenHeight = " + screenHeight + "; brotherBeginX = "
//							+ brotherBeginX + "; brotherBeginY = " + brotherBeginY
//							+ "; brotherEndX = " + brotherEndX + "; storeX = " + storeX
//							+ "; storeY = " + storeY + "; gunBeginX = " + gunBeginX
//							+ "; gunBeginY = " + gunBeginY + "; gunEndX = " + gunEndX
//							+ "; helmetBeginX = " + helmetBeginX + "; helmetBeginY = "
//							+ helmetBeginY + "; helmetEndX = " + helmetEndX + "; carBeginX = "
//							+ carBeginX + "; carBeginY = " + carBeginY + "; carEndX = " + carEndX
//							+ "; buyGunX = " + buyGunX + "; buyGunY = " + buyGunY
//							+ "; buyHelmetX = " + buyHelmetX + "; buyHelmetY = " + buyHelmetY
//							+ "; buyCarX = " + buyCarX + "; buyCarY = " + buyCarY
//							+ "; moneyBeginX = " + moneyBeginX + "; moneyBeginY = " + moneyBeginY
//							+ "; moneyEndX = " + moneyEndX);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Common.initRobot();
//			mainFight(args);
			mainVeryRich(args);		//running
//			mainCheck(args);
//			notice();
//			mainDoVeryRichTask();
//			mainPrepare();
//			System.exit(0);
		} catch (Exception e) {
			new File(Common.workingSign).delete();
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void mainCheck(String[] args) {
		while (true) {
			try {
				Common.enterSite(mobURL, startX, startY, 
						firefoxX, firefoxY, addressX, addressY);
				String pic = picFilePrefix + new Date().toString().replaceAll(":", "_") + ".jpg";
				Common.takePic(pic, screenWidth, screenHeight);
				Common.sendMail("check", new Date().toString(), pic);
				Common.exitFirefox(exitX, exitY);
				System.out.println("sleep until " + new Date(new Date().getTime()+60*60*1000).toString());
				Thread.currentThread().sleep(60*60*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void mainVeryRich(String[] args) {
		
		System.out.println("task");
//		Common.sleepUntil(11, 45, 0);
//		mainDoVeryRichTask();
//		mainPrepare();
//		mainDoVeryRichTask();
//		mainPrepare();
		
		while (true) {
			Common.sleepUntil(22, 45, 0);
			mainDoVeryRichTask();
			mainPrepare();
			mainDoVeryRichTask();
			mainPrepare();
			mainDoVeryRichTask();
			mainPrepare();
			Common.sleepUntil(4, 45, 0);
			mainDoVeryRichTask();
			mainPrepare();
			Common.sleepUntil(7, 0, 0);
			mainDoVeryRichTask();
			mainPrepare();
			Common.sleepUntil(11, 45, 0);
			mainDoVeryRichTask();
			mainPrepare();
		}
	}
	
	private static long mainPrepare() {

		Date start = new Date();
		long startms = start.getTime();
		System.out.println(start.toString() + " prepare begins");
		Common.notice("mob", 300, 300, 10);
		prepare();
		Date end = new Date();
		System.out.println(end.toString() + " prepare finished");
		long timeConsumption = end.getTime()-startms;

		return timeConsumption;

	}
	
	private static long mainDoVeryRichTask() {
		
		Date start = new Date();
		long startms = start.getTime();
		System.out.println(start.toString() + " task begins");
		Common.notice("mob", 300, 300, 10);
		doVeryRichTask();
		Date end = new Date();
		System.out.println(end.toString() + " task finished");
		long timeConsumption = end.getTime()-startms;
		
		return timeConsumption;
		
	}
	
	private static void prepare() {

		Common.enterSite(mobURL, startX, startY, firefoxX, firefoxY, addressX, addressY);
		
		Common.robot.delay(5000);
		Common.waitForLandmark(enterLandmark, 190, 180, screenWidth, screenHeight);
		
//		int stamina = getStamina();
//		if (stamina < 10) {
//			exitFirefox();
//			try {
//				System.out.println("waiting for " + ((10-stamina)*5) + " minutes");
//				Thread.currentThread().sleep((10-stamina)*5*60*1000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			enterMob();
//			robot.delay(5000);
//			waitForLandmark(enterLandmark);
//		}
		findAndClick("e:\\mobtemp\\gotoTask.bmp", 0, 0, true);

		Common.robot.delay(5000);
		Common.waitForLandmark(taskLandmark, 150, 340, screenWidth, screenHeight);
//		Point p = Common.findLandmark(jailLandmark, 160, 340);
//		if (p.x != -1 && p.y != -1) {
////			robot.mouseMove(lawyerX, lawyerY);
////			robot.mousePress(InputEvent.BUTTON1_MASK);
////			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//			
//			Common.exitFirefox();
//			
//			try {
//				System.out.println("in jail, waiting for 250 minutes");
//				Thread.currentThread().sleep(50*5*60*1000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			doVeryRichTask();
//			prepare();
//			doVeryRichTask();
//			
//			Common.enterGame(mobURL);
//			
//			Common.robot.delay(10000);
//			waitForLandmark(enterLandmark, 190, 180);
//			
////			stamina = getStamina();
////			if (stamina < 10) {
////				exitFirefox();
////				try {
////					System.out.println("waiting for " + ((10-stamina)*5) + " minutes");
////					Thread.currentThread().sleep((10-stamina)*5*60*1000);
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////				enterMob();
////				robot.delay(10000);
////				waitForLandmark(enterLandmark);
////			}
//			
//			Common.moveAndClick(taskX, taskY);
//
//			Common.robot.delay(5000);
//			waitForLandmark(taskLandmark, 150, 340);
//		}

		Common.moveAndClick(scrollX, scrollY);
		Common.robot.delay(1000);
		Common.moveAndClick(scrollX, scrollY);

		Common.robot.delay(5000);
		findAndClick("e:\\mobtemp\\prepare.bmp", 800, 300, true);

		Common.robot.delay(5000);
		Common.moveAndClick(scrollupX, scrollupY);
		Common.robot.delay(1000);
		Common.moveAndClick(scrollupX, scrollupY);

		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		Common.takePic(pic, screenWidth, screenHeight);
//		Common.sendMail("prepare", new Date().toString(), pic);

		Common.exitFirefox(exitX, exitY);
	}

	private static void doVeryRichTask() {
		
		Common.enterSite(mobURL, startX, startY, firefoxX, firefoxY, addressX, addressY);
		
		Common.robot.delay(5000);
		Common.waitForLandmark(enterLandmark, 190, 180, screenWidth, screenHeight);
		
//		int stamina = getStamina();
//		if (stamina < 40) {
//			exitFirefox();
//			try {
//				System.out.println("waiting for " + ((40-stamina)*5) + " minutes");
//				Thread.currentThread().sleep((40-stamina)*5*60*1000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			enterMob();
//			robot.delay(5000);
//			waitForLandmark(enterLandmark);
//		}
		findAndClick("e:\\mobtemp\\gotoTask.bmp", 0, 0, true);

		Common.robot.delay(5000);
		Common.waitForLandmark(taskLandmark, 150, 340, screenWidth, screenHeight);
		Common.moveAndClick(scrollX, scrollY);
		Common.robot.delay(1000);
		Common.moveAndClick(scrollX, scrollY);
		
		Common.robot.delay(5000);
		findAndClick("e:\\mobtemp\\do.bmp", 800, 300, true);
		
		Common.robot.delay(5000);
		Common.moveAndClick(scrollupX, scrollupY);
		Common.robot.delay(1000);
		Common.moveAndClick(scrollupX, scrollupY);
		
		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		Common.takePic(pic, screenWidth, screenHeight);
//		Common.sendMail("task", new Date().toString(), pic);
		
		Common.exitFirefox(exitX, exitY);
	}

	private static boolean findAndClick(String target, int startx, int starty, boolean shouldFind) {
		Common.robot.delay(1000);
		Common.robot.mouseMove(0, 0);
		Point tar = Common.findLandmark(target, startx, starty, shouldFind, screenWidth, screenHeight);
		if (tar.x != -1 && tar.y != -1) {
			Common.moveAndClick(tar.x, tar.y);
			return true;
		}
		return false;
	}

//	public static void mainRich(String[] args) {
//
//			initRobot();
//			try {
//				Thread.currentThread().sleep(25*5*60*1000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			while (true) {
//				while (new File(workingSign).exists()) {
//					System.out.println(new Date().toString() + " another robot working, waiting for 40 seconds");
//					try {
//						Thread.currentThread().sleep(40*1000);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				try {
//					new File(workingSign).createNewFile();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				Date start = new Date();
//				long startms = start.getTime();
//				System.out.println(start.toString() + " task begins");
//				notice();
//				doRichTask();
//				Date end = new Date();
//				System.out.println(end.toString() + " task finished");
//				long timeConsumption = end.getTime()-startms;
//
//				try {
//					Thread.currentThread().sleep(30 * 5 * 60 * 1000 - timeConsumption);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//			}
//		}

//		public static void mainFight(String[] args) {
//
//			System.out.println("fight");
////			try {
////				Thread.currentThread().sleep(40*3*60*1000);
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
//			while (true) {
//				Date start = new Date();
//				long startms = start.getTime();
//				System.out.println(start.toString() + " task begins");
//				notice();
//
//				enterMob();
//				
//				int health = getHealth();
//				System.out.println("health: " + health);
//				if (health < 90) {
//					exitFirefox();
//					System.out.println("waiting for " + ((145-health)*3) + " minutes");
//					try {
//						Thread.currentThread().sleep((145-health) * 180 * 1000);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					continue;
//				}
//
//				robot.delay(5000);
//				robot.mouseMove(fightX, fightY);
//				robot.mousePress(InputEvent.BUTTON1_MASK);
//				robot.mouseRelease(InputEvent.BUTTON1_MASK);
//				
//				robot.delay(15000);
//				robot.mouseMove(doFightX, doFightY);
//				robot.mousePress(InputEvent.BUTTON1_MASK);
//				robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//				robot.delay(5000);
//				String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
//				takePic(pic);
//				sendMail("fight", new Date().toString(), pic);
//				
//				int hurt = getHurt();
//				System.out.println("hurt: " + hurt);
//				exitFirefox();
//				
//				Date end = new Date();
//				long timeConsumption = end.getTime()-startms;
//				System.out.println(end.toString() + " task finished");
//				try {
//					System.out.println("waiting for " + (hurt*3) + " minutes");
//					Thread.currentThread().sleep(hurt * 3 * 60 * 1000 - timeConsumption);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		
//		private static int getHurt() {
//			
//			robot.delay(10000);
//			robot.mouseMove(hurtBeginX, hurtBeginY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseMove(hurtEndX, hurtBeginY);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//			robot.delay(5000);
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_C);
//			robot.keyRelease(KeyEvent.VK_C);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//
//			Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//			Transferable content = cb.getContents(null);
//			String str = "";
//			try {
//				str = (String) content.getTransferData(DataFlavor.stringFlavor);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println(str);
//			int numStart;
//			for (numStart = 0; numStart < str.length(); numStart++) 
//				if (Character.isDigit(str.charAt(numStart))) break;
//			if (numStart == str.length()) return 100;
//			int numEnd;
//			for (numEnd = numStart+1; numEnd < str.length(); numEnd++)
//				if (!Character.isDigit(str.charAt(numEnd))) break;
//			return Integer.parseInt(str.substring(numStart, numEnd));
//
//		}
	
//	private static int getStamina() {
//		try {
//			robot.delay(5000);
//			robot.mouseMove(staminaBeginX, staminaBeginY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseMove(staminaEndX, staminaBeginY);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//			
//			robot.delay(5000);
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_C);
//			robot.keyRelease(KeyEvent.VK_C);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//	
//			Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//			Transferable content = cb.getContents(null);
//			String str = (String) content.getTransferData(DataFlavor.stringFlavor);
//			System.out.println(str);
//			int numStart;
//			for (numStart = 0; numStart < str.length(); numStart++) 
//				if (Character.isDigit(str.charAt(numStart))) break;
//			if (numStart == str.length()) return 40;
//			int ret = Integer.parseInt(str.substring(numStart, str.indexOf("/")));
//			System.out.println("stamina: " + ret);
//			return ret;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 40;
//		}
//		
//	}
	
//	private static void doRichTask() {
//		
//		enterMob();
//		
//		robot.delay(5000);
//		robot.mouseMove(taskX, taskY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.delay(5000);
//		robot.mouseMove(scrollX, scrollY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.delay(5000);
//		robot.mouseMove(richTaskX, richTaskY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//		robot.delay(5000);
//		robot.mouseMove(scrollupX, scrollupY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
//		takePic(pic);
//		sendMail("", new Date().toString(), pic);
//
//		exitFirefox();
//		
//	}

//	private static int getHealth() {
//		
//		robot.delay(10000);
//		robot.mouseMove(healthBeginX, healthBeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(healthEndX, healthBeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.delay(5000);
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = "";
//		try {
//			str = (String) content.getTransferData(DataFlavor.stringFlavor);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(str);
////		for (int i = 0; i < str.length(); i++)
////			System.out.println(str.charAt(i));
//		int numStart;
//		for (numStart = 0; numStart < str.length(); numStart++) 
//			if (Character.isDigit(str.charAt(numStart))) break;
//		if (numStart == str.length()) return 15;
//		return Integer.parseInt(str.substring(numStart, str.indexOf("/")));
//		
//	}

//	private static int getLevel() {
//
//		enterMob();
//
//		robot.delay(5000);
//		robot.mouseMove(levelBeginX, levelBeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(levelEndX, levelBeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.delay(5000);
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = "";
//		try {
//			str = (String) content.getTransferData(DataFlavor.stringFlavor);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(str);
//		for (int i = 0; i < str.length(); i++)
//			System.out.println(str.charAt(i));
//
//		exitFirefox();
//
//		return Integer.parseInt(str.substring(3));
//		
//	}

//	private static void adjustEquip() {
//
//		enterMob();
//		enterStore();
//		int moneyAmount = getMoneyAmount();
//		int carNumber = getCarNumber();
//		System.out.println("money amount: " + moneyAmount + "; car number: " + carNumber);
//		while (moneyAmount >= 1150000 && carNumber > 10) {
//			sellCar();
//			buyCar1();
//			moneyAmount -= 1150000;
//			carNumber--;
//		}
//		exitFirefox();
//		
//	}

//	private static void sellCar() {
//
//		for (int i = 0; i < 3; i++) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		}
//
//		robot.delay(5000);
//		robot.mouseMove(sellCarX, sellCarY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//	}

//	private static void buyCar1() {
//
//		for (int i = 0; i < 3; i++) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		}
//
//		robot.delay(5000);
//		robot.mouseMove(buyCar1X, buyCar1Y);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//	}

//	public static void checkWeapon() {
//
//		enterMob();
//		int brotherNumber = getBrotherNumber();
//		System.out.println("got brother number: " + brotherNumber);
//		enterStore();
//		int moneyAmount = getMoneyAmount();
//		int gunNumber = getGunNumber();
//		int helmetNumber = getHelmetNumber();
//		int car1Number = getCar1Number();
//		System.out.println("money amount: " + moneyAmount + "; gun number: " + gunNumber
//				+ "; helmet number: " + helmetNumber + "; car1 number: " + car1Number);
//		exitFirefox();
//		while (moneyAmount >= 1500000 + 16000 + 200000 && gunNumber < brotherNumber + 1) {
//			enterMob();
//			enterStore();
//			buyGun();
//			buyHelmet();
//			buyCar1();
//			gunNumber++;
//			helmetNumber++;
//			car1Number++;
//			moneyAmount -= 1716000;
//			System.out.println("money amount: " + moneyAmount + "; gun number: " + gunNumber
//					+ "; helmet number: " + helmetNumber + "; car1 number: " + car1Number);
//			exitFirefox();
//		}
//	}

//	public static void doTask() {
//
//		enterMob();
//		robot.delay(5000);
//		robot.mouseMove(taskX, taskY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		if (configFile.equals("/configHome.prop")) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		} else {
//
//		}
//
//		robot.delay(5000);
//		robot.mouseMove(doX, doY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
//		takePic(pic);
//		sendMail("", new Date().toString(), pic);
//
//		exitFirefox();
//		
//	}

//	public static int getBrotherNumber() {
//		
//		robot.delay(5000);
//		robot.mouseMove(brotherBeginX, brotherBeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(brotherEndX, brotherBeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = "";
//		try {
//			str = (String) content.getTransferData(DataFlavor.stringFlavor);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(str);
//		for (int i = 0; i < str.length(); i++)
//			System.out.println(str.charAt(i));
//		return Integer.parseInt(str.substring(5, 8));
//		
//	}

//	public static void enterStore() {
//		
//		robot.delay(5000);
//		robot.mouseMove(storeX, storeY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//	}

//	public static int getMoneyAmount() {
//		
//		robot.delay(5000);
//		robot.mouseMove(moneyBeginX, moneyBeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(moneyEndX, moneyBeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = "";
//		try {
//			str = (String) content.getTransferData(DataFlavor.stringFlavor);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(str);
//		for (int i = 0; i < str.length(); i++)
//			System.out.println(str.charAt(i));
//		return Integer.parseInt(str.substring(4));
//		
//	}

//	public static int getGunNumber() {
//		
//		if (configFile.equals("/configHome.prop")) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		} else {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		}
//		robot.delay(5000);
//		robot.mouseMove(gunBeginX, gunBeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(gunEndX, gunBeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.delay(2000);
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
//		System.out.println(str);
//		return Integer.parseInt(str);
//		
//	}

//	public static int getHelmetNumber() {
//
//		for (int i = 0; i < 3; i++) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		}
//
//		robot.delay(5000);
//		robot.mouseMove(helmetBeginX, helmetBeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(helmetEndX, helmetBeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.delay(2000);
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
//		System.out.println(str);
//		return Integer.parseInt(str);
//		
//	}

//	public static int getCarNumber() {
//		robot.delay(5000);
//		robot.mouseMove(carBeginX, carBeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(carEndX, carBeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
//		System.out.println(str);
//		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
//		return Integer.parseInt(str);
//	}

//	public static int getCarNumber() {
//
//		if (configFile.equals("/configHome.prop")) {
//			for (int i = 0; i < 3; i++) {
//				robot.delay(5000);
//				robot.mouseMove(scrollX, scrollY);
//				robot.mousePress(InputEvent.BUTTON1_MASK);
//				robot.mouseRelease(InputEvent.BUTTON1_MASK);
//			}
//		} else {
//			for (int i = 0; i < 2; i++) {
//				robot.delay(5000);
//				robot.mouseMove(scrollX, scrollY);
//				robot.mousePress(InputEvent.BUTTON1_MASK);
//				robot.mouseRelease(InputEvent.BUTTON1_MASK);
//			}
//		}
//
//		robot.delay(5000);
//		robot.mouseMove(carBeginX, carBeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(carEndX, carBeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.delay(2000);
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
//		System.out.println(str);
//		return Integer.parseInt(str);
//	}

//	public static int getCar1Number() {
//
//		for (int i = 0; i < 3; i++) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		}
//
//		robot.delay(5000);
//		robot.mouseMove(car1BeginX, car1BeginY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseMove(car1EndX, car1BeginY);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		robot.delay(2000);
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_C);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//
//		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
//		Transferable content = cb.getContents(null);
//		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
//		System.out.println(str);
//		for (int i = 0; i < str.length(); i++)
//			System.out.println(str.charAt(i));
//		return Integer.parseInt(str);
//		
//	}

//	public static void buyGun() {
//		
//		if (configFile.equals("/configHome.prop")) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		} else {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//		}
//
//		robot.delay(5000);
//		robot.mouseMove(buyGunX, buyGunY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//	}

//	public static void buyHelmet() {
//		
//		for (int i = 0; i < 3; i++) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		}
//
//		robot.delay(5000);
//		robot.mouseMove(buyHelmetX, buyHelmetY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		
//	}

//	public static void buyCar() {
//		for (int i = 0; i < 3; i++) {
//			robot.delay(5000);
//			robot.mouseMove(scrollX, scrollY);
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		}
//		
//		robot.delay(5000);
//		robot.mouseMove(buyCarX, buyCarY);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//	}
}
