import java.io.BufferedReader;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
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
	public static String lap2index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\lap2";
	public static String lap3index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\lap3";
	public static String lap4index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\lap4";
	public static String lap5index = "\\\\poseidon\\team\\Semantic Search\\BillionTripleData\\index\\lap5";
	
	public static void main(String[] args) throws Exception {
		lap1index(new String[]{Common.wordnet});
	}
	
	/**
	 * treat each attribute statement as one document with two fields named "URI" and "<predicateURI>", 
	 * and each relation statement as two documents (one for each direction) with two fields named "URI" 
	 * and "<predicateURI>to" or "<predicateURI>from", only the "URI" field is indexed and not tokenized, 
	 * relation targets/sources and attribute values are not indexed.
	 * @throws Exception
	 */
	public static void lap1index(String[] toIndex) throws Exception {
		System.out.println(new Date().toString() + " : start lap 1 indexing");
		org.apache.lucene.analysis.Analyzer analyzer = new StandardAnalyzer();

				// Store the index in memory:
				// Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		Directory directory = FSDirectory.getDirectory(lap1index);
		IndexWriter iwriter = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
		for (String s : toIndex) {
			System.out.println(new Date().toString() + " : start indexing " + s);
			BufferedReader br = IOFactory.getGzBufferedReader(s);
			int count = 0;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				String[] parts = line.split(" ");
				for (int i = 3; i < parts.length-1; i++) parts[2] += (" "+parts[i]); // get whole attribute value
				Document doc = new Document();
				doc.add(new Field("URI", parts[0], Field.Store.YES, Field.Index.NOT_ANALYZED));
				if (parts[2].startsWith("<")) { // relation
					doc.add(new Field(parts[1]+"to", parts[2], Field.Store.YES, Field.Index.NO));
				} else { // attribute
					doc.add(new Field(parts[1], parts[2], Field.Store.YES, Field.Index.NO));
				}
				iwriter.addDocument(doc);
				if (parts[2].startsWith("<")) { // relation
					Document docRev = new Document();
					docRev.add(new Field("URI", parts[2], Field.Store.YES, Field.Index.NOT_ANALYZED));
					docRev.add(new Field(parts[1]+"from", parts[0], Field.Store.YES, Field.Index.NOT_ANALYZED));
				}
				count++;
				if (count % 1000000 == 0) System.out.println(new Date().toString() + " : " + count);
			}
			System.out.println(new Date().toString() + " : " + count + " lines in all");
		}
		iwriter.optimize();
		iwriter.close();

	}
	
	/**
	 * merge documents with the same "URI" field. The purpose of this lap is to collect all the 
	 * information of an individual in one posting list for further indexing and easy browsing 
	 * during clustering result analysis. Relation targets/sources and attribute values are still 
	 * not indexed. 
	 */
	public static void lap2index() throws Exception {
		System.out.println(new Date().toString() + " : start lap 2 indexing");
		org.apache.lucene.analysis.Analyzer analyzer = new StandardAnalyzer();
		Directory directory = FSDirectory.getDirectory(lap2index);
		IndexWriter iwriter = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
		IndexReader ireader = IndexReader.open(lap1index);
		IndexSearcher isearcher = new IndexSearcher(lap1index);
		TermQuery q = new TermQuery(new Term);
	}
	
	/**
	 * based on the 2nd-lap index, merge each individual's attribute values into its "basic" field. 
	 * Attribute values are tokenized and indexed. URIs with "<rdf:type>from", "<owl:class>from" or 
	 * "<skos:subject>from" fields (i.e. classes) are not contained in the index.
	 */ 
	
	/**
	 * based on the 2nd- and 3rd- lap indexes, for each individual, merge its attribute values and 
	 * its neighbors' attribute values into its "extended" field. This field is tokenized and indexed.
	 * URIs with "<rdf:type>from", "<owl:class>from" or "<skos:subject>from" fields (i.e. classes) are 
	 * not contained in the index.
	 */
	
	/**
	 * for terms in the 3rd- and 4th- lap indexes whose term frequencies are more than 1, split them 
	 * into different terms such as "term", "term.1", "term.2" ... to meet the requirement of applying 
	 * the C. Xiao et al. WWW09 positional prefix filtering technique.
	 */
	
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
