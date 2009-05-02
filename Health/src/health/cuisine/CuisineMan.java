package health.cuisine;

import health.PMF;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;

public class CuisineMan {
	
	/**
	 * add a new cuisine by name
	 * @param name
	 * @param date
	 * @param author
	 * @return true if successfully added, false otherwise
	 */
	public static boolean add(String name, Date date, User author) {
		
		// check whether this cuisine has been added
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Cuisine.class.getName();
	    List<Cuisine> cuisines = (List<Cuisine>) pm.newQuery(query).execute();
		for (Cuisine a : cuisines) if (a.getName().equals(name)) {
			pm.close();
			return false;
		}

		// add a new cuisine
		Cuisine cuisine = new Cuisine(name, author, new Date());
		pm.makePersistent(cuisine);
		pm.close();
		return true;
	}
	
	/**
	 * delete a cuisine by id
	 * @param id
	 * @return the cuisine deleted
	 */
	public static Cuisine delete(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Cuisine.class.getName() + 
	    				" where ID == " + id;
	    List<Cuisine> cuisines = (List<Cuisine>) pm.newQuery(query).execute();
	    Cuisine toDelete = cuisines.get(0);
	    pm.deletePersistent(toDelete);
	    pm.close();
	    return toDelete;
	}
	
	/**
	 * change the name of a cuisine to newName
	 * @param id the id of the cuisine
	 * @param newName the cuisine's new name
	 * 
	 */
	public static void changeCuisine(Long id, String newName) {
		// find the cuisine by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Cuisine.class.getName() + 
	    				" where ID == " + id;
	    List<Cuisine> cuisines = (List<Cuisine>) pm.newQuery(query).execute();
	    Cuisine toChange = cuisines.get(0);
	    Cuisine toAdd = new Cuisine(newName, toChange.getAuthor(), toChange.getDate());
	    // delete the cuisine and add a cuisine with the new name
	    pm.deletePersistent(toChange);
	    pm.makePersistent(toAdd);
	    pm.close();
	}
	
	/**
	 * get a cuisine by id
	 * @param id
	 * @return
	 */
	public static Cuisine getCuisine(Long id) {
		// find the cuisine by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Cuisine.class.getName() + 
	    				" where ID == " + id;
	    List<Cuisine> cuisines = (List<Cuisine>) pm.newQuery(query).execute();
	    Cuisine ret = cuisines.get(0);
	    pm.close();
	    return ret;
	}
}
