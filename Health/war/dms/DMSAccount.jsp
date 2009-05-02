<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.dms.Admin" %>
<%@ page import="health.dms.AdminMan" %>
<%@ page import="health.PMF" %>
<%
	request.setCharacterEncoding("utf-8");
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>谷风 - DMS帐号管理</title>
<%
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    boolean needLogin = false;
    if (user == null || !AdminMan.auth(user, Admin.AUTH_DMS)) {
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
    <p>您没有权限管理DMS帐号，正在跳转至首页，请稍候...</p>

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
    
    <h1>DMS帐号管理</h1>
    
    <table border="1">
    <tr>
      <th>帐号ID</th>	
      <th>Google帐号</th>	
      <th>姓名</th>	
      <th>员工号</th>
      <th>添加时间</th>	
      <th>管理</th>	
      <th>删除</th>
    </tr>
<%
	PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = "select from " + Admin.class.getName() + " order by date desc range 0,100";
    List<Admin> admins = (List<Admin>) pm.newQuery(query).execute();
    for (Admin a : admins) {
%>
    <tr>
      <td><%= a.getID() %></td>	
      <td><%= a.getEmail() %></td>	
      <td><%= a.getName() %></td>
      <td><%= a.getNumber() %></td>
      <td><%= a.getDate().toString() %></td>
      <td><a href="changeDMS.jsp?email=<%= a.getEmail() %>">修改</a></td>
      <td><a href="/deleteDMS?email=<%= a.getEmail() %>">删除</a></td>
    </tr>
<%
    }
%>
    </table>
    
    <p><a href="addDMS.jsp">增加新DMS帐号</a></p>
    
<%
    }
%>
  </body>
</html>
