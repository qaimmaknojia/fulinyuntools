import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class FileSearcher {

//	public static String root = "d:\\files\\pay as you go matching\\";
	public static String favoriteFile = "e:\\favoriteFiles";
	
	public static void main(String[] args) {
//		mainBookmark();
		mainSearch();
//		clean();
	}
	
	public static void clean() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(favoriteFile+" bak "+new Date().toString().replaceAll(":", "_")));
			BufferedReader br = new BufferedReader(new FileReader(favoriteFile));
			HashSet<String> fs = new HashSet<String>();
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				pw.println(line);
				fs.add(line);
			}
			pw.close();
			br.close();
			String[] fa = new String[fs.size()];
			int i = 0;
			for (String s : fs) {
				fa[i] = s;
				i++;
			}
			for (i = 0; i < fa.length; i++) {
				for (int j = i+1; j < fa.length; j++) {
					File fi = new File(fa[i]);
					File fj = new File(fa[j]);
					if (fj.isDirectory() && fi.getParent().startsWith(fj.getAbsolutePath())) fs.remove(fa[i]);//fj contains fi
					if (fi.isDirectory() && fj.getParent().startsWith(fi.getAbsolutePath())) fs.remove(fa[j]);//fi contains fj
				}
			}
			pw = new PrintWriter(new FileWriter(favoriteFile));
			for (String s : fs) if (new File(s).exists()) pw.println(s);
			pw.close();
			System.out.println("cleaned");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void mainBookmark() {
		System.out.println("bookmark");
		JFrame frame = new DropTargetFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void mainSearch() {
		System.out.println("search");
		Scanner sc = new Scanner(System.in);
		while (true) {
			searchFile(sc.nextLine());
			System.out.println("*****************************************");
			System.out.println();
		}
	}
	
	private static void searchFile(String key) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(favoriteFile));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if (new File(line).isDirectory()) searchDir(line, key);
				else if (line.toLowerCase().contains(key.toLowerCase())) System.out.println(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void searchDir(String root, String key) {
		File all = new File(root);
		File[] files = all.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				searchDir(f.getAbsolutePath(), key);
			} else if (f.getName().toLowerCase().contains(key.toLowerCase())) {
				System.out.println(f.getAbsolutePath());
			}
		}
	}
}

/**
 This frame contains a text area that is a simple drop target.
 */
class DropTargetFrame extends JFrame {
	
	public DropTargetFrame() {
		setTitle("Bookmarks");
		setLocation(700, 700);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JTextArea textArea = new JTextArea("Drop files into this text area.\n");

		new DropTarget(textArea, new TextDropTargetListener(textArea));
		add(new JScrollPane(textArea), "Center");
	}

	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 300;
}

/**
 This listener displays the properties of a dropped object.
 */
class TextDropTargetListener implements DropTargetListener {
	/**
	 Constructs a listener.
	 @param aTextArea the text area in which to display the
	 properties of the dropped object.
	 */
	public TextDropTargetListener(JTextArea aTextArea) {
		textArea = aTextArea;
	}

	public void dragEnter(DropTargetDragEvent event) {
		if (!isDragAcceptable(event)) {
			event.rejectDrag();
			return;
		}
	}

	public void dragExit(DropTargetEvent event) {
	}

	public void dragOver(DropTargetDragEvent event) {
		// you can provide visual feedback here
	}

	public void dropActionChanged(DropTargetDragEvent event) {
		if (!isDragAcceptable(event)) {
			event.rejectDrag();
			return;
		}
	}

	public void drop(DropTargetDropEvent event) {
		if (!isDropAcceptable(event)) {
			event.rejectDrop();
			return;
		}

		event.acceptDrop(DnDConstants.ACTION_COPY);

		Transferable transferable = event.getTransferable();

		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		for (int i = 0; i < flavors.length; i++) {
			DataFlavor d = flavors[i];
//			textArea.append("MIME type=" + d.getMimeType() + "\n");

			try {
				if (d.equals(DataFlavor.javaFileListFlavor)) {
					java.util.List<File> fileList = (java.util.List<File>) transferable
							.getTransferData(d);
					for (File f : fileList) {
						textArea.append(f.getAbsolutePath() + "\n");
						PrintWriter pw = new PrintWriter(new FileWriter(FileSearcher.favoriteFile, true));
						pw.println(f.getAbsolutePath());
						pw.close();
					}
				} /*else if (d.equals(DataFlavor.stringFlavor)) {
					String s = (String) transferable.getTransferData(d);
					textArea.append(s + "\n");
				}*/
			} catch (Exception e) {
				textArea.append(e + "\n");
			}
		}
		textArea.append("\n");
		event.dropComplete(true);
	}

	public boolean isDragAcceptable(DropTargetDragEvent event) {
		// usually, you check the available data flavors here
		// in this program, we accept all flavors
		return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
	}

	public boolean isDropAcceptable(DropTargetDropEvent event) {
		// usually, you check the available data flavors here
		// in this program, we accept all flavors
		return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
	}

	private JTextArea textArea;
}
