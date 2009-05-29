package main;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import basic.IOFactory;


public class Indexer {

	public static String lap1index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\lap1";
	public static String lap2index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\refIndex";
	public static String lap3index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\basicFeatureIndex";
	public static String lap4index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\lap4ext";
	public static String lap5index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\lap5ext";
	public static String lap6index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\extendedFeatureIndex";
	
	public static void main(String[] args) throws Exception {
//		System.out.println(sortUnique("as soon as possible! yes, as soon as possible!!"));
//		lap1index(new String[]{Common.uscensus, Common.dbpedia, Common.geonames, Common.foaf,
//				Common.wordnet, Common.dblp,  
//				Common.mba, Common.mbi, Common.mbr}, true);
		lap1index(new String[]{Common.dbpedia}, lap1index+"\\dbpedia", true); // running
		lap1index(new String[]{Common.geonames, Common.dblp}, lap1index+"\\geonames&dblp", true); // to run
//		lap1index(new String[]{Common.uscensus}, false);
//		observeLap1index();
//		lap2index(new String[]{lap1index+"\\dbpedia", lap1index+"\\geonames&dblp"}); // to run
//		observeLap2index();
//		System.out.println(sortUnique("mary mary peter peter"));
	}
	
	/**
	 * treat each attribute statement as one document with two fields named "URI" and "<predicateURI>", 
	 * and each relation statement as two documents (one for each direction) with two fields named "URI" 
	 * and "<predicateURI>to" or "<predicateURI>from", only the "URI" field is indexed and not tokenized, 
	 * relation targets/sources and attribute values are not indexed.
	 * time consumption: 97 sec for 1942887 lines, -> 36732sec (10 hr) to index 735737515 triples
	 * @throws Exception
	 */
	public static void lap1index(String[] toIndex, String targetFolder, boolean deleteOld) throws Exception {
		System.out.println(new Date().toString() + " : start lap 1 indexing");
		org.apache.lucene.analysis.Analyzer analyzer = new WhitespaceAnalyzer();

				// Store the index in memory:
				// Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		Directory directory = FSDirectory.getDirectory(targetFolder);
		IndexWriter iwriter = new IndexWriter(directory, analyzer, deleteOld, 
				IndexWriter.MaxFieldLength.UNLIMITED);
//		iwriter.setRAMBufferSizeMB(800); // this could make the indexing process faster, 
		                                 // at the risk of OutOfMemory Exception when dealing 
		                                 // with a large data set
		iwriter.setMergeFactor(2); // hope this can help avoid "background merge hit exception"~~ failed!!!
		for (String s : toIndex) {
			System.out.println(new Date().toString() + " : start indexing " + s);
			BufferedReader br = IOFactory.getGzBufferedReader(s);
			int count = 0;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				String[] parts = line.split(" ");
				for (int i = 3; i < parts.length-1; i++) parts[2] += (" "+parts[i]); // get whole attribute value
				Document doc = new Document();
				doc.add(new Field("URI", parts[0], Field.Store.YES, Field.Index.NOT_ANALYZED));
				if (!parts[2].startsWith("\"")) { // relation
					doc.add(new Field(parts[1]+"to", parts[2], Field.Store.YES, Field.Index.NO));
				} else { // attribute
					doc.add(new Field(parts[1], parts[2], Field.Store.YES, Field.Index.NO));
				}
				iwriter.addDocument(doc);
				if (!parts[2].startsWith("\"")) { // relation
					Document docRev = new Document();
					docRev.add(new Field("URI", parts[2], Field.Store.YES, Field.Index.NOT_ANALYZED));
					docRev.add(new Field(parts[1]+"from", parts[0], Field.Store.YES, Field.Index.NO));
					iwriter.addDocument(docRev);
				}
				count++;
				if (count % 1000000 == 0) System.out.println(new Date().toString() + " : " + count);
			}
			br.close();
			System.out.println(new Date().toString() + " : " + count + " lines in all");
		}
		iwriter.optimize();
		iwriter.close();
		directory.close();
		System.out.println(new Date().toString() + " : optimized");
	}
	
	/**
	 * have a look at whether the "<predicate>from" fields have been stored
	 * already known they are not indexed
	 * @throws Exception
	 */
	public static void observeLap1index() throws Exception {
		IndexReader ireader = IndexReader.open(lap1index);
		int count = 0;
		System.out.println(ireader.maxDoc());
		for (int i = 0; i < ireader.maxDoc(); i++) {
			Document doc = ireader.document(i);
			List fields = doc.getFields();
			for (Object o : fields) {
				Field f = (Field)o;
				if (f.name().endsWith("from")) {
					System.out.println(doc.toString());
					count++;
					break;
				}
			}
			if (count > 0 && count%50 == 0) System.in.read();
		}
	}
	
	/**
	 * merge documents with the same "URI" field. The purpose of this lap is to collect all the 
	 * information of an individual in one posting list for further indexing and easy browsing 
	 * during clustering result analysis. Relation targets/sources and attribute values are still 
	 * not indexed. 
	 * speed: 24hr to index all the data sets
	 */
	public static void lap2index(String[] lap1indices) throws Exception {
		System.out.println(new Date().toString() + " : start lap 2 indexing");
		org.apache.lucene.analysis.Analyzer analyzer = new WhitespaceAnalyzer();
		Directory directory = FSDirectory.getDirectory(lap2index);
		final IndexWriter iwriter = new IndexWriter(directory, analyzer, true, 
				IndexWriter.MaxFieldLength.UNLIMITED);
//		iwriter.setRAMBufferSizeMB(1200);
		iwriter.setMergeFactor(2);
		int count = 0;
		for (int i = 0; i < lap1indices.length; i++) {
			final IndexReader ireader = IndexReader.open(lap1indices[i]);
			IndexSearcher isearcher = new IndexSearcher(lap1indices[i]);
			
			// had a look at the terms in the "URI" field; they are in ascending lexical order 
			TermEnum te = ireader.terms();
			while (te.next()) {
				Term term = te.term();
				if (term.field().equals("URI")) { // always true
					final Document d = new Document();
					d.add(new Field("URI", term.text(), Field.Store.YES, Field.Index.NOT_ANALYZED));
					isearcher.search(new TermQuery(term), new HitCollector() {
						@Override
						public void collect(int doc, float score) {
							try {
								List fieldList = ireader.document(doc).getFields();
								for (Object o : fieldList) {
									Field f = (Field)o;
									if (!f.name().equals("URI")) d.add(f);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					});
					iwriter.addDocument(d);
					count++; // number of URIs aggregated
					if (count%10000 == 0) System.out.println(new Date().toString() + " : " + count);
				}
			}
			ireader.close();
			isearcher.close();
		}
		System.out.println(new Date().toString() + " : " + count + " URIs aggregated");
		iwriter.optimize();
		iwriter.close();
		directory.close();
		System.out.println(new Date().toString() + " : optimized");
	}
	
	public static void observeLap2index() throws Exception {
		IndexReader ireader = IndexReader.open(lap2index);
		System.out.println(ireader.maxDoc());
		for (int i = 0; i < 40; i++) System.out.println(ireader.document(i).toString());
		System.out.println();
		TermEnum te = ireader.terms();
		for (int i = 0; i < 40; i++) {
			te.next();
			Term term = te.term();
			System.out.println(term.toString());
		}
		ireader.close();
	}
	
	/**
	 * basic feature index
	 * based on the 2nd-lap index, merge each individual's attribute values into its "basic" field. 
	 * Attribute values are tokenized and indexed. URIs with "<rdf:type>from", "<owl:class>from" or 
	 * "<skos:subject>from" fields (i.e. classes) are not contained in the index.
	 */ 
	public static void lap3index() throws Exception {
		System.out.println(new Date().toString() + " : start lap 3 indexing");
		org.apache.lucene.analysis.Analyzer analyzer = new WhitespaceAnalyzer();
		Directory directory = FSDirectory.getDirectory(lap3index);
		IndexWriter iwriter = new IndexWriter(directory, analyzer, true, 
				IndexWriter.MaxFieldLength.UNLIMITED);
//		iwriter.setRAMBufferSizeMB(1200);
		iwriter.setMergeFactor(2);
		IndexReader ireader = IndexReader.open(lap2index);
		for (int i = 0; i < ireader.maxDoc(); i++) {
			Document doc = ireader.document(i);
			List fieldList = doc.getFields();
			String basic = "";
			boolean isIndividual = true;
			for (Object o : fieldList) {
				Field f = (Field)o;
				String fn = f.name();
				if (fn.equals(Common.rdfType+"from") || fn.equals(Common.owlClass+"from") || 
						fn.equals(Common.dbpediaSubject+"from")) {
					isIndividual = false;
					break;
				} else if (!fn.equals("URI") && !fn.endsWith("from") && !fn.endsWith("to")) {
					basic += " " + f.stringValue();
				}
			}
			if (isIndividual) {
				Document odoc = new Document();
				odoc.add(new Field("URI", doc.get("URI"), Field.Store.YES, Field.Index.NOT_ANALYZED));
				odoc.add(new Field("basic", basic, Field.Store.YES, Field.Index.ANALYZED));
				iwriter.addDocument(odoc);
			}
			if ((i+1)%1000000 == 0) System.out.println(new Date().toString() + " : " + (i+1));
		}
		ireader.close();
		iwriter.optimize();
		iwriter.close();
		directory.close();
		System.out.println(new Date().toString() + " : optimized");
	}
	
	/**
	 * extended feature index
	 * based on the 2nd- and 3rd- lap indexes, for each individual, merge its attribute values and 
	 * its neighbors' attribute values into its "extended" field. This field is tokenized and indexed.
	 * URIs with "<rdf:type>from", "<owl:class>from" or "<skos:subject>from" fields (i.e. classes) are 
	 * not contained in the index.
	 */
	public static void lap4index() throws Exception {
		System.out.println(new Date().toString() + " : start lap 4 indexing");
		org.apache.lucene.analysis.Analyzer analyzer = new WhitespaceAnalyzer();
		Directory directory = FSDirectory.getDirectory(lap4index);
		IndexWriter iwriter = new IndexWriter(directory, analyzer, true, 
				IndexWriter.MaxFieldLength.UNLIMITED);
//		iwriter.setRAMBufferSizeMB(1200);
		iwriter.setMergeFactor(2);
		IndexReader ireader2 = IndexReader.open(lap2index);
		IndexReader ireader3 = IndexReader.open(lap3index);
		IndexSearcher isearcher3 = new IndexSearcher(ireader3);
		for (int i = 0; i < ireader3.maxDoc(); i++) {
			Document doc = ireader3.document(i);
			String uri = doc.get("URI");
			String extended = doc.get("basic");
//			TopDocs td = isearcher2.search(new TermQuery(new Term("URI", uri)), 1);
//			Document toExtend = ireader2.document(td.scoreDocs[0].doc);
			Document toExtend = ireader2.document(i);
			if (!toExtend.get("URI").equals(doc.get("URI"))) {
				System.out.println("URI not matched!!!");
				System.exit(1);
			}
			List fieldList = toExtend.getFields();
			for (Object o : fieldList) {
				Field f = (Field)o;
				if (f.name().endsWith("from") || f.name().endsWith("to")) {
					TopDocs tdbasic = isearcher3.search(new TermQuery(new Term("URI", 
							f.stringValue())), 1);
					Document dbasic = ireader3.document(tdbasic.scoreDocs[0].doc);
					extended += dbasic.get("basic");
				}
			}
			Document odoc = new Document();
			odoc.add(new Field("URI", uri, Field.Store.YES, Field.Index.NOT_ANALYZED));
			odoc.add(new Field("extended", extended, Field.Store.YES, Field.Index.ANALYZED));
			iwriter.addDocument(odoc);
			if ((i+1)%1000000 == 0) System.out.println(new Date().toString() + " : " + (i+1));
		}
		iwriter.optimize();
		iwriter.close();
		directory.close();
		isearcher3.close();
		ireader2.close();
		ireader3.close();
		System.out.println(new Date().toString() + " : optimized");

	}
	
	/**
	 * unique extended feature index
	 * for terms in the 4th-lap indexes ("extended" field) whose term frequencies are more than 1, split them 
	 * into different terms such as "term", "term.1", "term.2" ... and sort all the terms according 
	 * to the lexical order to meet the requirement of applying the C. Xiao et al. WWW09 positional 
	 * prefix filtering technique.
	 */
	public static void lap5index() throws Exception {
		System.out.println(new Date().toString() + " : start lap 5 indexing");
		org.apache.lucene.analysis.Analyzer analyzer = new WhitespaceAnalyzer();
		Directory directory = FSDirectory.getDirectory(lap5index);
		IndexWriter iwriter = new IndexWriter(directory, analyzer, true, 
				IndexWriter.MaxFieldLength.UNLIMITED);
//		iwriter.setRAMBufferSizeMB(1200);
		iwriter.setMergeFactor(2);
		IndexReader ireader4 = IndexReader.open(lap4index);
		for (int i = 0; i < ireader4.maxDoc(); i++) {
			Document dextended = ireader4.document(i);
			String uriex = dextended.get("URI");
			String extended = dextended.get("extended");
			String extendedsorted = sortUnique(extended);
			Document odoc = new Document();
			odoc.add(new Field("URI", uriex, Field.Store.YES, Field.Index.NOT_ANALYZED));
			odoc.add(new Field("extended", extendedsorted, Field.Store.YES, Field.Index.ANALYZED));
			iwriter.addDocument(odoc);
			if ((i+1)%1000000 == 0) System.out.println(new Date().toString() + " : " + (i+1));
		}
		iwriter.optimize();
		iwriter.close();
		directory.close();
		ireader4.close();
		System.out.println(new Date().toString() + " : optimized");
		
	}
	
	/**
	 * sort and unique tokens in str, duplicated tokens are assigned unique aliases
	 * @param str
	 * @return
	 */
	private static String sortUnique(String str) {
		String[] tokens = str.split(" ");
		Arrays.sort(tokens);
		for (int i = 0; i < tokens.length; i++) 
			for (int j = i+1; j < tokens.length && tokens[j].equals(tokens[i]); j++) tokens[j] += ("."+(j-i));
		String ret = tokens[0];
		for (int i = 1; i < tokens.length; i++) ret += " " + tokens[i];
		return ret;
	}

	/**
	 * sorted extended feature index
	 * sort the terms in 5th-lap index according to document frequency ordering
	 * @throws Exception
	 */
	public static void lap6index() throws Exception {
		System.out.println(new Date().toString() + " : start lap 6 indexing");
		org.apache.lucene.analysis.Analyzer analyzer = new WhitespaceAnalyzer();
		Directory directory = FSDirectory.getDirectory(lap6index);
		IndexWriter iwriter = new IndexWriter(directory, analyzer, true, 
				IndexWriter.MaxFieldLength.UNLIMITED);
//		iwriter.setRAMBufferSizeMB(1200);
		iwriter.setMergeFactor(2);
		final IndexReader ireader5 = IndexReader.open(lap5index);
		for (int i = 0; i < ireader5.maxDoc(); i++) {
			String ext = ireader5.document(i).get("extended");
			String[] words = ext.split(" ");
			Arrays.sort(words, new Comparator<String>() {

				@Override
				public int compare(String a, String b) {
					try {
						int dfa = ireader5.terms(new Term("extended", a)).docFreq();
						int dfb = ireader5.terms(new Term("extended", b)).docFreq();
						if (dfa > dfb) return 1;
						else if (dfa == dfb) return 0;
						return -1;
					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
					
				}
				
			});
			String extSorted = words[0];
			for (int j = 1; j < words.length; j++) extSorted += " " + words[j];
			Document doc = new Document();
			doc.add(new Field("extended", extSorted, Field.Store.YES, Field.Index.ANALYZED));
			iwriter.addDocument(doc);
			if ((i+1)%1000000 == 0) System.out.println(new Date().toString() + " : " + (i+1));
		}
		iwriter.optimize();
		iwriter.close();
		directory.close();
		ireader5.close();
		System.out.println(new Date().toString() + " : optimized");
	}
	
	/**
	 * test similarity calculation
	 */
	public static void test() throws Exception {
		org.apache.lucene.analysis.Analyzer analyzer = new StandardAnalyzer();
		for (String s : StandardAnalyzer.STOP_WORDS) System.out.println(s); // show all the stop words
		Directory directory = new RAMDirectory();
		IndexWriter iwriter = new IndexWriter(directory, analyzer, true, 
				IndexWriter.MaxFieldLength.UNLIMITED);
		Document doc = new Document();
		doc.add(new Field("text", "mary jane", Field.Store.YES, Field.Index.ANALYZED));
		iwriter.addDocument(doc);
		iwriter.optimize();
		iwriter.close();
		
		// Now search the index:
		IndexReader ireader = IndexReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(directory);
		// Parse a simple query that searches for "text":
		QueryParser parser = new QueryParser("text", analyzer);
		Query query = parser.parse("mary");
		TopDocs results = isearcher.search(query, 1);
		// Iterate through the results:
		for (int i = 0; i < results.totalHits; i++) {
			Document result = ireader.document(results.scoreDocs[i].doc);
			float score = results.scoreDocs[i].score;
			System.out.println(result.get("text") + " : " + score);
		}
		System.out.println("************");
		
		query = parser.parse("mary mary");
		results = isearcher.search(query, 1);
		for (int i = 0; i < results.totalHits; i++) {
			Document result = ireader.document(results.scoreDocs[i].doc);
			float score = results.scoreDocs[i].score;
			System.out.println(result.get("text") + " : " + score);
		}
		
		isearcher.close();
		directory.close(); 
	}
}
