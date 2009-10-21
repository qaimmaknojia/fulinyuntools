package util;

import java.util.List;
import java.util.Scanner;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;

public class IndexLooker {
	
	public static String indexFolder = 
		"\\\\poseidon\\team\\semantic search\\billiontripledata\\index\\";
	public static String lap2index = indexFolder+"refIndex";
	public static String lap3index = indexFolder+"basicFeatureIndex";

	public static void main(String[] args) throws Exception {
		lookBasicFeature();
	}
	
	public static void lookBasicFeature() throws Exception {
		Scanner sc = new Scanner(System.in);
		IndexReader ireader = IndexReader.open(lap3index);
		while (true) {
			int n = sc.nextInt();
			System.out.println(n);
			System.out.println(ireader.document(n).get("URI"));
			System.out.println(ireader.document(n).get("basic"));
		}
	}
	
	public static void lookRefIndex() throws Exception {
		Scanner sc = new Scanner(System.in);
		IndexReader ireader = IndexReader.open(lap2index);
		while (true) {
			int n = sc.nextInt();
			System.out.println(n);
			System.out.println(ireader.document(n).get("URI"));
			List fieldList = ireader.document(n).getFields();
			for (Object obj : fieldList) {
				Field field = (Field)obj;
				if (!field.name().equals("URI")) 
					System.out.println(field.name() + " : " + field.stringValue());
			}
		}
	}

}
