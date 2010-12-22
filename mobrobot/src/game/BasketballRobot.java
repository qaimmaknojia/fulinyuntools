package game;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

import util.Common;


public class BasketballRobot {

	public static double sqrtG = 3.0;
	public static int x2 = 629, y2 = 535;	//todo
	public static Dialog calcDialog = new Dialog((Frame)null, "");
	public static int leftX = x2-629+110;	//todo
	public static int rightX = x2-629+745;	//todo
	public static double pixPerPower = 2.45;	//todo
	
	public static void main(String[] args) {
//		extractImageCenter("e:\\basketballBig.bmp", "e:\\basketball.bmp");
		main75();
	}
	
	public static void extractImageCenter(String input, String output) {
		try {
			BufferedImage bi = ImageIO.read(new File(input));
			BufferedImage bio = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
			for (int i = bi.getWidth()/2-1, j = 0; j < 3; i++, j++) {
				for (int ii = bi.getHeight()/2-1, jj = 0; jj < 3; ii++, jj++) {
					bio.setRGB(j, jj, bi.getRGB(i, ii));
				}
			}
			ImageIO.write(bio, "BMP", new File(output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double calcPower(int x1, int y1, int angle) {
		double theta = (angle+0.0)/180.0*Math.PI;
		return sqrtG*(x2-x1)/Math.sqrt(Math.sin(2.0*theta)*(x2-x1)
				+(y2-y1)*(1+Math.cos(2.0*theta)));
	}
	
	public static void leftShot(int x1, int y1, int bx) {
		x1 = bx*2-x1;
		listPowersReverse(x1, y1);
	}
	
	public static void rightShot(int x1, int y1, int bx) {
		x1 = bx*2-x1;
		x1 = (x2-25)*2-x1;
		listPowers(x1, y1);
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
		
		JButton init = new JButton("init");
		init.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Point p = Common.findLandmark("e:\\basket.bmp", 0, 0, true);	//todo ok
				x2 = p.x;
				y2 = p.y;
				leftX = x2-524;	//todo
				rightX = x2+186;	//todo
				Common.robot.mouseMove(p.x, p.y);
			}
		});
		calcDialog.add(init);
		
		JButton calc = new JButton("calc");
		final JLabel power = new JLabel("100");
		calc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Point p = Common.findLandmarkPartial("e:\\basketball.bmp", 0, 0, 1024, 768);	//todo
				double pow = calcPower(p.x, p.y, 75);
				power.setText("" + (int)(pow+0.5));
				Common.robot.mouseMove(p.x, p.y);
				Common.robot.delay(1000);
				double theta = 75.0/180.0*Math.PI;
				Common.robot.mouseMove(p.x+(int)(pow*pixPerPower*Math.cos(theta)), 
						(int)(p.y-pow*pixPerPower*Math.sin(theta)));
			}
		});
		calcDialog.add(calc);
		calcDialog.add(power);
		
		JButton list = new JButton("list");
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Point p = Common.findLandmarkPartial("e:\\basketball.bmp", 0, 0, 1024, 768);	//todo
				listPowers(p.x, p.y);
			}
		});
		calcDialog.add(list);
		
		JButton left = new JButton("left");
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Point p = Common.findLandmarkPartial("e:\\basketball.bmp", 0, 0, 1024, 768);	//todo
				leftShot(p.x, p.y, leftX);
			}
		});
		calcDialog.add(left);
		
		JButton right = new JButton("right");
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Point p = Common.findLandmarkPartial("e:\\basketball.bmp", 0, 0, 1024, 768);	//todo
				rightShot(p.x, p.y, rightX);
			}
		});
		calcDialog.add(right);
		
		calcDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		calcDialog.setLocation(new Point(600, 600));
		calcDialog.setSize(400, 70);
		calcDialog.setAlwaysOnTop(true);
		calcDialog.setVisible(true);

	}
}
