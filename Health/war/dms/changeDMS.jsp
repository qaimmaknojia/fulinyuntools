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
    <title>谷风 - 修改DMS帐号 - <%= request.getParameter("email") %></title><%
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

<script type="text/javascript">
<!--
function check() {
//  if(document.form.email.value.replace(/(^\s+)|\s+$/g,"") == "") {
    alert("您必须输入Google帐号！");
//    return false;
//  }

//  if(document.form.number.value.replace(/(^\s+)|\s+$/g,"") == "") {
//    alert("您必须输入员工号！");
//    return false;
//  }
  
  return true;
}
//-->
</script>

  </head>
  
  <body>
<%
	if (needLogin) {
%>
  <p>您没有权限修改DMS帐号，正在跳转至首页，请稍候...</p>
<%
	} else {  
        Admin admin = AdminMan.getAdmin(request.getParameter("email"));
        long auth = admin.getAuth();
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
    
    <h1>修改DMS帐号 <%= request.getParameter("email") %></h1>
    
    <form action="/changeDMS" method="post" onsubmit="reutrn check();">
    <table>
    <tr>
      <td>员工姓名</td>	
      <td><input type="text" name="name" value="<%= admin.getName() %>"></td> 
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>Google帐号</td>	
      <td><input type="text" name="email" value="<%= admin.getEmail() %>"></td> 
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>员工号</td>
      <td><input type="text" name="number" value="<%= admin.getNumber() %>"></td>
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>电话</td>
      <td><input type="text" name="tel" value="<%= admin.getTel() %>"></td>
    </tr>
    <tr><td colspan="3"> </td></tr>
    <tr>
      <td>操作权限</td>
      <td colspan="2">
        <table>
          <tr>
            <td><input type="checkbox" name="DMS"
            <%= (auth&Admin.AUTH_DMS)==0?"":"checked=\"true\"" %>>可以管理DMS账号</td>
            <td><input type="checkbox" name="dish"
            <%= (auth&Admin.AUTH_DISH)==0?"":"checked=\"true\"" %>>可以管理菜</td>
          </tr>
          <tr>
            <td><input type="checkbox" name="food"
            <%= (auth&Admin.AUTH_FOOD)==0?"":"checked=\"true\"" %>>可以管理食料</td>
            <td><input type="checkbox" name="nutriment"
            <%= (auth&Admin.AUTH_NUTRIMENT)==0?"":"checked=\"true\"" %>>可以管理营养素</td>
          </tr>
          <tr>
            <td><input type="checkbox" name="userGroup"
            <%= (auth&Admin.AUTH_USERGROUP)==0?"":"checked=\"true\"" %>>可以管理人群</td>
            <td><input type="checkbox" name="function"
            <%= (auth&Admin.AUTH_FUNCTION)==0?"":"checked=\"true\"" %>>可以管理功效</td>
          </tr>
          <tr>
            <td><input type="checkbox" name="unit"
            <%= (auth&Admin.AUTH_UNIT)==0?"":"checked=\"true\"" %>>可以管理度量单位</td>
            <td><input type="checkbox" name="user"
            <%= (auth&Admin.AUTH_USER)==0?"":"checked=\"true\"" %>>可以管理用户</td>
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
