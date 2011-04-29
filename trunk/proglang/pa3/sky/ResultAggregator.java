package sky;

import java.io.*;
import java.util.*;

public class ResultAggregator implements Serializable {

	public static Result summarize(Result[] results, int ntheater) {
		double cndis = Double.MAX_VALUE; // closest neighbour distance
		Vector[] cn = new Vector[6]; // closest neighbour pairs
		for (int i = 0; i < 6; i++)
			cn[i] = new Vector();
		double fndis = 0.0; // farthest neighbour distance
		Vector[] fn = new Vector[6]; // farthest neighbour pairs
		for (int i = 0; i < 6; i++)
			fn[i] = new Vector();
		double minmaxdis = Double.MAX_VALUE; // minimum max distance
		Vector[] ihs = new Vector[6]; // ideal hub stars
		for (int i = 0; i < 6; i++)
			ihs[i] = new Vector();
		double maxmindis = 0.0; // maximum min distance
		Vector[] ijs = new Vector[6]; // ideal jail stars
		for (int i = 0; i < 6; i++)
			ijs[i] = new Vector();
		double minavgdis = Double.MAX_VALUE; // minimum average distance
		Vector[] ics = new Vector[3]; // ideal capital stars
		for (int i = 0; i < 3; i++)
			ics[i] = new Vector();

		for (int i = 0; i < results.length; i++) {
			Result v = results[i];

			if (v.cndis < cndis) {
				cndis = v.cndis;
				cn = v.cn;
			} else if (v.cndis == cndis) {
				cn[0].addAll(v.cn[0]);
				cn[1].addAll(v.cn[1]);
				cn[2].addAll(v.cn[2]);
				cn[3].addAll(v.cn[3]);
				cn[4].addAll(v.cn[4]);
				cn[5].addAll(v.cn[5]);
			}

			if (v.fndis > fndis) {
				fndis = v.fndis;
				fn = v.fn;
			} else if (v.fndis == fndis) {
				fn[0].addAll(v.fn[0]);
				fn[1].addAll(v.fn[1]);
				fn[2].addAll(v.fn[2]);
				fn[3].addAll(v.fn[3]);
				fn[4].addAll(v.fn[4]);
				fn[5].addAll(v.fn[5]);
			}

			if (v.minmaxdis < minmaxdis) {
				minmaxdis = v.minmaxdis;
				ihs = v.ihs;
			} else if (v.minmaxdis == minmaxdis) {
				ihs[0].addAll(v.ihs[0]);
				ihs[1].addAll(v.ihs[1]);
				ihs[2].addAll(v.ihs[2]);
				ihs[3].addAll(v.ihs[3]);
				ihs[4].addAll(v.ihs[4]);
				ihs[5].addAll(v.ihs[5]);
			}

			if (v.maxmindis > maxmindis) {
				maxmindis = v.maxmindis;
				ijs = v.ijs;
			} else if (v.maxmindis == maxmindis) {
				ijs[0].addAll(v.ijs[0]);
				ijs[1].addAll(v.ijs[1]);
				ijs[2].addAll(v.ijs[2]);
				ijs[3].addAll(v.ijs[3]);
				ijs[4].addAll(v.ijs[4]);
				ijs[5].addAll(v.ijs[5]);
			}

			if (v.minavgdis < minavgdis) {
				minavgdis = v.minavgdis;
				ics = v.ics;
			} else if (v.minavgdis == minavgdis) {
				ics[0].addAll(v.ics[0]);
				ics[1].addAll(v.ics[1]);
				ics[2].addAll(v.ics[2]);
			}

		}

		Vector[] cndedup = dedupPair(cn);
		Vector[] fndedup = dedupPair(fn);

		long twork = 0;
		if (ntheater != 0) {
			for (int i = 0; i < ntheater; i++) {
				long theatertime = 0;
				for (int j = i; j < results.length; j += ntheater)
					theatertime += results[j].time;
				if (theatertime > twork)
					twork = theatertime;
			}
		} else {
			for (int i = 0; i < results.length; i++)
				if (results[i].time > twork)
					twork = results[i].time;
		}

		Result ret = new Result();
		ret.cndis = cndis;
		ret.cn = dedup(cndedup);
		ret.fndis = fndis;
		ret.fn = dedup(fndedup);
		ret.minmaxdis = minmaxdis;
		ret.ihs = dedup(ihs);
		ret.maxmindis = maxmindis;
		ret.ijs = dedup(ijs);
		ret.minavgdis = minavgdis;
		ret.ics = dedup3(ics);
		ret.time = twork;

		return ret;
	}

