<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.dms.AdminMan" %>
<%@ page import="health.dms.Admin" %>
<%@ page import="health.taste.Taste" %>
<%@ page import="health.taste.TasteMan" %>
<%
	request.setCharacterEncoding("utf-8");
	Long id = Long.parseLong(request.getParameter("id"));
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>谷风 - 修改口味 - <%= id %></title>
<%
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    boolean needLogin = false;
    if (user == null || !AdminMan.auth(user, Admin.AUTH_DISH)) {
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
    <p>您没有权限修改口味，正在跳转至首页，请稍候...</p>
<%
	} else {  
        Taste taste = TasteMan.getTaste(id);
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
    
    <h1>修改口味 <%= id %></h1>
    
    <form action="/changeTaste?id=<%= id %>" method="post">
    <table>
    <tr>
      <td>口味名</td>	
      <td><input type="text" name="name" value="<%= taste.getName() %>" /></td> 
      <td>*不能为空</td>
    </tr>
    <tr>
    <td><input type="submit" value="确定" /></td>
    <td> </td>
    <td><input type="reset" value="重置" /></td>
    </tr>
    </table>
    </form>
<%
    }
%>
  </body>
</html>