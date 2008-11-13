package annotate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: Highlight
 *
 */
 public class Highlight extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public Highlight() {
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
		String str = (String)request.getParameter("url");
		System.out.println("begin highlighting...\nurl = " + str);
		ServletContext context = this.getServletContext();
        String[] entities = (String[])context.getAttribute("entities");
//		String[] entities = (String[])request.getSession().getAttribute("entities");
		while (entities == null) {
			System.out.println(new Date().toString());
			System.out.println("entities not found in session");
			try {
				Thread.currentThread().sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			entities = (String[])context.getAttribute("entities");
		}
		System.out.println("got entities");
//      RequestDispatcher dispatcher = context.getRequestDispatcher("/Annotate");
//      dispatcher.include(request, response);

		try {
			String text = WebPageExp.highlight(str, entities);
//			request.getSession().removeAttribute("entities");
			PrintWriter print = response.getWriter();
			print.println(text);
			print.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
