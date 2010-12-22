package migrate;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

import util.Common;

public class MigrateRobot {
	
	public static String configFile = "config/migrate.prop";

	public static int startX;
	public static int startY;
	public static int firefoxX;
	public static int firefoxY;
	public static int addressX;
	public static int addressY;
	public static String url;
	public static int screenWidth;
	public static int screenHeight;
	public static int exitX;
	public static int exitY;
	
	public static int editX;
	public static int editY;
	public static int contentX;
	public static int contentY;
	
	public static int createX;
	public static int createY;
	public static int pageX;
	public static int pageY;
	public static int titleX;
	public static int titleY;
	public static int bodyX;
	public static int bodyY;
	public static int scrollX;
	public static int scrollY;
	public static int pathX;
	public static int pathY;
	public static int inputPathX;
	public static int inputPathY;
	public static int saveX;
	public static int saveY;
	
	public static String drupalUrlPrefix = "http://tw.rpi.edu/web/inside/allhands/";
	public static String wikiUrlPrefix = "http://tw.rpi.edu/wiki/tw:Tw_meeting_20";
	public static String wikiUrlRelativePrefix = "/wiki/tw:Tw_meeting_20";
	public static String wikiUrlSource = "http://tw.rpi.edu/wiki/tw:Lab_Meetings";
	public static String wikiUrlSourceFile = "C:\\temp\\twLabMeetings.htm";
	public static String drupalUrl = "http://tw.rpi.edu/web/";
	public static String pathBmp = "migrate/path.bmp";
		
	static {
		try{
			File conf = new File(configFile);
			System.out.println("using configuration file: " + conf.getAbsolutePath());
			Properties prop = new Properties();
			InputStream is = new FileInputStream(conf);
			prop.load(is);
			startX = Integer.parseInt(prop.getProperty("startX"));
			startY = Integer.parseInt(prop.getProperty("startY"));
			firefoxX = Integer.parseInt(prop.getProperty("firefoxX"));
			firefoxY = Integer.parseInt(prop.getProperty("firefoxY"));
			addressX = Integer.parseInt(prop.getProperty("addressX"));
			addressY = Integer.parseInt(prop.getProperty("addressY"));
			url = prop.getProperty("url");
			screenWidth = Integer.parseInt(prop.getProperty("screenWidth"));
			screenHeight = Integer.parseInt(prop.getProperty("screenHeight"));
			exitX = Integer.parseInt(prop.getProperty("exitX"));
			exitY = Integer.parseInt(prop.getProperty("exitY"));
			
			editX = Integer.parseInt(prop.getProperty("editX"));
			editY = Integer.parseInt(prop.getProperty("editY"));
			contentX = Integer.parseInt(prop.getProperty("contentX"));
			contentY = Integer.parseInt(prop.getProperty("contentY"));
			
			createX = Integer.parseInt(prop.getProperty("createX"));
			createY = Integer.parseInt(prop.getProperty("createY"));
			pageX = Integer.parseInt(prop.getProperty("pageX"));
			pageY = Integer.parseInt(prop.getProperty("pageY"));
			titleX = Integer.parseInt(prop.getProperty("titleX"));
			titleY = Integer.parseInt(prop.getProperty("titleY"));
			bodyX = Integer.parseInt(prop.getProperty("bodyX"));
			bodyY = Integer.parseInt(prop.getProperty("bodyY"));
			scrollX = Integer.parseInt(prop.getProperty("scrollX"));
			scrollY = Integer.parseInt(prop.getProperty("scrollY"));
			pathX = Integer.parseInt(prop.getProperty("pathX"));
			pathY = Integer.parseInt(prop.getProperty("pathY"));
			inputPathX = Integer.parseInt(prop.getProperty("inputPathX"));
			inputPathY = Integer.parseInt(prop.getProperty("inputPathY"));
			saveX = Integer.parseInt(prop.getProperty("saveX"));
			saveY = Integer.parseInt(prop.getProperty("saveY"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void generateTaskFile(String fileName, String output, 
			Extractor ex, Translator tr) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(output)));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
//			if (line.contains(wikiUrlRelativePrefix)) 
//				System.out.println(line);
			pw.print(tr.translate(ex.extract(line)));
		}
		pw.close();
		br.close();
	}
	
	// generate one "src dst" pair from a source url (relative) in wiki
	public static String translate(String relativeUrl, Translator t) {
		return t.translate(relativeUrl);
	}
	
