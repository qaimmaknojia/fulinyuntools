package basic;

public interface IDataSourceReader {

	/**
	 * read next line from the data set
	 * @return the next line
	 * @throws Exception
	 */
	public String readLine() throws Exception;
	
	/**
	 * close the reader
	 * @throws Exception
	 */
	public void close() throws Exception;
}
