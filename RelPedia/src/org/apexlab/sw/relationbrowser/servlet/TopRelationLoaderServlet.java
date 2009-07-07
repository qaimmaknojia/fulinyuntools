package org.apexlab.sw.relationbrowser.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apexlab.sw.relationbrowser.relationbrowser.RelAndNum;
import org.apexlab.sw.relationbrowser.relationbrowser.TopRelationLoader;

public class TopRelationLoaderServlet extends HttpServlet {

	ArrayList<RelAndNum> topRelations;
	
	public void init() throws ServletException {
		topRelations = TopRelationLoader.relationLoader("E:\\DBPediaData1\\top100relations.csv");
//		for (RelAndNum ran : topRelations) {
//			System.out.println(ran.RelName + " : " + ran.RelNum);
//		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println("doget of toprelationloaderservlet");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println("dopost of toprelationloaderservlet");
		try {
			response.setContentType("text/xml");
			PrintWriter resultWriter = response.getWriter();
			
			resultWriter.println("<TopRelations>");
			int topRelNum = topRelations.size();
//			System.out.println(topRelNum);
			for (int i = 0; i < topRelNum; i++) {
				resultWriter.println("<Relation><RelationName>" + topRelations.get(i).RelName
						+ "</RelationName><RelationNum>" + topRelations.get(i).RelNum
						+ "</RelationNum></Relation>");
			}
			resultWriter.println("</TopRelations>");
			
			resultWriter.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
