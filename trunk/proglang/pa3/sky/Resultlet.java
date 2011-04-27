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

}

