<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.dms.Admin" %>
<%@ page import="health.dms.AdminMan" %>
<%@ page import="health.PMF" %>
<%@ page import="health.function.Function" %>
<%
	request.setCharacterEncoding("utf-8");
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>谷风 - 功效管理</title>
<%    
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    boolean needLogin = false;
    if (user == null || !AdminMan.auth(user, Admin.AUTH_FUNCTION)) {
    needLogin = true;
%>
    <meta HTTP-EQUIV="REFRESH" content="0; url=/index.jsp">
<%  
    }
%>
  </head>
  <body>
<%
    if (needLogin) {
%>  
    <p>您没有权限管理功效，正在跳转至首页，请稍候...</p>
<%
    } else {  
%>
    <table border="0">
    <tr>
      <td><a href="/index.jsp"/>首页</td>
      <td>&nbsp;</td>
      <td><a href="/dms/DMSAccount.jsp"/>DMS帐号管理</td>
      <td>&nbsp;</td>
      <td><a href="/dish/dish.jsp"/>菜管理</td>
      <td>&nbsp;</td>
      <td><a href="/food/food.jsp"/>食料管理</td>
      <td>&nbsp;</td>
      <td><a href="/nutriment/nutriment.jsp"/>营养素管理</td>
      <td>&nbsp;</td>
      <td><a href="/userGroup/userGroup.jsp"/>人群管理</td>
      <td>&nbsp;</td>
      <td><a href="/function/function.jsp"/>功效管理</td>
      <td>&nbsp;</td>
      <td><a href="/unit/unit.jsp"/>度量单位管理</td>
      <td>&nbsp;</td>
      <td><a href="/user/user.jsp"/>用户管理</td>
    </tr>
    </table>
    
    <h1>功效管理</h1>
    <form action="function.jsp" method="post">
    <table border="0">
    <tr>
    <td>
    <select name="queryType">
	    <option value="">【选择查询项】</option>
	    <option value="ID">功效ID</option>
	    <option value="name">功效名</option>
    </select>
    </td>
    <td><input type="text" name="keyword" /></td>
    <td><input type="submit" value="查询"/></td>
    </tr>
    </table>
    </form>
    
    <table border="1">
    <tr>
      <th>功效ID</th>	
      <th>功效名</th>
      <th>添加时间</th>
      <th>添加者</th>	
      <th>管理</th>	
      <th>删除</th>
    </tr>
<%
	String queryType = request.getParameter("queryType");
	String keyword = request.getParameter("keyword");
	PersistenceManager pm = PMF.get().getPersistenceManager();
	String query = "select from " + Function.class.getName();
	List<Function> functions = (List<Function>) pm.newQuery(query).execute();
	if (queryType == null) {
		for (Function a : functions) {
%>
    <tr>
      <td><%= a.getID() %></td>	
      <td title="<%= a.getIntroduction() %>"><%= a.getName() %></td>	
      <td><%= a.getDate().toString() %></td>
      <td><%= a.getAuthor().getNickname() %></td>
      <td><a href="changeFunction.jsp?id=<%= a.getID() %>">修改</a></td>
      <td><a href="/deleteFunction?id=<%= a.getID() %>">删除</a></td>
    </tr>
<%
    	} 
	} else if (queryType.equals("ID")) {
		for (Function a : functions) {
    		if (a.getID() == Long.parseLong(keyword)) {
%>
      <tr>
        <td><%= a.getID() %></td>	
        <td title="<%= a.getIntroduction() %>"><%= a.getName() %></td>	
        <td><%= a.getDate().toString() %></td>
        <td><%= a.getAuthor().getNickname() %></td>
        <td><a href="changeFunction.jsp?id=<%= a.getID() %>">修改</a></td>
        <td><a href="/deleteFunction?id=<%= a.getID() %>">删除</a></td>
      </tr>
<%
    		} 
		}
	} else if (queryType.equals("name")) {
		for (Function a : functions) {
    		if (a.getName().contains(keyword)) {
%>    	      
	  <tr>
    	<td><%= a.getID() %></td>	
    	<td title="<%= a.getIntroduction() %>"><%= a.getName() %></td>	
    	<td><%= a.getDate().toString() %></td>
    	<td><%= a.getAuthor().getNickname() %></td>
    	<td><a href="changeFunction.jsp?id=<%= a.getID() %>">修改</a></td>
    	<td><a href="/deleteFunction?id=<%= a.getID() %>">删除</a></td>
      </tr>
<%
    		} 
		}
	} else {
		for (Function a : functions) {
%>
	  <tr>
    	<td><%= a.getID() %></td>	
    	<td title="<%= a.getIntroduction() %>"><%= a.getName() %></td>	
    	<td><%= a.getDate().toString() %></td>
    	<td><%= a.getAuthor().getNickname() %></td>
    	<td><a href="changeFunction.jsp?id=<%= a.getID() %>">修改</a></td>
    	<td><a href="/deleteFunction?id=<%= a.getID() %>">删除</a></td>
      </tr>
<%
    	}
	}
%>
    </table>
    
    <p><a href="addFunction.jsp">增加新功效</a></p>
    
<%
    }  
%>
  </body>
</html>
