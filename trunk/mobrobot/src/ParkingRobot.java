import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;


public class ParkingRobot {
	
	public static Dialog dashBoard = new Dialog((Frame)null, "");
	
	static {
		JButton start = new JButton("start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				act1();
				Common.robot.delay(500);
			}
		});
	}
	
	public static void act1() {
		Common.robot.mouseMove(400, 400);//
		Common.robot.keyPress(KeyEvent.VK_UP);
		Common.robot.delay(500);//
		Common.robot.keyPress(KeyEvent.VK_LEFT);
		Common.robot.delay(500);//
		Common.robot.keyRelease(KeyEvent.VK_LEFT);
		Common.robot.delay(500);//
		Common.robot.keyRelease(KeyEvent.VK_UP);
	}
}
