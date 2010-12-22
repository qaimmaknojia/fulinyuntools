package util;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Security;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;


public class Common {
	

	public static Dialog noticeDialog = new Dialog((Frame)null, "");
	public static int countdown;
	public static JButton delay = new JButton(" ");

	public static Robot robot = null;
	
	public static String workingSign = "/temp/robotworking.sign";

	// default values
	public static String configFile = "config/common.prop";

	public static int startX;
	public static int startY;
	public static int firefoxX;
	public static int firefoxY;
	public static int addressX;
	public static int addressY;
	public static int screenWidth;
	public static int screenHeight;
	public static int exitX;
	public static int exitY;
	
	static {
		try {
			File conf = new File(configFile);
			System.out.println("default configuration file: " + conf.getAbsolutePath());
			Properties prop = new Properties();
			InputStream is = new FileInputStream(conf);
			prop.load(is);
			startX = Integer.parseInt(prop.getProperty("startX"));
			startY = Integer.parseInt(prop.getProperty("startY"));
			firefoxX = Integer.parseInt(prop.getProperty("firefoxX"));
			firefoxY = Integer.parseInt(prop.getProperty("firefoxY"));
			addressX = Integer.parseInt(prop.getProperty("addressX"));
			addressY = Integer.parseInt(prop.getProperty("addressY"));
			screenWidth = Integer.parseInt(prop.getProperty("screenWidth"));
			screenHeight = Integer.parseInt(prop.getProperty("screenHeight"));
			exitX = Integer.parseInt(prop.getProperty("exitX"));
			exitY = Integer.parseInt(prop.getProperty("exitY"));

			System.out.println("using working sign: " + new File(workingSign).getAbsolutePath());
			noticeDialog.setLayout(new FlowLayout());
			noticeDialog.add(new Label("drop your mouse!"));
			delay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					countdown += 60;
					delay.setText(""+countdown);
				}
			});
			noticeDialog.add(delay);
			noticeDialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					countdown += 3600;
					delay.setText(""+countdown);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		initRobot();
