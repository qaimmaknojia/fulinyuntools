import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;


public class FarmlandRobot {

	public static Dialog noticeDialog = new Dialog((Frame)null, "");
	public static Robot robot = null;
	public static int screenWidth = 1024;
	public static int screenHeight = 768;
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static int place1offsetX = -390;
	public static int place1offsetY = 160;
	public static Point[] place = null;
	
	public static void main(String[] args) {
		initRobot();
		initPlace();
//		initDialog();
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
			robot.delay(500);
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
		Point shopPos = findLandmark("e:\\farmland\\shop.bmp", 0, 0);
		place[1].x = shopPos.x+place1offsetX;
		place[1].y = shopPos.y+place1offsetY;
		for (int i = 2; i < 19; i++) {
			place[i].x = place[1].x+(i-1)/3*100-(i-1)%3*100;
			place[i].y = place[1].y+(i-1)/3*50+(i-1)%3*50;
		}
	}
}
