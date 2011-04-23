package sky;

import java.io.*;

/* Distance.java --
 * Store a distance between two stars and the coordinates of both stars
 */

public class Distance implements Serializable {
	public double d = 0.0;
	public String s1 = "";
	public String s2 = "";

	Distance(double d, String s1, String s2) {
		this.d = d;
		this.s1 = s1;
		this.s2 = s2;
	}

	Distance(double x, double y, double z, double x1, double y1, double z1) {
		double dx = x-x1;
		double dy = y-y1;
		double dz = z-z1;
		d = Math.sqrt(dx*dx+dy*dy+dz*dz);
		s1 = x+" "+y+" "+z;
	 	s2 = x1+" "+y1+" "+z1;
	}

}

