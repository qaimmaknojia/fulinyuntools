package game;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

import util.Common;


public class ParkingRobot {
	
	public static Dialog dashBoard = new Dialog((Frame)null, "");
	
	static {

	}
	
	public static void main(String[] args) {
		Common.initRobot();
		final JButton start = new JButton("start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				act0();
				Common.robot.delay(500);
				act1();
				Common.robot.delay(1000);
				act2();
//				Common.robot.delay(500);
//				act3();
//				Common.robot.delay(500);
//				act4();
//				Common.robot.delay(500);
//				act5();
//				Common.robot.delay(500);
//				act6();
//				Common.robot.delay(500);
//				act7();
				start.setText("stopped");
			}
		});
		dashBoard.setLayout(new FlowLayout());
		dashBoard.add(start);
		dashBoard.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		dashBoard.setLocation(new Point(10, 650));
		dashBoard.setSize(300, 70);
		dashBoard.setAlwaysOnTop(true);
		dashBoard.setVisible(true);

	}
	
	public static void act0() {
		Common.robot.mouseMove(400, 400);
		Common.robot.mousePress(InputEvent.BUTTON1_MASK);
		Common.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void act1() {
		Common.robot.keyPress(KeyEvent.VK_UP);
		Common.robot.delay(1100);
		Common.robot.keyPress(KeyEvent.VK_LEFT);
		Common.robot.delay(1600);
		Common.robot.keyRelease(KeyEvent.VK_LEFT);
		Common.robot.delay(500);
		Common.robot.keyRelease(KeyEvent.VK_UP);
	}
	
	public static void act2() {
		Common.robot.keyPress(KeyEvent.VK_DOWN);
		Common.robot.delay(1500);//
		Common.robot.keyPress(KeyEvent.VK_RIGHT);
		Common.robot.delay(500);//
		Common.robot.keyRelease(KeyEvent.VK_RIGHT);
		Common.robot.keyRelease(KeyEvent.VK_DOWN);
		Common.robot.keyPress(KeyEvent.VK_UP);
		Common.robot.keyPress(KeyEvent.VK_LEFT);
		Common.robot.delay(500);//
		Common.robot.keyRelease(KeyEvent.VK_LEFT);
		Common.robot.keyPress(KeyEvent.VK_RIGHT);
		Common.robot.delay(800);//
		Common.robot.keyRelease(KeyEvent.VK_RIGHT);
		Common.robot.delay(500);//
		Common.robot.keyPress(KeyEvent.VK_LEFT);
		Common.robot.delay(800);//
		Common.robot.keyRelease(KeyEvent.VK_LEFT);
		Common.robot.delay(1000);//
		Common.robot.keyRelease(KeyEvent.VK_UP);
	}
	
	public static void act3() {
		Common.robot.keyPress(KeyEvent.VK_DOWN);
		Common.robot.delay(500);//
		Common.robot.keyPress(KeyEvent.VK_LEFT);
		Common.robot.delay(500);//
		Common.robot.keyRelease(KeyEvent.VK_LEFT);
		Common.robot.delay(500);//
		Common.robot.keyPress(KeyEvent.VK_RIGHT);
		Common.robot.delay(500);
		Common.robot.keyRelease(KeyEvent.VK_RIGHT);
		Common.robot.delay(500);//
		Common.robot.keyRelease(KeyEvent.VK_DOWN);
	}
	
	public static void act4() {
		
	}
	
	public static void act5() {
		
	}

	public static void act6() {
		
	}

	public static void act7() {
		
	}

}
