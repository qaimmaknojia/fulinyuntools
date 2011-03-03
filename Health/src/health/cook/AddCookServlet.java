package health.cook;

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

public class AddCookServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(AddCookServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// set request charset encoding
		req.setCharacterEncoding("utf-8");
		
		// get current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (AdminMan.auth(user, Admin.AUTH_DISH)) {
			// get cooking method information
			String name = req.getParameter("name");
			CookMan.add(name, new Date(), user);
		}
		resp.sendRedirect("/cook/cook.jsp");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		doPost(req, resp);
	}
}