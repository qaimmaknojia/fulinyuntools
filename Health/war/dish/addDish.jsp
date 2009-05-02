<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.dms.AdminMan" %>
<%@ page import="health.dms.Admin" %>
<%@ page import="health.dishtype.DishType" %>
<%@ page import="health.PMF %>
<%@ page import="health.cook.Cook %>
<%@ page import="health.taste.Taste %>
<%@ page import="health.cuisine.Cuisine %>
<%
	request.setCharacterEncoding("utf-8");
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>谷风 - 新增菜</title>
<%
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    boolean needLogin = false;
    if (user == null || !AdminMan.auth(user, Admin.AUTH_DISH)) {
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
    <p>您没有权限新增菜，正在跳转至首页，请稍候...</p>
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
    
    <h1>新增菜</h1>
    
    <form action="/addDish" method="post">
    <table>
    <tr>
      <td>菜名</td>	
      <td><input type="text" name="name" /></td> 
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>类别</td>	
      <td>
      <select name="type">
<%
        PersistenceManager pm = PMF.get().getPersistenceManager();
        String query = "select from " + DishType.class.getName() +
            " order by date desc range 0,100";
        List<DishType> dishTypes = (List<DishType>) pm.newQuery(query).execute();
        for (DishType a : dishTypes) {
%>
      <option value="<%= a.getID() %>"><%= a.getName() %></option>
<%
        }
%>
      </select>
      </td> 
      <td> </td>
    </tr>
    <tr>
      <td>价格</td>
      <td><input type="text" name="price" />元</td>
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>学习难度</td>
      <td>
        <select name="difficulty">
        <option value="0">易</option>
        <option value="1">中等</option>
        <option value="2">难</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>烹饪时间</td>
      <td><input type="text" name="price" />分钟</td>
      <td>*不能为空</td>
    </tr>
    <tr>
      <td>烹饪方式</td>
      <td>
      <select name="cook">
<%
	query = "select from " + Function.class.getName() +
            " order by date desc range 0,100";
        List<Function> cooks = (List<Function>) pm.newQuery(query).execute();
        for (Function a : cooks) {
%>
      <option value="<%= a.getID() %>"><%= a.getName() %></option>
<%
        }
%>
      </select>
      </td> 
      <td> </td>
    </tr>
    <tr>
      <td>适宜餐顿</td>
      <td>
        <select name="fitMeal">
        <option value="0">早餐</option>
        <option value="1">正餐</option>
        <option value="2">零食</option>
        <option value="3">都可以</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>口味</td>
      <td>
      <select name="taste">
<%
        query = "select from " + Taste.class.getName() +
            " order by date desc range 0,100";
        List<Taste> tastes = (List<Taste>) pm.newQuery(query).execute();
        for (Taste a : tastes) {
%>
      <option value="<%= a.getID() %>"><%= a.getName() %></option>
<%
        }
%>
      </select>
      </td> 
      <td> </td>
    </tr>
    <tr>
      <td>能量</td>
      <td><input type="text" name="energy" />KJ</td>
      <td> </td>
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
