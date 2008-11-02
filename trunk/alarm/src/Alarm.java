import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;


public class Alarm {

	public static Calendar alarmTime;
	
	public static void main(String[] args) throws Exception {
//		setTime(11, 12, 18);
		after(0, 50, 0);
		System.out.println(alarmTime.toString());
		while (true) {
			Calendar c = Calendar.getInstance();
			if (c.compareTo(alarmTime) >= 0) {
				Dialog d = new Dialog((Frame)null, new Date().toString());
				d.add(new Label(new Date().toString()));
				d.setLocation(new Point(300, 300));
				d.setSize(200, 70);
				d.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
				d.setAlwaysOnTop(true);
				d.setVisible(true);
				break;
			}
			Thread.currentThread().sleep(1000);
		}
	}
	
	public static void setTime(int hh, int mm, int ss) {
		alarmTime = Calendar.getInstance();
		alarmTime.set(Calendar.HOUR_OF_DAY, hh);
		alarmTime.set(Calendar.MINUTE, mm);
		alarmTime.set(Calendar.SECOND, ss);
	}
	
	public static void after(int hh, int mm, int ss) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, hh);
		c.add(Calendar.MINUTE, mm);
		c.add(Calendar.SECOND, ss);
	}
}
