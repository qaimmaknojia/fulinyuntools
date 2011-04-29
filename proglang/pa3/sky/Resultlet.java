package sky;

import java.io.*;

/** a resultlet stores the closest and the farthest stars to one particular star, and the total distance from this star to some other stars */
public class Resultlet implements Serializable {
	public double min; // minimum distance
	public double x; // x coordinate of this star
	public double y; // y coordinate of this star
	public double z; // z coordinate of this star
	public double[][] minstar; // closest star coordinates
	public double max; // maximum distance
	public double[][] maxstar; // farthest star coordinates
	public double avg; // total distance, actually

	/** returns a string describing the resultlet */
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

