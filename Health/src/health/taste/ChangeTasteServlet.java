package health.taste;

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

public class ChangeTasteServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(ChangeTasteServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// set request charset encoding
		req.setCharacterEncoding("utf-8");
		
		// get current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (AdminMan.auth(user, Admin.AUTH_DISH)) {
			// get taste information
			Long id = Long.parseLong(req.getParameter("id"));
			String name = req.getParameter("name");
			TasteMan.changeTaste(id, name);
		}
		resp.sendRedirect("/taste/taste.jsp");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		doPost(req, resp);
	}
}
