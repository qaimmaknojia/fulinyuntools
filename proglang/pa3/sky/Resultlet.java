package sky;

import java.io.*;

/* Result.java --
 *   Store results for one star (min-dist, max-dist, avg-dist)
 */

public class Resultlet implements Serializable {
	public double min;
	public double x;
	public double y;
	public double z;
	public double[][] minstar;
	public double max;
	public double[][] maxstar;
	public double avg;

	public String toString() {
		String ret = "";
		ret += "x"+x;
		ret += "y"+y;
		ret += "z"+z;
		ret += "min";
		ret += min;
		ret += "minstar";
		for (int i = 0; i < minstar.length; i++) {
			ret += minstar[i][0]+" "+minstar[i][1]+" "+minstar[i][2];
			ret += "    ";
		}
		ret += "max"+max;
		ret += "maxstar";
		for (int i = 0; i < maxstar.length; i++) {
			ret +=maxstar[i][0]+" "+maxstar[i][1]+" "+maxstar[i][2]+"    ";
		}
		ret +="avg"+avg;
		return ret;
	}
}

