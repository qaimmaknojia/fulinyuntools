import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;


public class Path {

	public static int[] dirr = {0, -1, 0, 1};
	public static int[] dirc = {-1, 0, 1, 0};
	
	public static void main(String[] args) {
		test1();
	}
	
	public static void test1() {
		int n = 5;
		int[][] map = new int[n][n];
		for (int i = 0, k = 0; i < n; i++) {
			if (i%2 == 0) {
				for (int j = 0; j < n; j++, k++) {
				map[i][j] = k;
				System.out.print("\t" + map[i][j]);
				}
			} else {
				for (int j = n-1; j >= 0; j--, k++) {
					map[i][j] = k;
				}
				for (int j = 0; j < n; j++) System.out.print("\t" + map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		LinkedList<Point> result = longestDownwardPath(map);
		for (Point p : result) System.out.print("(" + p.r + ","+ p.c + "," + p.h + ")");
	}

	public static void test() {
		int n = 5;
		int h = 1000;
		int[][] map = new int[n][n];
		Random r = new Random();
		long seed = new Date().getTime();
		r.setSeed(seed);
		System.out.println("seed: " + seed);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				map[i][j] = r.nextInt(h);
				System.out.print("\t" + map[i][j]);
			}	
			System.out.println();
		}
		System.out.println();
		LinkedList<Point> result = longestDownwardPath(map);
		for (Point p : result) System.out.print("(" + p.r + ","+ p.c + "," + p.h + ")");
	}
	
	public static LinkedList<Point> longestDownwardPath(int[][] map) {
		int[][] lowerMap = new int[map.length][map[0].length];
		int[][] lengthMap = new int[map.length][map[0].length];
		boolean[][] filledMap = new boolean[map.length][map[0].length];
		for (int i = 0; i < lowerMap.length; i++) for (int j = 0; j < lowerMap[i].length; j++) lowerMap[i][j] = getLowerNum(map, i, j);
		for (int i = 0; i < lengthMap.length; i++) Arrays.fill(lengthMap[i], -1);
		for (int i = 0; i < filledMap.length; i++) Arrays.fill(filledMap[i], false);
		boolean flag = true;
		int count = 1;
		while (flag) {
			System.out.println("round " + count);
			flag = false;
			for (int i = 0; i < map.length; i++) for (int j = 0; j < map[i].length; j++) 
				if (!filledMap[i][j] && lowerMap[i][j] == fillNum(map, lengthMap, i, j)) {
				fill(lengthMap, i, j);
				filledMap[i][j] = true;
				flag = true;
			}
			count++;
		}
		return findPath(map, lengthMap);
	}
	
	public static int getLowerNum(int[][] map, int r, int c) {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			int rr = r+dirr[i];
			int cc = c+dirc[i];
			if (rr < 0 || rr >= map.length || cc < 0 || cc >= map[0].length) continue;
			if (map[rr][cc] < map[r][c]) count++;
		}
		return count;
	}
	
	public static int fillNum(int[][] map, int[][] lengthMap, int r, int c) {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			int rr = r + dirr[i];
			int cc = c + dirc[i];
			if (rr < 0 || rr >= map.length || cc < 0 || cc >= map[0].length) continue;
			if (lengthMap[rr][cc] != -1 && map[rr][cc] < map[r][c]) count++;
		}
		return count;
	}
	
	public static void fill(int[][] map, int r, int c) {
		int max = -1;
		for (int i = 0; i < 4; i++) {
			int rr = r + dirr[i];
			int cc = c + dirc[i];
			if (rr < 0 || rr >= map.length || cc < 0 || cc >= map[0].length) continue;
			if (map[rr][cc] > max) max = map[rr][cc];
		}
		map[r][c] = max+1;
	}
	
	public static LinkedList<Point> findPath(int[][] map, int[][] lengthMap) {
		int maxr = -1;
		int maxc = -1;
		int maxPath = -1;
		for (int i = 0; i < lengthMap.length; i++) for (int j = 0; j < lengthMap[i].length; j++) if (lengthMap[i][j] > maxPath) {
			maxr = i;
			maxc = j;
			maxPath = lengthMap[i][j];
		}
		LinkedList<Point> ret = new LinkedList<Point>();
		Point p = new Point(maxr, maxc, map[maxr][maxc], maxPath);
		ret.add(p);
		while (p.l >= 1) {
			System.out.println("(" + p.r + "," + p.c + "," + p.l + ")");
			int rr = -1, cc = -1;
			for (int i = 0; i < 4; i++) {
				rr = p.r + dirr[i];
				cc = p.c + dirc[i];
				if (rr < 0 || rr >= map.length || cc < 0 || cc >= map[0].length) continue;
				if (lengthMap[rr][cc] == p.l-1) break;
			}
			p = new Point(rr, cc, map[rr][cc], lengthMap[rr][cc]);
			ret.add(p);
		}
		return ret;
	}
}

class Point {
	public int r;
	public int c;
	public int h;
	public int l;
	
	public Point(int row, int column, int height, int length) {
		r = row;
		c = column;
		h = height;
		l = length;
	}
}
