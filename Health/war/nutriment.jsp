<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.dms.AdminMan" %>
<%@ page import="health.dms.Admin" %>
<%@ page import="health.Nutriment" %>
<%@ page import="health.PMF" %>
<%
request.setCharacterEncoding("utf-8");
%>
<html>
  <head>
	<meta http-equiv="Content-Type"	content="text/html; charset=utf-8">
<!-- <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" /> -->
    <title>谷风 - 营养素管理</title>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    boolean needLogin = false;
    if (user == null || !AdminMan.auth(user, Admin.AUTH_NUTRIMENT)) {
        needLogin = true;
%>
    <meta HTTP-EQUIV="REFRESH" content="0; url=index.jsp">
<%  
    }
%>
  </head>

  <body>

<%
    if (needLogin) {
%>
    <p>您没有权限修改营养素，正在跳转至首页，请稍候...</p>
<%
    } else {  
%>
    <table border="0">
    <tr>
      <td><a href="index.jsp"/>首页</td>
      <td>&nbsp;</td>
      <td><a href="DMSAccount.jsp"/>DMS帐号管理</td>
      <td>&nbsp;</td>
      <td><a href="dish.jsp"/>菜管理</td>
      <td>&nbsp;</td>
      <td><a href="food.jsp"/>食料管理</td>
      <td>&nbsp;</td>
      <td><a href="nutriment.jsp"/>营养素管理</td>
      <td>&nbsp;</td>
      <td><a href="userGroup.jsp"/>人群管理</td>
      <td>&nbsp;</td>
      <td><a href="function.jsp"/>功效管理</td>
      <td>&nbsp;</td>
      <td><a href="unit.jsp"/>度量单位管理</td>
      <td>&nbsp;</td>
      <td><a href="user.jsp"/>用户管理</td>
    </tr>
    </table>

	<h1>营养素管理</h1>
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
匿名用户添加了：&nbsp;
<%
            } else {
%>
<b><%= n.getAuthor().getNickname() %></b> 添加了：&nbsp;
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
    	<table border="0">
    	<tr><td>营养素类别：</td><td><input type="text" name="type"></td></tr>
    	<tr><td>营养素名：</td><td><input type="text" name="name"></td></tr>
    	<tr><td>能量含量(KJ/g)：</td><td><input type="text" name="energyContent"></td></tr>
    	<tr><td>介绍：</td><td><input type="text" name="introduction"></td></tr>
      	</table>
		<div><input type="submit" value="添加营养素" /></div>
    </form>
<%
    }
%>
  </body>
</html>
