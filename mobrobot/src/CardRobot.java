import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;


public class CardRobot {

	public static int startX = 30;
	public static int startY = 1006;
	public static int firefoxX = 78;
	public static int firefoxY = 294;
	public static int addressX = 765;
	public static int addressY = 74;
	public static int exitX = 1267;
	public static int exitY = 11;
	public static int screenWidth = 1280;
	public static int screenHeight = 1024;

	public static String cardURL = "http://apps.xiaonei.com/boyaa_texas/index.php";
	public static Robot robot = null;
	public static String picFilePrefix = "E:\\cardtemp\\snapshot";
	public static String workingSign = "E:\\mobtemp\\robotworking";
	public static Dialog noticeDialog = new Dialog((Frame)null, "");

	static {
		noticeDialog.add(new Label("card task begins!"));
	}
	
	public static void main(String[] args) {
		try {
			main1(args);
		} catch (Exception e) {
			new File(workingSign).delete();
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void notice() throws Exception {

		Dialog d = noticeDialog;
		d.setTitle(new Date().toString());
		d.setLocation(new Point(300, 300));
		d.setSize(200, 70);
		d.setAlwaysOnTop(true);
		d.setVisible(true);
		Thread.currentThread().sleep(5000);
		d.setVisible(false);
		
	}
	
	public static void main1(String[] args) throws Exception {
		initRobot();
		System.out.println("card");
		Thread.currentThread().sleep(15*60*60*1000);
		while (true) {
			while (new File(workingSign).exists()) {
				System.out.println(new Date().toString() + " another robot working, waiting for 40 seconds");
				Thread.currentThread().sleep(40*1000);
			}
			new File(workingSign).createNewFile();

			Date start = new Date();
			long startms = start.getTime();
			notice();
			enterCard();
			robot.delay(10000);
			takePic(picFilePrefix + start.toString().replaceAll(":", "_") + ".jpg");
			exitFirefox();
			
			new File(workingSign).delete();
			
			Date end = new Date();
			long timeConsumption = end.getTime()-startms;
			System.out.println(end.toString() + " logged in card");
			Thread.currentThread().sleep(24*60*60*1000-timeConsumption);
		}
	}
	
	public static void initRobot() throws Exception {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		robot = new Robot(screen);
	}
	
	public static void enterCard() throws Exception {
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
		for (int i = 0; i < cardURL.length(); i++) {
			if (cardURL.charAt(i) == ':') {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (cardURL.charAt(i) == '_') {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else {
				robot.keyPress(Character.toUpperCase(cardURL.charAt(i)));
				robot.keyRelease(Character.toUpperCase(cardURL.charAt(i)));
			}
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public static void takePic(String fn) throws Exception {
		robot.delay(2000);
		BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, screenWidth, screenHeight));
		ImageIO.write(image, "JPEG", new File(fn));
	}

	public static void exitFirefox() throws Exception {
		robot.delay(5000);
		robot.mouseMove(exitX, exitY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

}
