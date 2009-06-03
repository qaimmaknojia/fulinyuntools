package main;

public interface Common {

	public static final String gzFolder = "\\\\poseidon\\team\\semantic search\\BillionTripleData\\gz\\";
	public static final String wordnet = gzFolder+"wordnet.gz"; // 1942887 triples
	public static final String dblp = gzFolder+"dblp.gz"; // 14936600 triples
	public static final String dbpedia = gzFolder+"dbpedia.gz"; // 110241463 triples
	public static final String geonames = gzFolder+"geonames.gz"; // 69778255 triples
	public static final String uscensus = gzFolder+"uscensus.gz"; // 445752172 triples
	public static final String foaf = gzFolder+"foaf.gz"; // 54000000 triples
	public static final String mbi = gzFolder+"mb.instance.gz"; // 8772612 triples
	public static final String mbr = gzFolder+"mb.relation.gz"; // 13591684 triples
	public static final String mba = gzFolder+"mb.attribute.gz"; // 16721842 triples
	
	public static final String rdfType = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	public static final String owlClass = "<http://www.w3.org/2002/07/owl#Class>";
	public static final String dbpediaSubject = "<http://www.w3.org/2004/02/skos/core#subject>";
	public static final String sameAs = "<http://www.w3.org/2002/07/owl#sameAs>";
	public static final String dbpediaSubclass = "<http://www.w3.org/2004/02/skos/core#broader>";
	
}