	private static Vector[] dedupPair(Vector[] pairs) {
		for (int i = 1; i < pairs[0].size();) {
			boolean removed = false;
			for (int j = 0; j < i; j++) {
				double i0 = (Double) pairs[0].get(i);
				double i1 = (Double) pairs[1].get(i);
				double i2 = (Double) pairs[2].get(i);
				double i3 = (Double) pairs[3].get(i);
				double i4 = (Double) pairs[4].get(i);
				double i5 = (Double) pairs[5].get(i);
				double j0 = (Double) pairs[0].get(j);
				double j1 = (Double) pairs[1].get(j);
				double j2 = (Double) pairs[2].get(j);
				double j3 = (Double) pairs[3].get(j);
				double j4 = (Double) pairs[4].get(j);
				double j5 = (Double) pairs[5].get(j);
				if (i0 == j3 && i1 == j4 && i2 == j5 && i3 == j0 && i4 == j1
						&& i5 == j2) {
					for (int k = 0; k < 6; k++)
						pairs[k].remove(i);
					removed = true;
					break;
				}
			}
			if (!removed)
				i++;
		}
		return pairs;
	}

	private static Vector[] dedup(Vector[] pairs) {
		for (int i = 1; i < pairs[0].size();) {
			boolean removed = false;
			for (int j = 0; j < i; j++) {
				double i0 = (Double) pairs[0].get(i);
				double i1 = (Double) pairs[1].get(i);
				double i2 = (Double) pairs[2].get(i);
				double i3 = (Double) pairs[3].get(i);
				double i4 = (Double) pairs[4].get(i);
				double i5 = (Double) pairs[5].get(i);
				double j0 = (Double) pairs[0].get(j);
				double j1 = (Double) pairs[1].get(j);
				double j2 = (Double) pairs[2].get(j);
				double j3 = (Double) pairs[3].get(j);
				double j4 = (Double) pairs[4].get(j);
				double j5 = (Double) pairs[5].get(j);
				if (i0 == j0 && i1 == j1 && i2 == j2 && i3 == j3 && i4 == j4
						&& i5 == j5) {
					for (int k = 0; k < 6; k++)
						pairs[k].remove(i);
					removed = true;
					break;
				}
			}
			if (!removed)
				i++;
		}
		return pairs;
	}

	private static Vector[] dedup3(Vector[] pairs) {
		for (int i = 1; i < pairs[0].size();) {
			boolean removed = false;
			for (int j = 0; j < i; j++) {
				double i0 = (Double) pairs[0].get(i);
				double i1 = (Double) pairs[1].get(i);
				double i2 = (Double) pairs[2].get(i);
				double j0 = (Double) pairs[0].get(j);
				double j1 = (Double) pairs[1].get(j);
				double j2 = (Double) pairs[2].get(j);
				if (i0 == j0 && i1 == j1 && i2 == j2) {
					for (int k = 0; k < 3; k++)
						pairs[k].remove(i);
					removed = true;
					break;
				}
			}
			if (!removed)
				i++;
		}
		return pairs;
	}

	public static Resultlet getMinMax(double[] star, double[][] values) {
		Resultlet r = new Resultlet();
		r.x = star[0];
		r.y = star[1];
		r.z = star[2];
		r.min = Double.MAX_VALUE;
		r.max = 0.0;
		r.avg = 0.0;
		for (int i = 0; i < values.length; i++) {

			double dx = r.x - values[i][0];
			double dy = r.y - values[i][1];
			double dz = r.z - values[i][2];
			if (dx != 0.0 || dy != 0.0 || dz != 0.0) {
				double dis = Math.sqrt(dx * dx + dy * dy + dz * dz);

				if (dis < r.min) {
					r.min = dis;
					r.minstar = new double[1][3];
					for (int j = 0; j < 3; j++)
						r.minstar[0][j] = values[i][j];
				} else if (dis == r.min) {
					double[][] t = new double[r.minstar.length + 1][3];
					for (int j = 0; j < r.minstar.length; j++)
						for (int k = 0; k < 3; k++)
							t[j][k] = r.minstar[j][k];
					for (int j = 0; j < 3; j++)
						t[r.minstar.length][j] = values[i][j];
					r.minstar = t;
				}

				if (dis > r.max) {
					r.max = dis;
					r.maxstar = new double[1][3];
					for (int j = 0; j < 3; j++)
						r.maxstar[0][j] = values[i][j];
				} else if (dis == r.max) {
					double[][] t = new double[r.maxstar.length + 1][3];
					for (int j = 0; j < r.maxstar.length; j++)
						for (int k = 0; k < 3; k++)
							t[j][k] = r.maxstar[j][k];
					for (int j = 0; j < 3; j++)
						t[r.maxstar.length][j] = values[i][j];
					r.maxstar = t;
				}

				r.avg += dis;
			}
		}
		return r;
	}

