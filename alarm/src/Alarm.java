import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;


public class Alarm {

	public static int h, m, s;
	
	public static void main(String[] args) throws Exception {
		setTime(11, 12, 18);
		while (true) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
//			System.out.println(c.get(Calendar.HOUR_OF_DAY) + ", " + c.get(Calendar.MINUTE) + 
//					", " + c.get(Calendar.SECOND));
			if (c.get(Calendar.HOUR_OF_DAY) >= h && c.get(Calendar.MINUTE) >= m && c.get(Calendar.SECOND) >= s) {
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
		h = hh;
		m = mm;
		s = ss;
	}
}
