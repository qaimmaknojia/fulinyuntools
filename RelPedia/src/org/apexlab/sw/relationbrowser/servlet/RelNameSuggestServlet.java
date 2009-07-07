package org.apexlab.sw.relationbrowser.servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apexlab.sw.relationbrowser.relationbrowser.RelationNameSuggestor;

public class RelNameSuggestServlet extends HttpServlet {
	
	private PrintStream errLogPrint;
//	private PrintStream queryLogPrint;

	public void init() throws ServletException {
//		try{
//			errLogPrint = new PrintStream(
//					"D:\\Tomcat55" +
//					"\\webapps\\RelPedia\\ErrorLog");
//			queryLogPrint = new PrintStream(
//					"C:\\Program Files\\" +
//					"Apache Software Foundation\\Tomcat 5.5" +
//					"\\webapps\\RelPedia\\QueryLog");
//			System.setErr(errLogPrint);
//			System.setOut(errLogPrint);
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/xml");
			PrintWriter resultWriter = response.getWriter();
			
			resultWriter.println("<Suggestions>");
			
//			resultWriter.println("<Suggestion>Hello World</Suggestion>");
			
			String[] relSuggestion = RelationNameSuggestor.suggest(request.getParameter("query"));
			int suggestNum = relSuggestion.length;
			resultWriter.println("<SuggestNum>"+suggestNum+"</SuggestNum>");
			
			if(suggestNum != 0){
				for(int i=0; i<suggestNum; i++){
					resultWriter.println("<Suggestion>"+relSuggestion[i]+"</Suggestion>");
				}
			}
			resultWriter.println("</Suggestions>");
			
			resultWriter.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
//			errLogPrint.println(e.toString());
		}
	}
}
