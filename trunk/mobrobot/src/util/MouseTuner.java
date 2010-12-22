package util;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.util.Scanner;


public class MouseTuner {

	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		Robot robot = new Robot(screen);
		while (true) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			robot.mouseMove(x, y);
		}
	}
	
}
