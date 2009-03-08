import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;


public class BasketballRobot {

	public static double sqrtG = 3.07;
	public static int x2 = 629, y2 = 535;	//todo
	public static Dialog calcDialog = new Dialog((Frame)null, "");
	public static int boundaryX = 135;	//todo
	
	public static double calcPower(int x1, int y1, int angle) {
		double theta = (angle+0.0)/180.0*Math.PI;
		return sqrtG*(x2-x1)/Math.sqrt(Math.sin(2.0*theta)*(x2-x1)
				+(y2-y1)*(1+Math.cos(2.0*theta)));
	}
	
	public static void main(String[] args) {
		main75();
	}
	
	public static void hardShot(int x1, int y1, int bx) {
		x1 = bx*2-x1;
		listPowersReverse(x1, y1);
	}
	
	public static void listPowers(int x1, int y1) {
		System.out.println(x1 + "," + y1);
		int startAngle = (int)(Math.atan2(y1-y2, x2-x1))+1;
		for (int angle = startAngle; angle < 90; angle++) 
			System.out.println(angle + " : " + calcPower(x1, y1, angle));
	}
	
	public static void listPowersReverse(int x1, int y1) {
		System.out.println(x1 + "," + y1);
		int startAngle = (int)(Math.atan2(y1-y2, x2-x1))+1;
		for (int angle = startAngle; angle < 90; angle++) 
			System.out.println("" + (180-angle) + " : " + calcPower(x1, y1, angle));
	}
	
	public static void main75() {
//		Scanner sc = new Scanner(System.in);
//		while (true) {
//			int x1 = sc.nextInt();
//			int y1 = sc.nextInt();
//			System.out.println(calcPower(x1, y1, 75));
//		}
		Common.initRobot();
		calcDialog.setLayout(new FlowLayout());
		JButton calc = new JButton("calc");
		final JLabel power = new JLabel("100");
		
		calc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Point p = Common.findLandmarkPartial("e:\\basketball.bmp", 160, 500, 721, 809);	//todo
				power.setText("" + (int)(calcPower(p.x, p.y, 75)+0.5));
			}
			
		});
		calcDialog.add(calc);
		calcDialog.add(power);
		
		JButton hard = new JButton("hard");
		hard.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Point p = Common.findLandmarkPartial("e:\\basketball.bmp", 160, 500, 721, 809);	//todo
				hardShot(p.x, p.y, boundaryX);
			}
		});
		calcDialog.add(hard);
		calcDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		calcDialog.setLocation(new Point(700, 600));
		calcDialog.setSize(200, 70);
		calcDialog.setAlwaysOnTop(true);
		calcDialog.setVisible(true);

	}
}
