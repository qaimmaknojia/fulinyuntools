package health.dishtype;

import health.dms.Admin;
import health.dms.AdminMan;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class DeleteDishTypeServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(DeleteDishTypeServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// set request charset encoding
		req.setCharacterEncoding("utf-8");
		
		// get current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (AdminMan.auth(user, Admin.AUTH_DISH)) {
			// get admin email
			Long id = Long.parseLong(req.getParameter("id"));
			DishTypeMan.delete(id);
		}
		resp.sendRedirect("/dishtype/dishType.jsp");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		doPost(req, resp);
	}
}
