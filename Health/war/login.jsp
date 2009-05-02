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
    <title>谷风 - 登陆</title>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    boolean redirect = false;
    if (user != null && AdminMan.auth(user, Admin.AUTH_ANY)) {
    redirect = true;
%>
    <meta HTTP-EQUIV="REFRESH" content="0; url=index.jsp">
<%  
    }
%>    
  </head>

  <body>
  
    <p>DMS (Data Management System) 是健康密码的内部管理系统</p>
<%  
    if (!redirect) {
%>
    <p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>">用Google帐号登录</a></p>
<%
    } else {  
%>
	<p>您好，<%= user.getNickname() %>！正在跳转，请稍候...</p>
<%
    }
%>
    <p>版权所有 2009 上海健康密码网络科技有限公司<br />
       Copyright&copy; 2009 Tenhao Inc. All Rights Reserved.
    </p>
    
  </body>
</html>