//		enterGame("about:blank");
//		exitFirefox();
//		robot.delay(5000);
//		Point p = findLandmarkPartial("e:\\test.bmp", 300, 300, 500, 500);
//		robot.mouseMove(p.x, p.y);
	}
	
	public static void sleepUntil(int h, int m, int s) {
		Calendar alarmTime = Calendar.getInstance();
		alarmTime.set(Calendar.HOUR_OF_DAY, h);
		alarmTime.set(Calendar.MINUTE, m);
		alarmTime.set(Calendar.SECOND, s);
		if (Calendar.getInstance().compareTo(alarmTime) > 0) alarmTime.add(Calendar.HOUR_OF_DAY, 24);
		System.out.println("sleep until " + alarmTime.getTime().toString());
		try {
			Thread.currentThread().sleep(alarmTime.getTimeInMillis()-Calendar.getInstance().getTimeInMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moveAndClick(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void notice(String title, int x, int y) {
		notice(title, x, y, 10);
	}
	
	public static void notice(String title, int x, int y, int countdown) {
		Dialog d = noticeDialog;
		d.setTitle(title);
		d.setLocation(new Point(x, y));
		d.setSize(200, 70);
		d.setAlwaysOnTop(true);
		d.setVisible(true);
		delay.setText(""+countdown);
		try {
			Common.countdown = countdown;
			while (countdown != 0) {
				Thread.currentThread().sleep(1000);
				countdown--;
				delay.setText(""+countdown);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		d.setVisible(false);
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

	public static void selectAll() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress('A');
		robot.keyRelease('A');
		robot.keyRelease(KeyEvent.VK_CONTROL);		
	}
	
	public static void copy() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress('C');
		robot.keyRelease('C');
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public static void paste() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress('V');
		robot.keyRelease('V');
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	public static void switchProgram() {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_ALT);		
	}
	
	public static void enterString(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ':') {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (str.charAt(i) == '_') {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (Character.isUpperCase(str.charAt(i))) {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(Character.toUpperCase(str.charAt(i)));
				robot.keyRelease(Character.toUpperCase(str.charAt(i)));
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else {
				robot.keyPress(Character.toUpperCase(str.charAt(i)));
				robot.keyRelease(Character.toUpperCase(str.charAt(i)));
			}
		}

	}
	
	public static void enterSite(String url) {
		enterSite(url, startX, startY, firefoxX, firefoxY, addressX, addressY);
	}
	
	public static void enterSite(String url, int startX, int startY, 
			int firefoxX, int firefoxY, int addressX, int addressY) {

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
		moveAndClick(startX, startY);

		robot.delay(5000);
		moveAndClick(firefoxX, firefoxY);
		
		robot.delay(7000);
		moveAndClick(addressX, addressY);

		robot.delay(2000);
		enterString(url);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public static void takePic(String fn) {
		takePic(fn, screenWidth, screenHeight);
	}
	
	public static void takePic(String fn, int screenWidth, int screenHeight) {

		robot.delay(2000);
		BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, screenWidth,
				screenHeight));
		try {
			ImageIO.write(image, "JPEG", new File(fn));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exitFirefox() {
		exitFirefox(exitX, exitY);
	}
	
	public static void exitFirefox(int exitX, int exitY) {

		robot.delay(5000);
		moveAndClick(exitX, exitY);
		new File(workingSign).delete();
	}

	public static boolean findAndClick(String bmpLm, int sx, int sy, int screenWidth, int screenHeight) throws Exception {
		Point trgt = findLandmark(bmpLm, sx, sy, screenWidth, screenHeight);
		if (trgt.x != -1 && trgt.y != -1) {
			moveAndClick(trgt.x, trgt.y);
			return true;
		}
		return false;
	}
	
	public static Point findLandmark(String bmpLm, int sx, int sy, int screenWidth, int screenHeight) {
		try {
			robot.delay(2000);
			BufferedImage screen = robot.createScreenCapture(new Rectangle(screenWidth, screenHeight));
			BufferedImage image = ImageIO.read(new File(bmpLm));
			for (int y = sy; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = sx; x < screen.getWidth()-image.getWidth(); x++) {
					if (match(screen, image, x, y)) {
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			for (int y = 0; y < sy; y++) {
				for (int x = 0; x < screen.getWidth()-image.getWidth(); x++) {
					if (match(screen, image, x, y)) {
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			for (int y = sy; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = 0; x < sx; x++) {
					if (match(screen, image, x, y)) {
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

	public static Point findLandmark(String bmpLm, int sx, int sy, boolean shouldFind) {
		return findLandmark(bmpLm, sx, sy, shouldFind, screenWidth, screenHeight);
	}
	
	public static Point findLandmark(String bmpLm, int sx, int sy, boolean shouldFind, 
			int screenWidth, int screenHeight) {
		try {
			robot.delay(2000);
			BufferedImage screen = robot.createScreenCapture(new Rectangle(screenWidth, screenHeight));
			BufferedImage image = ImageIO.read(new File(bmpLm));
			for (int y = sy; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = sx; x < screen.getWidth()-image.getWidth(); x++) {
					if (match(screen, image, x, y)) {
						if (!shouldFind) {
							System.out.println(new Date().toString() + " find landmark " + bmpLm + " at " + x + "," + y);
							takePicHQ("e:\\unexpected\\"+new Date().toString().replaceAll(":", "_")+".bmp");
						}
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			for (int y = 0; y < sy; y++) {
				for (int x = 0; x < screen.getWidth()-image.getWidth(); x++) {
					if (match(screen, image, x, y)) {
						if (!shouldFind) {
							System.out.println(new Date().toString() + " find landmark " + bmpLm + " at " + x + "," + y);
							takePicHQ("e:\\unexpected\\"+new Date().toString().replaceAll(":", "_")+".bmp");
						}
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			for (int y = sy; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = 0; x < sx; x++) {
					if (match(screen, image, x, y)) {
						if (!shouldFind) {
							System.out.println(new Date().toString() + " find landmark " + bmpLm + " at " + x + "," + y);
							takePicHQ("e:\\unexpected\\"+new Date().toString().replaceAll(":", "_")+".bmp");
						}
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			if (shouldFind) {
				System.out.println(new Date().toString() + " fail to find " + bmpLm);
				takePicHQ("e:\\unexpected\\"+new Date().toString().replaceAll(":", "_")+".bmp");
			}
			return new Point(-1, -1);
		} catch (Exception e) {
			System.out.println(new Date().toString() + " fail to find " + bmpLm);
			takePicHQ("e:\\unexpected\\"+new Date().toString().replaceAll(":", "_")+".bmp");
			e.printStackTrace();
			return new Point(-1, -1);
		}
	}

	private static void takePicHQ(String fn) {
		
		robot.delay(2000);
		BufferedImage image = robot.createScreenCapture(new Rectangle(166, 222, 945-166, 845-222));
		try {
			ImageIO.write(image, "BMP", new File(fn));
		} catch (Exception e) {
			e.printStackTrace();
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

	public static Point findLandmarkFuzzy(String bmpLm, int x1, int y1, int x2, int y2) {
		try {
			robot.delay(2000);
			BufferedImage screen = robot.createScreenCapture(new Rectangle(x1, y1, x2-x1, y2-y1));
			BufferedImage image = ImageIO.read(new File(bmpLm));
			double minDiff = image.getWidth()*image.getHeight()*255.0*255.0*3;
			int minX = 0, minY = 0;
			for (int y = 0; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = 0; x < screen.getWidth()-image.getWidth(); x++) {
					double diff = matchFuzzy(screen, image, x, y);
					if (diff < minDiff) {
						minDiff = diff;
						minX = x;
						minY = y;
					}
				}
			}
			return new Point(x1+minX+image.getWidth()/2, y1+minY+image.getHeight()/2);
		} catch (Exception e) {
			e.printStackTrace();
			return new Point(-1, -1);
		}
	}
	
	private static double matchFuzzy(BufferedImage screen, BufferedImage image, int sx, int sy) {
		double ret = 0;
		for (int x = sx, ix = 0; ix < image.getWidth(); x++, ix++) for (int y = sy, iy = 0; iy < image.getHeight(); y++, iy++) {
			Color sc = new Color(screen.getRGB(x, y));
			int r = sc.getRed();
			int g = sc.getGreen();
			int b = sc.getBlue();
			Color ic = new Color(image.getRGB(ix, iy));
			int ir = ic.getRed();
			int ig = ic.getGreen();
			int ib = ic.getBlue();
			ret += (r-ir)*(r-ir)+(g-ig)*(g-ig)+(b-ib)*(b-ib);
		}
		return ret;
	}

	public static Point findLandmarkPartial(String bmpLm, int x1, int y1, int x2, int y2) {
		try {
			robot.delay(2000);
			BufferedImage screen = robot.createScreenCapture(new Rectangle(x1, y1, x2-x1, y2-y1));
			BufferedImage image = ImageIO.read(new File(bmpLm));
			int maxMatch = 0;
			int maxX = 0, maxY = 0;
			for (int y = 0; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = 0; x < screen.getWidth()-image.getWidth(); x++) {
					int match = matchPartial(screen, image, x, y);
					if (match > maxMatch) {
						maxMatch = match;
						maxX = x;
						maxY = y;
					}
				}
			}
			return new Point(x1+maxX+image.getWidth()/2, y1+maxY+image.getHeight()/2);
		} catch (Exception e) {
			e.printStackTrace();
			return new Point(-1, -1);
		}
	}
	
	private static int matchPartial(BufferedImage screen, BufferedImage image, int sx, int sy) {
		int ret = 0;
		for (int x = sx, ix = 0; ix < image.getWidth(); x++, ix++) for (int y = sy, iy = 0; iy < image.getHeight(); y++, iy++) {
			if (screen.getRGB(x, y) == image.getRGB(ix, iy)) ret++;
		}
		return ret;
	}
	
	public static void sendMail(String type, String msgText, String attach) {
		try {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			Properties props = System.getProperties();
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.auth", "true");
			final String username = "mobrobot";
			final String password = "qwertyui";
			Session session = Session.getDefaultInstance(props,	new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					});
	
			// -- Create a new message --
			Message msg = new MimeMessage(session);
	
			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(username + "@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
					"fulinyun@126.com", false));
			msg.setSubject("mob robot report " + type);
			
			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(msgText);
	
			// create the second message part
			MimeBodyPart mbp2 = new MimeBodyPart();
	
			// attach the file to the message
			mbp2.attachFile(attach);
	
			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
	
			// add the Multipart to the message
			msg.setContent(mp);
	
			msg.setSentDate(new Date());
			
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void waitForLandmark(String landmark, int sx, int sy) {
		waitForLandmark(landmark, sx, sy, screenWidth, screenHeight);
	}
	
	public static void waitForLandmark(String landmark, int sx, int sy, int screenWidth, int screenHeight) {

		Point p = findLandmark(landmark, sx, sy, true, screenWidth, screenHeight);
		int retry = 0;
		while (p.x == -1 && p.y == -1) {
			retry++;
			if (retry == 10) break;
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
			robot.delay(30000);
			p = findLandmark(landmark, sx, sy, true, screenWidth, screenHeight);
		}
	}

	public static void copyString(int x, int beginY, int endY) {
		robot.delay(10000);
		robot.mouseMove(x, beginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(x, endY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public static String getClipBoardString() {
		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = "";
		try {
			str = (String) content.getTransferData(DataFlavor.stringFlavor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(str);
		return str;
	}

	public static void sleep(long i) {
		try {
			System.out.println("sleep until " + new Date(new Date().getTime()+i).toString());
			Thread.currentThread().sleep(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
