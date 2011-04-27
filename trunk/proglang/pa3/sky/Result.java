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
}