	// copy the source code of a wiki page to a drupal page
	public static void migrate(String year, String month, String date) throws Exception {
		String src = wikiUrlPrefix + year + "-" + month + "-" + date;
		Common.enterSite(src, startX, startY, 
				firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(10000);
		Common.moveAndClick(editX, editY);
		Common.robot.delay(10000);
		Common.moveAndClick(contentX, contentY);
		Common.robot.delay(2000);
		Common.selectAll();
		Common.robot.delay(2000);
		Common.copy();
		Common.robot.delay(2000);
		Common.exitFirefox(exitX, exitY);
		
		Common.enterSite(drupalUrl, startX, startY, 
				firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(10000);
		Common.robot.mouseMove(createX, createY);
		Common.robot.delay(2000);
		Common.moveAndClick(pageX, pageY);
		Common.robot.delay(5000);
		Common.moveAndClick(titleX, titleY);
		Common.robot.delay(2000);
		Common.enterString(getTitle(year, month, date));
		Common.robot.delay(2000);
		Common.moveAndClick(bodyX, bodyY);
		Common.robot.delay(2000);
		Common.paste();
		Common.robot.delay(2000);
		Common.moveAndClick(scrollX, scrollY);
		Common.robot.delay(2000);
		Point path = Common.findLandmark(pathBmp, pathX-100, pathY-100, screenWidth, screenHeight);
		System.out.println(path.x + "," + path.y);
		if (path.x == -1 || path.y == -1) {
			System.out.println("fail to find landmark " + pathBmp);
			System.exit(1);
		}
		Common.moveAndClick(path.x, path.y);
		Common.robot.delay(2000);
		Common.moveAndClick(path.x+inputPathX-pathX, path.y+inputPathY-pathY);
		Common.robot.delay(2000);
		Common.enterString(getPath(year, month, date));
		Common.robot.delay(2000);
		Common.moveAndClick(path.x+saveX-pathX, path.y+saveY-pathY);
		Common.robot.delay(2000);
		Common.exitFirefox(exitX, exitY);

		System.out.println(drupalUrlPrefix + year + month + date);
	}
	
	private static String getPath(String year, String month, String date) {
		return "inside/allhands/"+year+month+date;
	}

	private static String getTitle(String year, String month, String date) {
		String[] monthName = {"", "January", "February", "March", "April", "May", 
				"June", "July", "August", "September", "October", "November", 
				"December"};
		return "TWC All Hands Meeting " + monthName[Integer.parseInt(month)] + 
			" " + Integer.parseInt(date) + ", 20" + year;
	}

	public static void migrate(String taskFile) throws Exception {
		Common.notice("migrate", 300, 300, 10);
		Common.initRobot();

		BufferedReader br = new BufferedReader(new FileReader(taskFile));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] parts = line.split(" ");
			migrate(parts[0], parts[1], parts[2]);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
//		Common.sleepUntil(17, 33, 0);
		migrate();
		
	}
	
	public static void migrate() throws Exception {
//		generateTaskFile(wikiUrlSourceFile, "task.mig", new Extractor() {
//			public String extract(String line) {
//				if (!line.contains(wikiUrlRelativePrefix))
//					return null;
//				String t = line.split(wikiUrlRelativePrefix)[1];
//				return t.substring(0, t.indexOf('\"'));
//			}
//		}, new Translator() {
//			public String translate(String suffix) {
//				if (suffix == null)
//					return "";
//				String[] yymmdd = suffix.split("-");
//				return yymmdd[0] + " " + yymmdd[1] + " " + yymmdd[2] + "\n";
//			}
//		});
//		System.out.println("task.mig generated");
		migrate("task.mig");
	}
	
	public static void test() {
		Common.notice("migrate", 300, 300, 10);
		Common.initRobot();
		Common.enterSite(url, startX, startY, 
				firefoxX, firefoxY, addressX, addressY);
		Common.robot.delay(10000);
		Common.robot.mouseMove(createX, createY);
		Common.robot.delay(2000);
		Common.moveAndClick(pageX, pageY);
		Common.robot.delay(2000);
		Common.moveAndClick(titleX, titleY);
		Common.robot.delay(2000);
		Common.moveAndClick(bodyX, bodyY);
		Common.robot.delay(2000);
		Common.switchProgram();
		Common.robot.delay(2000);
		Common.selectAll();
		Common.robot.delay(2000);
		Common.copy();
		Common.robot.delay(2000);
		Common.switchProgram();
		Common.robot.delay(2000);
		Common.paste();
		Common.robot.delay(2000);
		Common.moveAndClick(scrollX, scrollY);
		Common.robot.delay(2000);
		Common.moveAndClick(pathX, pathY);
		Common.robot.delay(2000);
		Common.moveAndClick(inputPathX, inputPathY);
		Common.robot.delay(2000);
		Common.robot.mouseMove(saveX, saveY);
		Common.robot.delay(2000);
		
		Common.exitFirefox(exitX, exitY);

	}
}

interface Translator {
	public String translate(String src);
}

interface Extractor {
	public String extract(String line);
}
