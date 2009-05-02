package health.taste;

import health.PMF;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;

public class TasteMan {
	
	/**
	 * add a new taste by name
	 * @param name
	 * @param date
	 * @param author
	 * @return true if successfully added, false otherwise
	 */
	public static boolean add(String name, Date date, User author) {
		
		// check whether this taste has been added
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Taste.class.getName();
	    List<Taste> tastes = (List<Taste>) pm.newQuery(query).execute();
		for (Taste a : tastes) if (a.getName().equals(name)) {
			pm.close();
			return false;
		}

		// add a new taste
		Taste taste = new Taste(name, author, new Date());
		pm.makePersistent(taste);
		pm.close();
		return true;
	}
	
	/**
	 * delete a taste by id
	 * @param id
	 * @return the taste deleted
	 */
	public static Taste delete(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Taste.class.getName() + 
	    				" where id == " + id;
	    List<Taste> tastes = (List<Taste>) pm.newQuery(query).execute();
	    Taste toDelete = tastes.get(0);
	    pm.deletePersistent(toDelete);
	    pm.close();
	    return toDelete;
	}
	
	/**
	 * change the name of a taste to newName
	 * @param id the id of the taste
	 * @param newName the taste's new name
	 * 
	 */
	public static void changeTaste(Long id, String newName) {
		// find the taste by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Taste.class.getName() + 
	    				" where id == " + id;
	    List<Taste> tastes = (List<Taste>) pm.newQuery(query).execute();
	    Taste toChange = tastes.get(0);
	    Taste toAdd = new Taste(newName, toChange.getAuthor(), toChange.getDate());
	    // delete the taste and add a taste with the new name
	    pm.deletePersistent(toChange);
	    pm.makePersistent(toAdd);
	    pm.close();
	}
	
	/**
	 * get a taste by id
	 * @param id
	 * @return
	 */
	public static Taste getTaste(Long id) {
		// find the taste by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Taste.class.getName() + 
	    				" where id == " + id;
	    List<Taste> tastes = (List<Taste>) pm.newQuery(query).execute();
	    Taste ret = tastes.get(0);
	    pm.close();
	    return ret;
	}
}
