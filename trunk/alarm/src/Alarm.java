import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class Alarm {

	public static Calendar alarmTime;
	
	public static void main(String[] args) throws Exception {
//		setAlarm(13, 51, 0, "mob@xiaonei");
//		setAfterAlarm(0, 55, 0, "mob@xiaonei, 33, 11");
//		setAfterAlarm(0, 40, 0, "ares");
//		everyDay(10, 0, 0, "netyi");
//		setAfterAlarm(0, 6, 0, "5ips");
//		setAfterAlarm(0, 20, 0, "TOEFL writing");
//		setAfterAlarm(0, 0, 10, "test");
		examClock(100, 3);
	}

	public static void examClock(int nMin, int lastMin) throws Exception {
		Calendar c = Calendar.getInstance();
		final JDialog d = new JDialog((JFrame)null, "Please print your name on each sheet    Good luck!");
		JLabel l = new JLabel(String.format("%5d minutes left", nMin));
		l.setFont(new Font("SansSerif", Font.PLAIN, 108));
		d.add(l);
		d.setLocation(new Point(50, 200));
		d.setSize(900, 400);
		d.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		d.setAlwaysOnTop(true);
		d.setVisible(true);
		while (nMin > lastMin) {
			Thread.currentThread().sleep(60*1000);
			nMin--;
			l.setText(String.format("%5d minutes left", nMin));
		}
		int sec = lastMin*60;
		l.setForeground(Color.RED);
		while (sec > 0) {
			Thread.currentThread().sleep(1000);
			sec--;
			l.setText(String.format("%5d:%02d", sec/60, sec%60));			
		}
		l.setText("<html><p>&nbsp;&nbsp;&nbsp;&nbsp;Please return all your exam papers</p><p>&nbsp;&nbsp;&nbsp;&nbsp;Thank you!</p></html>");
		l.setFont(new Font("SansSerif", Font.PLAIN, 48));
		
	}
	
	public static void everyDay(int h, int m, int s, String message) throws Exception {
		setTime(h, m, s);
		System.out.println(alarmTime.get(Calendar.HOUR_OF_DAY) + ":" + alarmTime.get(Calendar.MINUTE) + 
				":" + alarmTime.get(Calendar.SECOND) + "\tevery day\t" + message);
		while (true) {
			Calendar c = Calendar.getInstance();
			final Dialog d = new Dialog((Frame)null, new Date().toString());
			d.add(new Label(message));
			d.setLocation(new Point(300, 300));
			d.setSize(200, 70);
			d.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					d.setVisible(false);
				}
			});
			d.setAlwaysOnTop(true);
			if (c.compareTo(alarmTime) >= 0) {
				d.setVisible(true);
				FileInputStream notice = new FileInputStream("media/notice.wav");
				AudioStream as = new AudioStream(notice);
				AudioPlayer.player.start(as);
//				as.close();
				setTime(h, m, s);
			}
			Thread.currentThread().sleep(1000);
		}
		
	}
	
	public static void setAlarm(int h, int m, int s, String message) throws Exception {
		setTime(h, m, s);
		System.out.println(alarmTime.get(Calendar.HOUR_OF_DAY) + ":" + alarmTime.get(Calendar.MINUTE) + 
				":" + alarmTime.get(Calendar.SECOND) + "\t" + message);
		while (true) {
			Calendar c = Calendar.getInstance();
			if (c.compareTo(alarmTime) >= 0) {
				Dialog d = new Dialog((Frame)null, new Date().toString());
				d.add(new Label(message));
				d.setLocation(new Point(300, 300));
				d.setSize(200, 70);
				d.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
				d.setAlwaysOnTop(true);
				d.setVisible(true);
//				FileInputStream notice = new FileInputStream("media/notice.wav");
//				AudioStream as = new AudioStream(notice);
//				AudioPlayer.player.start(as);
//				as.close();
				break;
			}
			Thread.currentThread().sleep(1000);
		}
	}
	
	public static void setAfterAlarm(int h, int m, int s, String message) throws Exception {
		after(h, m, s);
		System.out.println(alarmTime.get(Calendar.HOUR_OF_DAY) + ":" + alarmTime.get(Calendar.MINUTE) + 
				":" + alarmTime.get(Calendar.SECOND) + "\t" + message);
		while (true) {
			Calendar c = Calendar.getInstance();
			if (c.compareTo(alarmTime) >= 0) {
				Dialog d = new Dialog((Frame)null, new Date().toString());
				d.add(new Label(message));
				d.setLocation(new Point(300, 300));
				d.setSize(200, 70);
				d.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
				d.setAlwaysOnTop(true);
				d.setVisible(true);
//				FileInputStream notice = new FileInputStream("media/notice.wav");
//				AudioStream as = new AudioStream(notice);
//				AudioPlayer.player.start(as);
//				as.close();
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
		if (Calendar.getInstance().compareTo(alarmTime) > 0) alarmTime.add(Calendar.HOUR_OF_DAY, 24);
	}
	
	public static void after(int hh, int mm, int ss) {
		alarmTime = Calendar.getInstance();
		alarmTime.add(Calendar.HOUR_OF_DAY, hh);
		alarmTime.add(Calendar.MINUTE, mm);
		alarmTime.add(Calendar.SECOND, ss);
	}
	
//	public static void loginXiaonei() throws Exception {
//		HttpClient client = new HttpClient();
//		PostMethod method = new PostMethod("http://login.xiaonei.com/Login.do");
////		method.setRequestHeader("Cookie", "");
//		NameValuePair[] data = { new NameValuePair("email", "fulinyun@126.com"),
//				new NameValuePair("password", "password") };
//		method.setRequestBody(data);
//		client.executeMethod(method);
//		Header[] responseHeaders = method.getResponseHeaders();
//		for (int i = 0; i < responseHeaders.length; i++) {
//			System.out.println(responseHeaders[i].getName() + " = " 
//					+ responseHeaders[i].getValue());
//		}
////		byte[] responseBody = method.getResponseBody();
////		System.out.println(new String(responseBody));
//		System.out.println("***************** body ******************");
//		System.out.println(method.getResponseBodyAsString());
//		
//	}
	
//	public static void doJob() throws Exception {
//		HttpClient client = new HttpClient();
//		PostMethod method = new PostMethod("http://mob.xiaonei.com/job.do");
////		method.setRequestHeader("Cookie", "");
//		NameValuePair[] data = { new NameValuePair("id", "5") };
//		method.setRequestBody(data);
//		client.executeMethod(method);
//		Header[] responseHeaders = method.getResponseHeaders();
//		for (int i = 0; i < responseHeaders.length; i++) {
//			System.out.println(responseHeaders[i].getName() + " = " 
//					+ responseHeaders[i].getValue());
//		}
////		byte[] responseBody = method.getResponseBody();
////		System.out.println(new String(responseBody));
//		System.out.println("***************** body ******************");
//		System.out.println(method.getResponseBodyAsString());
//		
//	}
	
//	public static void logout() throws Exception {
//		HttpClient client = new HttpClient();
//		PostMethod method = new PostMethod("http://www.xiaonei.com/Logout.do");
////		method.setRequestHeader("Cookie", "");
//		NameValuePair[] data = { };
//		method.setRequestBody(data);
//		client.executeMethod(method);
//		Header[] responseHeaders = method.getResponseHeaders();
//		for (int i = 0; i < responseHeaders.length; i++) {
//			System.out.println(responseHeaders[i].getName() + " = " 
//					+ responseHeaders[i].getValue());
//		}
////		byte[] responseBody = method.getResponseBody();
////		System.out.println(new String(responseBody));
//		System.out.println("***************** body ******************");
//		System.out.println(method.getResponseBodyAsString());
//		
//	}
}
