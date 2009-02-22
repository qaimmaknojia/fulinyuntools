import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;


public class FarmlandRobot {

	public static String configFile = "/configSchool.prop";
	
	public static Dialog noticeDialog = new Dialog((Frame)null, "");
	public static Robot robot = null;
	public static int screenWidth = 1024;
	public static int screenHeight = 768;
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static int place1offsetX = -390;
	public static int place1offsetY = 160;
	public static Point[] place = null;
	public static Point shopPlace = null;
	public static int numPlace = 7;
	public static String farmlandURL = "http://apps.xiaonei.com/happyfarm";
	public static String workingSign = "E:\\mobtemp\\robotworking";
	public static int startX = 23;
	public static int startY = 752;
	public static int firefoxX = 100;
	public static int firefoxY = 445;
	public static int addressX = 521;
	public static int addressY = 64;
	public static int exitX = 1014;
	public static int exitY = 8;
	public static int countdown = 5;
	public static JButton delay = new JButton(""+countdown);
	
	static {
		try {
			
			noticeDialog.setLayout(new FlowLayout());
			noticeDialog.add(new Label("task begins!"));
			delay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					countdown += 60;
					delay.setText(""+countdown);
				}
			});
			noticeDialog.add(delay);
			noticeDialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					countdown += 60;
					delay.setText(""+countdown);
				}
			});

			Properties prop = new Properties();
			InputStream is = MobRobot.class.getResourceAsStream(configFile);
			prop.load(is);
			startX = Integer.parseInt(prop.getProperty("startX"));
			startY = Integer.parseInt(prop.getProperty("startY"));
			firefoxX = Integer.parseInt(prop.getProperty("firefoxX"));
			firefoxY = Integer.parseInt(prop.getProperty("firefoxY"));
			addressX = Integer.parseInt(prop.getProperty("addressX"));
			addressY = Integer.parseInt(prop.getProperty("addressY"));
			screenWidth = Integer.parseInt(prop.getProperty("screenWidth"));
			screenHeight = Integer.parseInt(prop.getProperty("screenHeight"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		initRobot();
		enterFarmland();
//		robot.delay(5000);
		initPlace();
//		testPlacePos();
//		initDialog();
		harvest();
		scarify();
		sellAll();
		plant();
		exitFirefox();
	}
	
	private static void plant() {
		findAndClick("e:\\farmland\\bag.bmp");
		findAndClick("e:\\farmland\\carrotInBag.bmp");
		traverseLand();
	}

	private static void findAndClick(String target) {
		Point tar = findLandmark(target, 0, 0);
		robot.mouseMove(tar.x, tar.y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	private static void sellAll() {
		findAndClick("e:\\farmland\\storeHouse.bmp");
		findAndClick("e:\\farmland\\sell.bmp");
		findAndClick("e:\\farmland\\confirm.bmp");
		
	}

	public static void enterFarmland() {

		while (new File(workingSign).exists()) {
			System.out.println(new Date().toString() + " another robot working, waiting for 40 seconds");
			try {
				Thread.currentThread().sleep(40*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			new File(workingSign).createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		robot.delay(2000);
		robot.mouseMove(startX, startY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(2000);
		robot.mouseMove(firefoxX, firefoxY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(addressX, addressY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(2000);
		for (int i = 0; i < farmlandURL.length(); i++) {
			if (farmlandURL.charAt(i) == ':') {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else {
				robot.keyPress(Character.toUpperCase(farmlandURL.charAt(i)));
				robot.keyRelease(Character.toUpperCase(farmlandURL.charAt(i)));
			}
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
	}

	public static void exitFirefox() {

		robot.delay(5000);
		robot.mouseMove(exitX, exitY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		new File(workingSign).delete();
		
	}
	
	private static void harvest() {
		findAndClick("e:\\farmland\\harvest.bmp");
		traverseLand();
	}
	
	private static void traverseLand() {
		for (int i = 1; i <= numPlace; i++) {
			robot.delay(1000);
			robot.mouseMove(place[i].x, place[i].y);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
	}

	private static void scarify() {
		Point scarify = findLandmark("e:\\farmland\\scarify.bmp", 0, 0);
		robot.mouseMove(scarify.x, scarify.y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		for (int i = 1; i <= numPlace; i++) {
			robot.delay(1000);
			robot.mouseMove(place[i].x, place[i].y);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			Point cancel = findLandmark("e:\\farmland\\cancelScarify.bmp", 0, 0);
			if (cancel.x != -1 && cancel.y != -1) {
				robot.delay(1000);
				robot.mouseMove(cancel.x, cancel.y);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		}

	}
	
	private static void testPlacePos() {
		for (int i = 1; i < 19; i++) {
			robot.mouseMove(place[i].x, place[i].y);
			robot.delay(2000);
		}
	}
	
	private static void initDialog() {
		JButton findHarvest = createJButton("find harvest", "e:\\farmland\\harvest.bmp");
		JButton findScarify = createJButton("find scarify", "e:\\farmland\\scarify.bmp");
		JButton findDeadLeaf = createJButton("find dead leaf", "e:\\farmland\\deadleaf.bmp");
		JButton findBag = createJButton("find bag", "e:\\farmland\\bag.bmp");
		JButton findShop = createJButton("find shop", "e:\\farmland\\shop.bmp");
		JButton findStoreHouse = createJButton("find store house", "e:\\farmland\\storeHouse.bmp");
		JButton findCarrot = createJButton("find carrot", "e:\\farmland\\carrot.bmp");
		JButton findConfirm = createJButton("find confirm", "e:\\farmland\\confirmBuy.bmp");
		
		noticeDialog.setLayout(new FlowLayout());
		noticeDialog.add(findHarvest);
		noticeDialog.add(findScarify);
		noticeDialog.add(findDeadLeaf);
		noticeDialog.add(findBag);
		noticeDialog.add(findShop);
		noticeDialog.add(findStoreHouse);
		noticeDialog.add(findCarrot);
		noticeDialog.add(findConfirm);
		
		noticeDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		noticeDialog.setLocation(new Point(10, 650));
		noticeDialog.setSize(1000, 70);
		noticeDialog.setAlwaysOnTop(true);
		noticeDialog.setVisible(true);
	}

	private static JButton createJButton(String title, final String target) {
		JButton ret = new JButton(title);
		ret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				robot.delay(500);
				Point p = findLandmark(target, mouseX, mouseY);
				robot.mouseMove(p.x, p.y);
			}
		});
		return ret;
	}

	public static void initRobot() {

		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		try {
			robot = new Robot(screen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static Point findLandmark(String bmpLm, int startX, int startY) {
		try {
			robot.delay(2000);
			BufferedImage screen = robot.createScreenCapture(new Rectangle(screenWidth, screenHeight));
			BufferedImage image = ImageIO.read(new File(bmpLm));
			for (int y = startY; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = startX; x < screen.getWidth()-image.getWidth(); x++) {
					if (match(screen, image, x, y)) {
						System.out.println("find landmark " + bmpLm + " at " + x + "," + y);
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			for (int y = 0; y < startY; y++) {
				for (int x = 0; x < startX; x++) {
					if (match(screen, image, x, y)) {
						System.out.println("find landmark " + bmpLm + " at " + x + "," + y);
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			
			return new Point(-1, -1);
		} catch (Exception e) {
			e.printStackTrace();
			return new Point(-1, -1);
		}
	}
	
	private static boolean match(BufferedImage screen, BufferedImage image, int x, int y) {
		for (int sy = y, iy = 0; iy < image.getHeight(); sy++, iy++) {
			for (int sx = x, ix = 0; ix < image.getWidth(); sx++, ix++) {
				if (screen.getRGB(sx, sy) != image.getRGB(ix, iy)) return false;
			}
		}
		return true;
	}

	public static void initPlace() {
		place = new Point[20];
		for (int i = 0; i < 20; i++) place[i] = new Point();
		shopPlace = findLandmark("e:\\farmland\\shop.bmp", 0, 0);
		place[1].x = shopPlace.x+place1offsetX;
		place[1].y = shopPlace.y+place1offsetY;
		for (int i = 2; i < 19; i++) {
			place[i].x = place[1].x+(i-1)/3*100-(i-1)%3*100;
			place[i].y = place[1].y+(i-1)/3*50+(i-1)%3*50;
		}
	}
}
