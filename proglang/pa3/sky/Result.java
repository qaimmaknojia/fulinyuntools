package sky;

import java.util.*;
import java.io.*;

public class Result implements Serializable {
	double cndis; // closest neighbor distance
	Vector[] cn; // closest neighbor pairs
	double fndis; // farthest neighbor distance
	Vector[] fn; // farthest neighbor pairs
	double minmaxdis; // minimum maximal distance
	Vector[] ihs; // ideal hub stars and farthest stars from them
	double maxmindis; // maximum min distance
	Vector[] ijs; // ideal jail stars
	double minavgdis; // minimum average distance
	Vector[] ics; // ideal capital stars
	long time; // time spent by the worker to get this result

	public String toString(int nstar) {
		String ret = "minimal pairwise distance: "+cndis+"\n";
		ret += outputVector(cn);
		ret += "\n";
		ret += "maximal pairwise distance: "+fndis+"\n";
		ret += outputVector(fn);
		ret += "\n";
		ret += "minimum maximal distance: "+minmaxdis+"\n";
		ret += outputVector(ihs);
		ret += "\n";
		ret += "maximum minimal distance: "+maxmindis+"\n";
		ret += outputVector(ijs);
		ret += "\n";
		ret += "minimal average distance: "+minavgdis/(nstar-1)+"\n";
		ret += outputVector(ics);
		ret += "\n";
		return ret;
	}

	private String outputVector(Vector[] list) {
		String[] cols = new String[list[0].size()];
		for (int i = 0; i < list[0].size(); i++) cols[i] = outputColumn(list, i);
		return concat(cols);
	}

	private String concat(String[] strings) {
		String ret = "";
		for (int i = 0; i < strings.length; i++) ret += (String)strings[i]+"\n";
		return ret;
	}

	private String outputColumn(Vector[] list, int col) {
		String ret = "<";
		for (int i = 0; i < list.length; i++) {
			ret += list[i].get(col);
			if (i == list.length-1) ret += ">";
			else if (i%3 == 2) ret += "> <";
			else ret += ",";
		}
		return ret;
	}

}

