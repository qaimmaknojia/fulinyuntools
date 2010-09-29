package edu.rpi.cs.os.queuingpolicy;

public class Util {

	/**
	 * convert time units to milliseconds
	 * @param timeUnits the number of time units to convert, 1 time unit = 0.5 ms
	 * @return equivalent milliseconds, represented by a string
	 */
	public static String getMs(int timeUnits) {
		return timeUnits/2 + (timeUnits%2 == 0 ? ".0" : ".5") + " ms";
	}
}
