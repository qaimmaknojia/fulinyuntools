package sky;

import java.io.*;

/* Result.java --
 *   Store results (min-dist, max-dist)
 */

public class Result implements Serializable {
	public double min = 0.0;
	public String minstar1 = "";
	public String minstar2 = "";
	public double max = 0.0;
	public String maxstar1 = "";
	public String maxstar2 = "";

	Result(double min, String minstar1, String minstar2, double max, String maxstar1, String maxstar2) {
		this.min = min;
		this.minstar1 = minstar1;
		this.minstar2 = minstar2;
		this.max = max;
		this.maxstar1 = maxstar1;
		this.maxstar2 = maxstar2;
	}

}

