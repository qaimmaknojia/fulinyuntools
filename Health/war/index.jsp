<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.dms.AdminMan" %>
<%@ page import="health.dms.Admin" %>
<%
request.setCharacterEncoding("utf-8");
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>谷风 - 首页</title>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    boolean needLogin = false;
    if (user == null || !AdminMan.auth(user, Admin.AUTH_ANY)) {
        needLogin = true;
%>
    <meta HTTP-EQUIV="REFRESH" content="0; url=login.jsp">
<%  
    }
%>    
  </head>
  
  <body>
<%
    if (needLogin) {
%>  
    <p>您还没有登录，正在跳转至登录页，请稍候...</p>
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
    
    <h1>系统信息</h1>
	
    <table>
      <tr>
        <td colspan="2" style="font-weight:bold;">统计</td>        
      </tr>
      <tr>
        <td>站点名称：</td>	<td>DMS系统 - 后台数据维护</td>
      </tr>
      <tr>
        <td>站点URL：</td>	<td>http://dms.jkmm.com/</td>
      </tr>
      <tr>
        <td>当前用户：</td>	<td><%= user.getNickname() %>&nbsp;<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">登出</a></td>
      </tr>
      <tr>
        <td>管理权限：</td>	<td>后台数据随便改</td>
      </tr>
      <tr>
        <td>统计信息：</td>	<td>暂无</td>
      </tr>
    </table>
<%  
    }
%>
  </body>
</html>
