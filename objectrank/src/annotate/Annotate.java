package annotate;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import linkGraphProcess.Processor;

/**
 * Servlet implementation class for Servlet: Annotate
 *
 */
 public class Annotate extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
   
	public Annotate() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
/*		String str=(String)request.getParameter("url");
		WebPageExp exp = new WebPageExp();
		WebPageExp.stopwords.add("-");
		WebPageExp.stopwords.add("&nbsp;");
		WebPageExp.stopwords.add("a");
		WebPageExp.stopwords.add("the");
		StringBuffer sb = new StringBuffer();
		Searcher searcher = new IndexSearcher(WebPageExp.linkRecomIdxFold);
		try {
			exp.annotate(str, sb, searcher);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getOutputStream().println(sb.toString());
		System.out.println("total processing time: " + (end.getTime()-start.getTime()) + " milliseconds.");
	*/
		String str = (String)request.getParameter("url");
		System.out.println("url = " + str);
//		if (print == null) System.out.println("print is null!");
	//	print.println(str);
	//WebPageExp.getStream1(str);
		String[] recognizedNames = WebPageExp.recognize(str);
		HashSet<Integer> inPage = Processor.getInPageId(recognizedNames);
		for (Integer i : inPage) System.out.println(Processor.nameMap[i.intValue()]);
		double[] pr = Processor.calculatePR(1.0, 6, Processor.matrix, Processor.olinkNum, inPage, 0.05);
		String[] list = Processor.selectTop(pr, 10, new double[10]);
		request.getSession().setAttribute("entities", list);
		PrintWriter print = response.getWriter();
		for (String s : list) print.println("<p>" + s + "</p>");
		print.close();
	}
}
