import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;

public class MobRobot {

	public static String configFile = "/configHome.prop";

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
	
	public static String mobURL = "http://mob.xiaonei.com/home.do";
	public static Robot robot = null;
	public static String picFilePrefix = "E:\\mobtemp\\snapshot";
	
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
			
			System.out.println("startX = " + startX + "; startY = " + startY + "; firefoxX = " + firefoxX + 
					"; firefoxY = " + firefoxY + "; addressX = " + addressX + "; addressY = " + addressY + 
					"; taskX = " + taskX + "; taskY = " + taskY + "; scrollX = " + scrollX + "; scrollY = " + 
					scrollY + "; doX = " + doX + "; doY = " + doY + "; exitX = " + exitX + "; exitY = " + 
					exitY + "; screenWidth = " + screenWidth + "; screenHeight = " + screenHeight + 
					"; brotherBeginX = " + brotherBeginX + "; brotherBeginY = " + brotherBeginY + 
					"; brotherEndX = " + brotherEndX + "; storeX = " + storeX + "; storeY = " + storeY + 
					"; gunBeginX = " + gunBeginX + "; gunBeginY = " + gunBeginY + "; gunEndX = " + gunEndX + 
					"; helmetBeginX = " + helmetBeginX + "; helmetBeginY = " + helmetBeginY + "; helmetEndX = " + 
					helmetEndX + "; carBeginX = " + carBeginX + "; carBeginY = " + carBeginY + "; carEndX = " + 
					carEndX + "; buyGunX = " + buyGunX + "; buyGunY = " + buyGunY + "; buyHelmetX = " + 
					buyHelmetX + "; buyHelmetY = " + buyHelmetY + "; buyCarX = " + buyCarX + "; buyCarY = " + 
					buyCarY + "; moneyBeginX = " + moneyBeginX + "; moneyBeginY = " + moneyBeginY + 
					"; moneyEndX = " + moneyEndX);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
//		while(true) {
//			initRobot();
//			enterMob();
//			doTask();
//			takePic(picFilePrefix+ new Date().toString().replaceAll(":", "_") + ".jpg");
//			exit();
//			Thread.currentThread().sleep(50*60*1000);
//		}
		
//		initRobot();
//		enterMob();
//		enterStore();
//		buyGun();
//		buyHelmet();
//		buyCar();
//		exitMob();
		
//		while (true) {
//			checkWeapon();
//			Thread.currentThread().sleep(50*60*1000);
//		}
		initRobot();
		while (true) {
			System.out.println(new Date().toString() + " task begins");
//			doTask();
			int level = getLevel();
			System.out.println("level: " + level);
			if (level >= 40) break;
			checkWeapon();
			adjustEquip();
			System.out.println(new Date().toString() + " task finished");
			Thread.currentThread().sleep(50*60*1000);
		}
	}
	
	private static int getLevel() throws Exception {
		enterMob();
		
		robot.delay(5000);
		robot.mouseMove(levelBeginX, levelBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(levelEndX, levelBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.delay(5000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		
		exitMob();
		
		return Integer.parseInt(str.substring(3));
	}

	private static void adjustEquip() throws Exception {
		enterMob();
		enterStore();
		int moneyAmount = getMoneyAmount();
		int carNumber = getCarNumber1();
		System.out.println("money amount: " + moneyAmount + "; car number: " + carNumber);
		while (moneyAmount >= 1150000 && carNumber > 10) {
			sellCar();
			buyCar1();
			moneyAmount -= 1150000;
			carNumber--;
		}
		exitMob();
	}

	private static void sellCar() throws Exception {
		if (configFile.equals("/configHome.prop")) for (int i = 0; i < 3; i++) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}

		robot.delay(5000);
		robot.mouseMove(sellCarX, sellCarY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	private static void buyCar1() throws Exception {

		if (configFile.equals("/configHome.prop")) for (int i = 0; i < 3; i++) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}

		robot.delay(5000);
		robot.mouseMove(buyCar1X, buyCar1Y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void checkWeapon() throws Exception {
		enterMob();
		int brotherNumber = getBrotherNumber();
		System.out.println("got brother number: " + brotherNumber);
		enterStore();
		int moneyAmount = getMoneyAmount();
		int gunNumber = getGunNumber();
		int helmetNumber = getHelmetNumber();
		int car1Number = getCar1Number();
		System.out.println("money amount: " + moneyAmount + "; gun number: " + gunNumber + 
				"; helmet number: " + helmetNumber + "; car1 number: " + car1Number);
		exitMob();
		while (moneyAmount >= 1500000+16000+200000 && gunNumber < brotherNumber+1) {
			enterMob();
			enterStore();
			buyGun();
			buyHelmet();
			buyCar1();
			gunNumber++;
			helmetNumber++;
			car1Number++;
			moneyAmount -= 1716000;
			System.out.println("money amount: " + moneyAmount + "; gun number: " + gunNumber + 
					"; helmet number: " + helmetNumber + "; car1 number: " + car1Number);
			exitMob();
		}
	}
	
	public static void initRobot() throws Exception {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		robot = new Robot(screen);
	}
	
	public static void enterMob() throws Exception {
		robot.delay(2000);
		robot.mouseMove(startX, startY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		robot.keyPress(KeyEvent.VK_WINDOWS);
//		robot.keyRelease(KeyEvent.VK_WINDOWS);
		
		robot.delay(2000);
		robot.mouseMove(firefoxX, firefoxY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(addressX, addressY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.delay(2000);
		for (int i = 0; i < mobURL.length(); i++) {
			if ((int)mobURL.charAt(i) == 58) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else {
				robot.keyPress(Character.toUpperCase(mobURL.charAt(i)));
				robot.keyRelease(Character.toUpperCase(mobURL.charAt(i)));
			}
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public static void doTask() throws Exception {
		enterMob();
		robot.delay(5000);
		robot.mouseMove(taskX, taskY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		if (configFile.equals("/configHome.prop")) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} else {
			
		}
		
		robot.delay(5000);
		robot.mouseMove(doX, doY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		exitMob();
	}
	
	public static void takePic(String fn) throws Exception {
		robot.delay(2000);
		BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, screenWidth, screenHeight));
		ImageIO.write(image, "JPEG", new File(fn));
	}
	
	public static void exitMob() throws Exception {
		robot.delay(5000);
		robot.mouseMove(exitX, exitY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static int getBrotherNumber() throws Exception {
		robot.delay(5000);
		robot.mouseMove(brotherBeginX, brotherBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(brotherEndX, brotherBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str.substring(5, 8));
	}
	
	public static void enterStore() throws Exception {
		robot.delay(5000);
		robot.mouseMove(storeX, storeY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static int getMoneyAmount() throws Exception {
		robot.delay(5000);
		robot.mouseMove(moneyBeginX, moneyBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(moneyEndX, moneyBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str.substring(4));
	}
	
	public static int getGunNumber() throws Exception {
		if (configFile.equals("/configHome.prop")) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		robot.delay(5000);
		robot.mouseMove(gunBeginX, gunBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(gunEndX, gunBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str);
	}
	
	public static int getHelmetNumber() throws Exception {
		if (configFile.equals("/configHome.prop")) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		robot.delay(5000);
		robot.mouseMove(helmetBeginX, helmetBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(helmetEndX, helmetBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str);
	}
	
	public static int getCarNumber() throws Exception {
		robot.delay(5000);
		robot.mouseMove(carBeginX, carBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(carEndX, carBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str);
	}
	
	public static int getCarNumber1() throws Exception {
		
		if (configFile.equals("/configHome.prop")) for (int i = 0; i < 3; i++) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		
		robot.delay(5000);
		robot.mouseMove(carBeginX, carBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(carEndX, carBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str);
	}
	
	public static int getCar1Number() throws Exception {
		
		if (configFile.equals("/configHome.prop")) for (int i = 0; i < 3; i++) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		
		robot.delay(5000);
		robot.mouseMove(car1BeginX, car1BeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(car1EndX, car1BeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String)content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str);
	}
	
	public static void buyGun() throws Exception {
		if (configFile.equals("/configHome.prop")) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		robot.delay(5000);
		robot.mouseMove(buyGunX, buyGunY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void buyHelmet() throws Exception {
		if (configFile.equals("/configHome.prop")) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		robot.delay(5000);
		robot.mouseMove(buyHelmetX, buyHelmetY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void buyCar() throws Exception {
		if (configFile.equals("/configHome.prop")) {
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(5000);
			robot.mouseMove(scrollX, scrollY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		robot.delay(5000);
		robot.mouseMove(buyCarX, buyCarY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
