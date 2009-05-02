<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="health.dms.Admin" %>
<%@ page import="health.dms.AdminMan" %>
<%@ page import="health.PMF" %>
<%@ page import="health.dish.Dish" %>
<%
	request.setCharacterEncoding("utf-8");
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>谷风 - 菜管理</title>
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
    <p>您没有权限管理菜，正在跳转至首页，请稍候...</p>
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
    
    <h1>菜管理</h1>
    <table border="0">
    <tr><td>
    <form action="dish.jsp" method="post">
    <table border="0">
    <tr>
    <td>
    <select name="queryType">
	    <option value="">【选择查询项】</option>
	    <option value="ID">菜ID</option>
	    <option value="name">菜名</option>
	    <option value="food">食料</option>
	    <option value="userGroup">相关人群</option>
    </select>
    </td>
    <td><input type="text" name="keyword" /></td>
    <td><input type="submit" value="查询"/></td>
    </tr>
    </table>
    </form>
    </td>
    <td>
    <form action="advancedDishQuery.jsp" method="post">
    <input type="submit" value="高级查询" />
    </form>
    </td>
    </tr>
    </table>
    
    <table border="1">
    <tr>
      <th>ID</th>
      <th>菜名</th>	
      <th>菜系</th>	
      <th>类别</th>
      <th>口味</th>	
      <th>热能</th>	
      <th>烹饪时间</th>
      <th>烹饪方式</th>
      <th>餐顿</th>
      <th>价格</th>
      <th>难度</th>
      <th>添加时间</th>
      <th>管理</th>
      <th>删除</th>
    </tr>
<%
	String queryType = request.getParameter("queryType");
	String keyword = request.getParameter("keyword");
    PersistenceManager pm = PMF.get().getPersistenceManager();
    String query = null;
    if (queryType == null)
    	query = "select from " + Dish.class.getName() + 
    	" order by date desc range 0,100";
    else if (queryType.equals("ID")) 
    	query = "select from " + Dish.class.getName() + 
    	" where ID == \"" + keyword + "\"";
    else if (queryType.equals("name"))
    	query = "select from " + Dish.class.getName() + 
    	" where Name == \"" + keyword + "\"";
    else 
    	query = "select from " + Dish.class.getName() + 
    	" order by date desc range 0,100";
    List<Dish> dishes = (List<Dish>) pm.newQuery(query).execute();
    for (Dish a : dishes) {
%>
    <tr>
      <td><%= a.getID() %></td>
      <td><%= a.getName() %></td>
      <td><%= a.getCuisine().getName() %></td>
      <td><%= a.getType().getName() %></td>
      <td><%= a.getTaste().getName() %></td>
      <td><%= a.getEnergyContent() %> KJ/g</td>
      <td><%= a.getTime() %>分钟</td>
      <td><%= a.getPreparation() %></td>
      <td><%= a.getFitMealString() %></td>
      <td><%= a.getPrice() %>元</td>
      <td><%= a.getDifficultyString() %></td>
      <td><%= a.getDate().toString() %></td>
      <td><a href="changeDish.jsp?id=<%= a.getID() %>">修改</a></td>
      <td><a href="/deleteDish?id=<%= a.getID() %>">删除</a></td>
    </tr>
<%
    }
%>
    </table>
    
    <p><a href="addDish.jsp">增加新菜</a></p>
    
<%
    }
%>
  </body>
</html>
