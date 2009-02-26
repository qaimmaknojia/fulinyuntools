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
import java.io.InputStream;
import java.security.Security;
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
	
	public static String configFile = "/configSchool.prop";

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

	public static Dialog noticeDialog = new Dialog((Frame)null, "");
	public static int countdown = 5;
	public static JButton delay = new JButton(""+countdown);

	public static Robot robot = null;
	
	public static String workingSign = "E:\\mobtemp\\robotworking";

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
					countdown += 3600;
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
			exitX = Integer.parseInt(prop.getProperty("exitX"));
			exitY = Integer.parseInt(prop.getProperty("exitY"));
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
		Dialog d = noticeDialog;
		d.setTitle(title);
		d.setLocation(new Point(x, y));
		d.setSize(200, 70);
		d.setAlwaysOnTop(true);
		d.setVisible(true);
		try {
			countdown = 5;
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

	public static void enterGame(String url) {

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

		robot.delay(2000);
		moveAndClick(firefoxX, firefoxY);
		
		robot.delay(5000);
		moveAndClick(addressX, addressY);

		robot.delay(2000);
		for (int i = 0; i < url.length(); i++) {
			if (url.charAt(i) == ':') {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else if (url.charAt(i) == '_') {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_SHIFT);
			} else {
				robot.keyPress(Character.toUpperCase(url.charAt(i)));
				robot.keyRelease(Character.toUpperCase(url.charAt(i)));
			}
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public static void takePic(String fn) {

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

		robot.delay(5000);
		moveAndClick(exitX, exitY);
		new File(workingSign).delete();
	}

	public static Point findLandmark(String bmpLm, int sx, int sy) {
		try {
			robot.delay(2000);
			BufferedImage screen = robot.createScreenCapture(new Rectangle(screenWidth, screenHeight));
			BufferedImage image = ImageIO.read(new File(bmpLm));
			for (int y = sy; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = sx; x < screen.getWidth()-image.getWidth(); x++) {
					if (match(screen, image, x, y)) {
						System.out.println("find landmark " + bmpLm + " at " + x + "," + y);
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			for (int y = 0; y < sy; y++) {
				for (int x = 0; x < screen.getWidth()-image.getWidth(); x++) {
					if (match(screen, image, x, y)) {
						System.out.println("find landmark " + bmpLm + " at " + x + "," + y);
						return new Point(x+image.getWidth()/2, y+image.getHeight()/2); 
					}
				}
			}
			for (int y = sy; y < screen.getHeight()-image.getHeight(); y++) {
				for (int x = 0; x < sx; x++) {
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

		Point p = findLandmark(landmark, sx, sy);
		int retry = 0;
		while (p.x == -1 && p.y == -1) {
			robot.delay(5000);
			p = findLandmark(landmark, sx, sy);
			retry++;
			if (retry == 10) break;
		}
		
		if (retry == 10) {
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
			robot.delay(30000);
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
}