	public static Result combine(Resultlet[] results, long initialTime,
			int interleave) {
		// for (int i = 0; i < results.length; i++) {
		// System.out.println(results[i].toString());
		// }
		Result ret = new Result();
		ret.cndis = Double.MAX_VALUE; // closest neighbour distance
		ret.cn = new Vector[6]; // closest neighbour pairs
		for (int i = 0; i < 6; i++)
			ret.cn[i] = new Vector();
		ret.fndis = 0.0; // farthest neighbour distance
		ret.fn = new Vector[6]; // farthest neighbour pairs
		for (int i = 0; i < 6; i++)
			ret.fn[i] = new Vector();
		ret.minmaxdis = Double.MAX_VALUE; // minimum max distance
		ret.ihs = new Vector[6]; // ideal hub stars
		for (int i = 0; i < 6; i++)
			ret.ihs[i] = new Vector();
		ret.maxmindis = 0.0; // maximum min distance
		ret.ijs = new Vector[6]; // ideal jail stars
		for (int i = 0; i < 6; i++)
			ret.ijs[i] = new Vector();
		ret.minavgdis = Double.MAX_VALUE; // minimum average distance
		ret.ics = new Vector[3]; // ideal capital stars
		for (int i = 0; i < 3; i++)
			ret.ics[i] = new Vector();

		for (int i = 0; i < results.length; i++) {
			Resultlet v = results[i];

			if (v.min < ret.cndis) {
				ret.cndis = v.min;
				for (int j = 0; j < 6; j++)
					ret.cn[j].clear();
				for (int j = 0; j < v.minstar.length; j++) {
					ret.cn[0].add(v.x);
					ret.cn[1].add(v.y);
					ret.cn[2].add(v.z);
					ret.cn[3].add(v.minstar[j][0]);
					ret.cn[4].add(v.minstar[j][1]);
					ret.cn[5].add(v.minstar[j][2]);
				}
			} else if (v.min == ret.cndis) {
				for (int j = 0; j < v.minstar.length; j++) {
					ret.cn[0].add(v.x);
					ret.cn[1].add(v.y);
					ret.cn[2].add(v.z);
					ret.cn[3].add(v.minstar[j][0]);
					ret.cn[4].add(v.minstar[j][1]);
					ret.cn[5].add(v.minstar[j][2]);
				}
			}

			if (v.max > ret.fndis) {
				ret.fndis = v.max;
				for (int j = 0; j < 6; j++)
					ret.fn[j].clear();
				for (int j = 0; j < v.maxstar.length; j++) {
					ret.fn[0].add(v.x);
					ret.fn[1].add(v.y);
					ret.fn[2].add(v.z);
					ret.fn[3].add(v.maxstar[j][0]);
					ret.fn[4].add(v.maxstar[j][1]);
					ret.fn[5].add(v.maxstar[j][2]);
				}
			} else if (v.max == ret.fndis) {
				for (int j = 0; j < v.maxstar.length; j++) {
					ret.fn[0].add(v.x);
					ret.fn[1].add(v.y);
					ret.fn[2].add(v.z);
					ret.fn[3].add(v.maxstar[j][0]);
					ret.fn[4].add(v.maxstar[j][1]);
					ret.fn[5].add(v.maxstar[j][2]);
				}
			}
		}

		for (int i = 0; i < interleave; i++) {
			double max = 0.0;
			for (int j = i; j < results.length; j += interleave) {
				if (results[j].max > max) {
					max = results[j].max;
					results[i].maxstar = results[j].maxstar;
				} else if (results[j].max == max && j != i) {
					double[][] maxstar = new double[results[i].maxstar.length
							+ results[j].maxstar.length][3];
					for (int k = 0; k < results[i].maxstar.length; k++)
						for (int l = 0; l < 3; l++)
							maxstar[k][l] = results[i].maxstar[k][l];
					for (int k = 0, kk = results[i].maxstar.length; k < results[j].maxstar.length; k++, kk++)
						for (int l = 0; l < 3; l++)
							maxstar[kk][l] = results[j].maxstar[k][l];
					results[i].maxstar = maxstar;
				}
			}
			Resultlet v = results[i];
			if (max < ret.minmaxdis) {
				ret.minmaxdis = max;
				for (int j = 0; j < 6; j++)
					ret.ihs[j].clear();
				for (int j = 0; j < v.maxstar.length; j++) {
					ret.ihs[0].add(v.x);
					ret.ihs[1].add(v.y);
					ret.ihs[2].add(v.z);
					ret.ihs[3].add(v.maxstar[j][0]);
					ret.ihs[4].add(v.maxstar[j][1]);
					ret.ihs[5].add(v.maxstar[j][2]);
				}
			} else if (max == ret.minmaxdis) {
				for (int j = 0; j < v.maxstar.length; j++) {
					ret.ihs[0].add(v.x);
					ret.ihs[1].add(v.y);
					ret.ihs[2].add(v.z);
					ret.ihs[3].add(v.maxstar[j][0]);
					ret.ihs[4].add(v.maxstar[j][1]);
					ret.ihs[5].add(v.maxstar[j][2]);
				}
			}
		}

		for (int i = 0; i < interleave; i++) {
			double min = Double.MAX_VALUE;
			for (int j = i; j < results.length; j += interleave) {
				if (results[j].min < min) {
					min = results[j].min;
					results[i].minstar = results[j].minstar;
				} else if (results[j].min == min && j != i) {
					double[][] minstar = new double[results[i].minstar.length
							+ results[j].minstar.length][3];
					for (int k = 0; k < results[i].minstar.length; k++)
						for (int l = 0; l < 3; l++)
							minstar[k][l] = results[i].minstar[k][l];
					for (int k = 0, kk = results[i].minstar.length; k < results[j].minstar.length; k++, kk++)
						for (int l = 0; l < 3; l++)
							minstar[kk][l] = results[j].minstar[k][l];
					results[i].minstar = minstar;
				}
			}
			Resultlet v = results[i];
			if (min > ret.maxmindis) {
				ret.maxmindis = min;
				for (int j = 0; j < 6; j++)
					ret.ijs[j].clear();
				for (int j = 0; j < v.minstar.length; j++) {
					ret.ijs[0].add(v.x);
					ret.ijs[1].add(v.y);
					ret.ijs[2].add(v.z);
					ret.ijs[3].add(v.minstar[j][0]);
					ret.ijs[4].add(v.minstar[j][1]);
					ret.ijs[5].add(v.minstar[j][2]);
				}
			} else if (min == ret.maxmindis) {
				for (int j = 0; j < v.minstar.length; j++) {
					ret.ijs[0].add(v.x);
					ret.ijs[1].add(v.y);
					ret.ijs[2].add(v.z);
					ret.ijs[3].add(v.minstar[j][0]);
					ret.ijs[4].add(v.minstar[j][1]);
					ret.ijs[5].add(v.minstar[j][2]);
				}
			}
		}

		for (int i = 0; i < interleave; i++) {
			double avg = 0.0;
			for (int j = i; j < results.length; j += interleave) {
				avg += results[j].avg;
			}
			Resultlet v = results[i];
			if (avg < ret.minavgdis) {
				ret.minavgdis = avg;
				for (int j = 0; j < 3; j++)
					ret.ics[j].clear();
				ret.ics[0].add(v.x);
				ret.ics[1].add(v.y);
				ret.ics[2].add(v.z);
			} else if (avg == ret.minavgdis) {
				ret.ics[0].add(v.x);
				ret.ics[1].add(v.y);
				ret.ics[2].add(v.z);
			}
		}

		ret.time = System.currentTimeMillis() - initialTime;
		return ret;
	}

