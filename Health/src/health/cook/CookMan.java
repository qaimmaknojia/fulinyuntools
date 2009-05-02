package health.cook;

import health.PMF;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;

public class CookMan {
	
	/**
	 * add a new cooking method by name
	 * @param name
	 * @param date
	 * @param author
	 * @return true if successfully added, false otherwise
	 */
	public static boolean add(String name, Date date, User author) {
		
		// check whether this cooking method has been added
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Cook.class.getName();
	    List<Cook> cooks = (List<Cook>) pm.newQuery(query).execute();
		for (Cook a : cooks) if (a.getName().equals(name)) {
			pm.close();
			return false;
		}

		// add a new cooking method
		Cook cook = new Cook(name, author, new Date());
		pm.makePersistent(cook);
		pm.close();
		return true;
	}
	
	/**
	 * delete a cooking method by id
	 * @param id
	 * @return the cooking method deleted
	 */
	public static Cook delete(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Cook.class.getName() + 
	    				" where ID == " + id;
	    List<Cook> cooks = (List<Cook>) pm.newQuery(query).execute();
	    Cook toDelete = cooks.get(0);
	    pm.deletePersistent(toDelete);
	    pm.close();
	    return toDelete;
	}
	
	/**
	 * change the name of a cooking method to newName
	 * @param id the id of the cooking method
	 * @param newName the cooking method's new name
	 * 
	 */
	public static void changeCook(Long id, String newName) {
		// find the cooking method by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Cook.class.getName() + 
	    				" where ID == " + id;
	    List<Cook> cooks = (List<Cook>) pm.newQuery(query).execute();
	    Cook toChange = cooks.get(0);
	    Cook toAdd = new Cook(newName, toChange.getAuthor(), toChange.getDate());
	    // delete the cooking method and add a cooking method with the new name
	    pm.deletePersistent(toChange);
	    pm.makePersistent(toAdd);
	    pm.close();
	}
	
	/**
	 * get a cooking method by id
	 * @param id
	 * @return
	 */
	public static Cook getCook(Long id) {
		// find the cooking method by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Cook.class.getName() + 
	    				" where ID == " + id;
	    List<Cook> cooks = (List<Cook>) pm.newQuery(query).execute();
	    Cook ret = cooks.get(0);
	    pm.close();
	    return ret;
	}
}
