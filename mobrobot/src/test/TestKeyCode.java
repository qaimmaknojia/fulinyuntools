package test;

import java.awt.event.KeyEvent;

public class TestKeyCode {

	public static void main(String[] args) {
		System.out.println(KeyEvent.VK_COLON);
		System.out.println((int)':');
		String mobURL = "http://mob.xiaonei.com/home.do";
		for (int i = 0; i < mobURL.length(); i++) System.out.println((int)mobURL.charAt(i));
	}
}