	// public static Resultlet combine1(Resultlet[] snippets) {
	// Resultlet r = new Resultlet();
	// r.min = Double.MAX_VALUE;
	// r.max = 0.0;
	// r.avg = 0.0;
	// for (int i = 0; i < snippets.length; i++) {
	// Resultlet rl = snippets[i];
	// if (rl.min < r.min) {
	// r.min = rl.min;
	// r.minstar = rl.minstar;
	// } else if (rl.min == r.min) {
	// double[][] minstar = new double[rl.minstar.length+r.minstar.length][3];
	// for (int j = 0; j < rl.minstar.length; j++) for (int k = 0; k < 3; k++)
	// minstar[j][k] = rl.minstar[j][k];
	// for (int j = 0, jj = rl.minstar.length; j < r.minstar.length; j++, jj++)
	// for (int k = 0; k < 3; k++) minstar[jj][k] = r.minstar[j][k];
	// r.minstar = minstar;
	// }
	//
	// if (rl.max > r.max) {
	// r.max = rl.max;
	// r.maxstar = rl.maxstar;
	// } else if (rl.max == r.max) {
	// double[][] maxstar = new double[rl.maxstar.length+r.maxstar.length][3];
	// for (int j = 0; j < rl.maxstar.length; j++) for (int k = 0; k < 3; k++)
	// maxstar[j][k] = rl.maxstar[j][k];
	// for (int j = 0, jj = rl.maxstar.length; j < r.maxstar.length; j++, jj++)
	// for (int k = 0; k < 3; k++) maxstar[jj][k] = r.maxstar[j][k];
	// r.maxstar = maxstar;
	// }
	//
	// r.avg += rl.avg;
	// }
	// r.x = snippets[0].x;
	// r.y = snippets[0].y;
	// r.z = snippets[0].z;
	//
	// return r;
	// }

}
