import java.awt.Dialog;
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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import sun.misc.BASE64Encoder;

public class MobRobot {

//	public static String configFile = "/configHome.prop";
	public static String configFile = "/configSchool.prop";

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
	
	public static Dialog noticeDialog = new Dialog((Frame)null, "");
	
	public static String mobURL = "http://mob.xiaonei.com/home.do";
	public static Robot robot = null;
	public static String picFilePrefix = "E:\\mobtemp\\snapshot";
	public static String workingSign = "E:\\mobtemp\\robotworking";
	
	static {
		try {
			
			noticeDialog.add(new Label("task begins!"));
			
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

//	public static void main(String[] args) throws Exception {
//		sendMail("smtp.sjtu.edu.cn", "java mail test", null);
//	}
	
	public static void main(String[] args) throws Exception {
		try {
			initRobot();
//			mainFight(args);
			Thread.currentThread().sleep(30*5*60*1000);
			mainVeryRich(args);
		} catch (Exception e) {
			new File(workingSign).delete();
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void mainVeryRich(String[] args) throws Exception {
		
		while (true) {
			long timeConsumption = mainDoVeryRichTask();
			Thread.currentThread().sleep(10 * 5 * 60 * 1000 - timeConsumption);
			
			long timeConsumption1 = mainPrepare();
			Thread.currentThread().sleep(40 * 5 * 60 * 1000 - timeConsumption1);
			
		}
	}
	
	private static void sendMail(String msgText, String attach) throws Exception {
		
		String to = "fulinyunxp@sjtu.edu.cn";
		String from = "fulinyunxp@sjtu.edu.cn";
		String host = "smtp.sjtu.edu.cn";
		String filename = attach;
		boolean debug = false;
		String msgText1 = msgText+"\n";
		String subject = "mob robot report";

		// create some properties and get the default Session
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);

		Session session = Session.getInstance(props, null);
		session.setDebug(debug);

		try {
			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);

			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(msgText1);

			// create the second message part
			MimeBodyPart mbp2 = new MimeBodyPart();

			// attach the file to the message
			mbp2.attachFile(filename);

			/*
			 * Use the following approach instead of the above line if
			 * you want to control the MIME type of the attached file.
			 * Normally you should never need to do this.
			 *
			   FileDataSource fds = new FileDataSource(filename) {
			public String getContentType() {
			    return "application/octet-stream";
			}
			};
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());
			 */

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);

			// add the Multipart to the message
			msg.setContent(mp);

			// set the Date: header
			msg.setSentDate(new Date());

			/*
			 * If you want to control the Content-Transfer-Encoding
			 * of the attached file, do the following.  Normally you
			 * should never need to do this.
			 *
			msg.saveChanges();
			mbp2.setHeader("Content-Transfer-Encoding", "base64");
			 */

			// send the message
			Transport.send(msg);

		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
//	private static void receive(Scanner in) throws Exception {
//		if (in.hasNextLine()) {
//			String line = in.nextLine();
//			System.out.println(line);
//		}
//	}

//	public static void send(PrintWriter out, String s) throws Exception {
//		System.out.println(s);
//		out.print(s.replaceAll("\n", "\r\n"));
//		out.print("\r\n");
//		out.flush();
//	}

	private static long mainPrepare() throws Exception {

		while (new File(workingSign).exists()) {
			System.out.println(new Date().toString() + " another robot working, waiting for 40 seconds");
			Thread.currentThread().sleep(40*1000);
		}
		new File(workingSign).createNewFile();
		
		Date start = new Date();
		long startms = start.getTime();
		System.out.println(start.toString() + " prepare begins");
		notice();
		prepare();
		Date end = new Date();
		System.out.println(end.toString() + " prepare finished");
		long timeConsumption = end.getTime()-startms;
		
		new File(workingSign).delete();

		return timeConsumption;
	}
	
	private static long mainDoVeryRichTask() throws Exception {

		while (new File(workingSign).exists()) {
			System.out.println(new Date().toString() + " another robot working, waiting for 40 seconds");
			Thread.currentThread().sleep(40*1000);
		}
		new File(workingSign).createNewFile();
		
		Date start = new Date();
		long startms = start.getTime();
		System.out.println(start.toString() + " task begins");
		notice();
		doVeryRichTask();
		Date end = new Date();
		System.out.println(end.toString() + " task finished");
		long timeConsumption = end.getTime()-startms;
		
		new File(workingSign).delete();

		return timeConsumption;
		
	}
	
	public static void mainRich(String[] args) throws Exception {

		initRobot();
		Thread.currentThread().sleep(25*5*60*1000);
		while (true) {
			while (new File(workingSign).exists()) {
				System.out.println(new Date().toString() + " another robot working, waiting for 40 seconds");
				Thread.currentThread().sleep(40*1000);
			}
			new File(workingSign).createNewFile();
			
			Date start = new Date();
			long startms = start.getTime();
			System.out.println(start.toString() + " task begins");
			notice();
			doRichTask();
			Date end = new Date();
			System.out.println(end.toString() + " task finished");
			long timeConsumption = end.getTime()-startms;
			
			new File(workingSign).delete();

			Thread.currentThread().sleep(30 * 5 * 60 * 1000 - timeConsumption);
			
		}
	}

	public static void mainFight(String[] args) throws Exception {

		while (true) {
			while (new File(workingSign).exists()) {
				System.out.println(new Date().toString() + " another robot working, waiting for 40 seconds");
				Thread.currentThread().sleep(40*1000);
			}
			new File(workingSign).createNewFile();
			
			Date start = new Date();
			long startms = start.getTime();
			System.out.println(start.toString() + " task begins");
			notice();

			enterMob();
			
			int health = getHealth();
			System.out.println("health: " + health);
			if (health < 90) {
				exitFirefox();
				new File(workingSign).delete();
				System.out.println("waiting for " + ((145-health)*3) + " minutes");
				Thread.currentThread().sleep((145-health) * 180 * 1000);
				continue;
			}

			robot.delay(5000);
			robot.mouseMove(fightX, fightY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			
			robot.delay(15000);
			robot.mouseMove(doFightX, doFightY);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);

			robot.delay(5000);
			String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
			takePic(pic);
			sendMail(new Date().toString(), pic);
			
			int hurt = getHurt();
			System.out.println("hurt: " + hurt);
			exitFirefox();
			
			new File(workingSign).delete();
			
			Date end = new Date();
			long timeConsumption = end.getTime()-startms;
			System.out.println(end.toString() + " task finished");
			Thread.currentThread().sleep(hurt * 3 * 60 * 1000 - timeConsumption);
		}
	}
	
	private static int getHurt() throws Exception {
		
		robot.delay(10000);
		robot.mouseMove(hurtBeginX, hurtBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(hurtEndX, hurtBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		int numStart;
		for (numStart = 0; numStart < str.length(); numStart++) 
			if (Character.isDigit(str.charAt(numStart))) break;
		if (numStart == str.length()) return 100;
		int numEnd;
		for (numEnd = numStart+1; numEnd < str.length(); numEnd++)
			if (!Character.isDigit(str.charAt(numEnd))) break;
		return Integer.parseInt(str.substring(numStart, numEnd));

	}

	private static void prepare() throws Exception {

		enterMob();
		
		robot.delay(5000);
		robot.mouseMove(taskX, taskY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(scrollX, scrollY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(prepareX, prepareY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(scrollupX, scrollupY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		takePic(pic);
		sendMail(new Date().toString(), pic);

		exitFirefox();
		
	}

	private static void doVeryRichTask() throws Exception {
		
		enterMob();
		
		robot.delay(5000);
		robot.mouseMove(taskX, taskY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(scrollX, scrollY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(veryRichTaskX, veryRichTaskY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.delay(5000);
		robot.mouseMove(scrollupX, scrollupY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		takePic(pic);
		sendMail(new Date().toString(), pic);
		
		exitFirefox();
		
	}

	private static void doRichTask() throws Exception {
		
		enterMob();
		
		robot.delay(5000);
		robot.mouseMove(taskX, taskY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(scrollX, scrollY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.mouseMove(richTaskX, richTaskY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.delay(5000);
		robot.mouseMove(scrollupX, scrollupY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		takePic(pic);
		sendMail(new Date().toString(), pic);

		exitFirefox();
		
	}

	private static void notice() throws Exception {

		Dialog d = noticeDialog;
		d.setTitle(new Date().toString());
//		d.add(new Label("task begins!"));
		d.setLocation(new Point(300, 300));
		d.setSize(200, 70);
		d.setAlwaysOnTop(true);
		d.setVisible(true);
//		FileInputStream notice = new FileInputStream("media/notice.wav");
//		AudioStream as = new AudioStream(notice);
//		AudioPlayer.player.start(as);
		Thread.currentThread().sleep(5000);
		d.setVisible(false);
		
	}

	private static int getHealth() throws Exception {
		
		robot.delay(10000);
		robot.mouseMove(healthBeginX, healthBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(healthEndX, healthBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(5000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
//		for (int i = 0; i < str.length(); i++)
//			System.out.println(str.charAt(i));
		int numStart;
		for (numStart = 0; numStart < str.length(); numStart++) 
			if (Character.isDigit(str.charAt(numStart))) break;
		if (numStart == str.length()) return 15;
		return Integer.parseInt(str.substring(numStart, str.indexOf("/")));
		
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
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++)
			System.out.println(str.charAt(i));

		exitFirefox();

		return Integer.parseInt(str.substring(3));
		
	}

	private static void adjustEquip() throws Exception {

		enterMob();
		enterStore();
		int moneyAmount = getMoneyAmount();
		int carNumber = getCarNumber();
		System.out.println("money amount: " + moneyAmount + "; car number: " + carNumber);
		while (moneyAmount >= 1150000 && carNumber > 10) {
			sellCar();
			buyCar1();
			moneyAmount -= 1150000;
			carNumber--;
		}
		exitFirefox();
		
	}

	private static void sellCar() throws Exception {

		for (int i = 0; i < 3; i++) {
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

		for (int i = 0; i < 3; i++) {
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
		System.out.println("money amount: " + moneyAmount + "; gun number: " + gunNumber
				+ "; helmet number: " + helmetNumber + "; car1 number: " + car1Number);
		exitFirefox();
		while (moneyAmount >= 1500000 + 16000 + 200000 && gunNumber < brotherNumber + 1) {
			enterMob();
			enterStore();
			buyGun();
			buyHelmet();
			buyCar1();
			gunNumber++;
			helmetNumber++;
			car1Number++;
			moneyAmount -= 1716000;
			System.out.println("money amount: " + moneyAmount + "; gun number: " + gunNumber
					+ "; helmet number: " + helmetNumber + "; car1 number: " + car1Number);
			exitFirefox();
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
/*
		robot.delay(5000);
		robot.mouseMove(2, 2);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_R);
		robot.keyRelease(KeyEvent.VK_R);

		robot.delay(5000);
		robot.mouseMove(5, 5);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_X);
		robot.keyRelease(KeyEvent.VK_X);
*/		
		robot.delay(5000);
		robot.mouseMove(addressX, addressY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(2000);
		for (int i = 0; i < mobURL.length(); i++) {
			if (mobURL.charAt(i) == ':') {
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
		
		String pic = picFilePrefix + new Date().toString().replaceAll(":", "_")+".jpg";
		takePic(pic);
		sendMail(new Date().toString(), pic);

		exitFirefox();
		
	}

	public static void takePic(String fn) throws Exception {

		robot.delay(2000);
		BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, screenWidth,
				screenHeight));
		ImageIO.write(image, "JPEG", new File(fn));
		
	}

	public static void exitFirefox() throws Exception {

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
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++)
			System.out.println(str.charAt(i));
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
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++)
			System.out.println(str.charAt(i));
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
		} else {
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

		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++)
			System.out.println(str.charAt(i));
		return Integer.parseInt(str);
		
	}

	public static int getHelmetNumber() throws Exception {

		for (int i = 0; i < 3; i++) {
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

		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
//		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str);
		
	}

//	public static int getCarNumber() throws Exception {
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

	public static int getCarNumber() throws Exception {

		if (configFile.equals("/configHome.prop")) {
			for (int i = 0; i < 3; i++) {
				robot.delay(5000);
				robot.mouseMove(scrollX, scrollY);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		} else {
			for (int i = 0; i < 2; i++) {
				robot.delay(5000);
				robot.mouseMove(scrollX, scrollY);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		}

		robot.delay(5000);
		robot.mouseMove(carBeginX, carBeginY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(carEndX, carBeginY);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
//		for (int i = 0; i < str.length(); i++) System.out.println(str.charAt(i));
		return Integer.parseInt(str);
	}

	public static int getCar1Number() throws Exception {

		for (int i = 0; i < 3; i++) {
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

		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		Clipboard cb = new Frame().getToolkit().getSystemClipboard();
		Transferable content = cb.getContents(null);
		String str = (String) content.getTransferData(DataFlavor.stringFlavor);
		System.out.println(str);
		for (int i = 0; i < str.length(); i++)
			System.out.println(str.charAt(i));
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
		} else {
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
		
		for (int i = 0; i < 3; i++) {
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

//	public static void buyCar() throws Exception {
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
