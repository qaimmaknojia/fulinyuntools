package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class ColorTest {

	public static String iconFolder = "e:\\farmland\\";
	
	public static void main(String[] args) {
		
		TreeMap<Integer, Integer> brightness = new TreeMap<Integer, Integer>();
		File dir = new File(iconFolder);
		File[] files = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File arg0, String name) {
				return name.endsWith(".bmp");
			}
			
		});
		for (File f : files) {
			try {
				System.out.println(f.getName());
				BufferedImage bi = ImageIO.read(f);
				for (int i = 0; i < bi.getWidth(); i++) for (int j = 0; j < bi.getHeight(); j++) {
					Color rgb = new Color(bi.getRGB(i, j));
					int bright = rgb.getRed()*rgb.getRed()+rgb.getGreen()*rgb.getGreen()+rgb.getBlue()*rgb.getBlue();
					if (!brightness.containsKey(new Integer(bright))) {
						brightness.put(bright, 1);
					} else {
						brightness.put(bright, brightness.get(bright)+1);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Integer i : brightness.keySet()) if (brightness.get(i) > 5) System.out.println(i.intValue() + " : " + brightness.get(i));
	}
}
