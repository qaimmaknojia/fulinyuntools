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

	public static int place1offsetX = -390;
	public static int place1offsetY = 160;
	public static Point[] place = null;
	public static Point shopPlace = null;
	public static int numPlace = 7;
	public static String farmlandURL = "http://apps.xiaonei.com/happyfarm";
	public static String picFilePrefix = "E:\\farmland\\snapshot ";

	public static void main(String[] args) {
		mainHarvest(args);
//		mainMaintain(args);
	}
	
	public static void mainMaintain(String[] args) {
		System.out.println("maintain");
		try {
			Thread.currentThread().sleep(3*60*60*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Common.notice(new Date().toString(), 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		initPlace();
		
//		testPlacePos();
//		initDialog();

		while (true) {
			water();
			removeWeed();
			removeWorm();
//			harvest();
//			scarify();
//			sellAll();
//			buyCarrot();
//			plant();
			String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic);
			Common.exitFirefox();
			System.out.println(new Date().toString() + " maintain completed");
			try {
				Thread.currentThread().sleep(3*60*60*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void mainHarvest(String[] args) {
		System.out.println("harvest");
		try {
			Thread.currentThread().sleep(10*60*60*1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Common.notice("farmland", 300, 300);
		Common.initRobot();
		Common.enterGame(farmlandURL);
		Common.robot.delay(30000);
		initPlace();
		
//		testPlacePos();
//		initDialog();

		while (true) {
//			water();
//			removeWeed();
//			removeWorm();
			harvest();
			scarify();
			sellAll();
			buyCarrot();
			plant();
			String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
			Common.takePic(pic);
			Common.exitFirefox();
			System.out.println(new Date().toString() + " harvest completed");
			try {
				Thread.currentThread().sleep(15*60*60*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void removeWorm() {
		findAndClick("e:\\farmland\\removeWorm.bmp");
		traverseLand();
	}

	private static void removeWeed() {
		findAndClick("e:\\farmland\\removeWeed.bmp");
		traverseLand();
	}

	private static void water() {
		findAndClick("e:\\farmland\\water.bmp");
		traverseLand();
	}

	private static void plant() {
		findAndClick("e:\\farmland\\bag.bmp");
		Common.robot.delay(1000);
		findAndClick("e:\\farmland\\carrotInBag.bmp");
		traverseLand();
	}

	private static boolean findAndClick(String target) {
		Point tar = Common.findLandmark(target, 0, 0);
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

	private static void buyCarrot() {
		findAndClick("e:\\farmland\\shop.bmp");
		Common.robot.delay(1000);
		for (int i = 0; i < numPlace; i++) {
			findAndClick("e:\\farmland\\carrot.bmp");
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
		traverseLand();
	}
	
	private static void traverseLand() {
		for (int i = 1; i <= numPlace; i++) {
			Common.robot.delay(2000);
			Common.moveAndClick(place[i].x, place[i].y);
		}
	}

	private static void scarify() {
		findAndClick("e:\\farmland\\scarify.bmp");
		
		for (int i = 1; i <= numPlace; i++) {
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
		shopPlace = Common.findLandmark("e:\\farmland\\shop.bmp", 0, 0);
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
