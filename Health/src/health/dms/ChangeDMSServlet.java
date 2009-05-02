package health.dms;

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

public class ChangeDMSServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(ChangeDMSServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// set request charset encoding
		req.setCharacterEncoding("utf-8");
		
		// get current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (AdminMan.auth(user, Admin.AUTH_DMS)) {
			// get admin information
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			String newTel = req.getParameter("newTel");
			long auth = 0;
			if (req.getParameter("DMS") != null) auth |= Admin.AUTH_DMS;
			if (req.getParameter("dish") != null) auth |= Admin.AUTH_DISH;
			if (req.getParameter("food") != null) auth |= Admin.AUTH_FOOD;
			if (req.getParameter("function") != null) auth |= Admin.AUTH_FUNCTION;
			if (req.getParameter("nutriment") != null) auth |= Admin.AUTH_NUTRIMENT;
			if (req.getParameter("unit") != null) auth |= Admin.AUTH_UNIT;
			if (req.getParameter("user") != null) auth |= Admin.AUTH_USER;
			if (req.getParameter("userGroup") != null) auth |= Admin.AUTH_USERGROUP;
			AdminMan.changeDMS(name, email, newTel, auth);
		}
		resp.sendRedirect("/dms/DMSAccount.jsp");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		doPost(req, resp);
	}
}
