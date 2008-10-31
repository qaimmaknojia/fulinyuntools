package test;

import java.util.HashSet;

public class HashSetTest {

	public static void main(String[] args) {
		HashSet<Integer> set = new HashSet<Integer>();
		for (Integer i : set) System.out.println(i.intValue());
	}
}
