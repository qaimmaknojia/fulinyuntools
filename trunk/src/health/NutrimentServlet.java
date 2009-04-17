package health;

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

public class NutrimentServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(NutrimentServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// set request charset encoding
		req.setCharacterEncoding("gb2312");
		
		// get current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// get Nutriment information
		Short type = Short.parseShort(req.getParameter("type"));
		String name = req.getParameter("name");
		Float energyContent = Float.parseFloat(req.getParameter("energyContent"));
		String introduction = req.getParameter("introduction");
		
		Date date = new Date();
		Nutriment nutriment = new Nutriment(type, name, energyContent, introduction, user, new Date());

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(nutriment);
		} finally {
			pm.close();
		}

		resp.sendRedirect("/nutriment.jsp");
	}

}
