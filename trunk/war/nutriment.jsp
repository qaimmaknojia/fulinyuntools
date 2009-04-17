<%@ page contentType="text/html;charset=gb2312" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.Nutriment" %>
<%@ page import="health.PMF" %>
<%
request.setCharacterEncoding("gb2312");
%>
<html>
  <head>
	<meta http-equiv="Content-Type"	content="text/html; charset=gb2312">
<!-- <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" /> -->
  </head>

  <body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>您好，<%= user.getNickname() %>！（您可以
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">登出</a>。）</p>
<%
    } else {
%>
<p>您好！
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">登入</a>
以便在您贡献的条目中包含您的姓名。</p>
<%
    }
%>

<%
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + Nutriment.class.getName() + " order by date desc range 0,5";
    List<Nutriment> nutriments = (List<Nutriment>) pm.newQuery(query).execute();
    if (nutriments.isEmpty()) {
%>
<p>营养素表是空的。</p>
<%
    } else {
        for (Nutriment n : nutriments) {
%>
<p><%= n.getDate().toString()+" " %>
<%
            if (n.getAuthor() == null) {
%>
匿名用户添加了：&nbsp
<%
            } else {
%>
<b><%= n.getAuthor().getNickname() %></b> 添加了：&nbsp
<%
            }
%>
<%= n.getID()+","+n.getType()+","+n.getName()+","+n.getEnergyContent()+","+n.getIntroduction() %></p>
<%
        }
    }
    pm.close();
%>

	<form action="/nutriment" method="post">
    	<table border=0>
    	<tr><td>营养素类别：</td><td><input type="text" name="type"></td></tr>
    	<tr><td>营养素名：</td><td><input type="text" name="name"></td></tr>
    	<tr><td>能量含量(KJ/g)：</td><td><input type="text" name="energyContent"></td></tr>
    	<tr><td>介绍：</td><td><input type="text" name="introduction"></td></tr>
      	</table>
		<div><input type="submit" value="添加营养素" /></div>
    </form>

  </body>
</html>
