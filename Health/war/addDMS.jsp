<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.AdminMan" %>
<%@ page import="health.Admin" %>
<%
request.setCharacterEncoding("utf-8");
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>谷风 - 新增DMS帐号</title>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    boolean needLogin = false;
    if (user == null || !AdminMan.auth(user, Admin.AUTH_DMS)) {
    needLogin = true;
%>
    <meta HTTP-EQUIV="REFRESH" content="0; url=index.jsp">
<%  
    }
%>

<script type="text/javascript">
function check() {
  if(document.form.email.value.replace(/(^\s+)|\s+$/g,"") == "") {
    alert("您必须输入Google帐号！");
    return false;
  }

  if(document.form.number.value.replace(/(^\s+)|\s+$/g,"") == "") {
    alert("您必须输入员工号！");
    return false;
  }
  
  return true;
}
</script>

  </head>
  <body>
<%
    if (needLogin) {
%>  
    <p>您没有权限新增DMS帐号，正在跳转至首页，请稍候...</p>
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
    
    <h1>新增DMS帐号</h1>
    
    <form action="/addDMS" method="post" onsubmit="reutrn check();">
    <table>
    <tr>
      <td>员工姓名</td>	
      <td><input type="text" name="name" /></td> 
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>Google帐号</td>	
      <td><input type="text" name="email" /></td> 
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>员工号</td>
      <td><input type="text" name="number" /></td>
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>电话</td>
      <td><input type="text" name="tel" /></td>
    </tr>
    <tr>
      <td>操作权限</td>
      <td colspan="2">
        <table>
          <tr>
            <td><input type="checkbox" name="DMS" />可以管理DMS账号</td>
            <td><input type="checkbox" name="dish" />可以管理菜</td>
          </tr>
          <tr>
            <td><input type="checkbox" name="food" />可以管理食料</td>
            <td><input type="checkbox" name="nutriment" />可以管理营养素</td>
          </tr>
          <tr>
            <td><input type="checkbox" name="userGroup" />可以管理人群</td>
            <td><input type="checkbox" name="function" />可以管理功效</td>
          </tr>
          <tr>
            <td><input type="checkbox" name="unit" />可以管理度量单位</td>
            <td><input type="checkbox" name="user" />可以管理用户</td>
          </tr>
        </table>
      </td>
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
