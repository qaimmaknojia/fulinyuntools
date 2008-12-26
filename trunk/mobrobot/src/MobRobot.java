import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MobRobot {

	public static int startX = 30;
	public static int startY = 1006;
	public static int firefoxX = 78;
	public static int firefoxY = 294;
	public static int addressX = 765;
	public static int addressY = 74;
	public static String mobURL = "http://mob.xiaonei.com/home.do";
	public static int taskX = 251;
	public static int taskY = 313;
	public static int doX = 878;
	public static int doY = 786;
	public static int exitX = 1267;
	public static int exitY = 11;

	public static void main(String[] args) throws Exception {
		doTask();
		while(true) {
			doTask();
			Thread.currentThread().sleep(50*60*1000);
		}
	}
	
	public static void doTask() throws Exception {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		Robot robot = new Robot(screen);

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
		
		robot.delay(5000);
		robot.mouseMove(taskX, taskY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.delay(5000);
		robot.mouseMove(doX, doY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.delay(2000);
		BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, 1280, 1024));
		ImageIO.write(image, "JPEG", new File("E:\\mobtemp\\snapshot" + new Date().toString().replaceAll(":", "_") + ".jpg"));
		
		robot.delay(5000);
		robot.mouseMove(exitX, exitY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		System.out.println(new Date().toString() + " task finished");

	}
}

class ImageFrame extends JFrame {
	/**
	   @param image the image to display
	 */
	public ImageFrame(Image image) {
		setTitle("Capture");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JLabel label = new JLabel(new ImageIcon(image));
		add(label);
	}

	public static final int DEFAULT_WIDTH = 450;
	public static final int DEFAULT_HEIGHT = 350;
}
