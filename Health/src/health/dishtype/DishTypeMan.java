package health.dishtype;

import health.PMF;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;

public class DishTypeMan {
	
	/**
	 * add a new dish type by name
	 * @param name
	 * @param date
	 * @param author
	 * @return true if successfully added, false otherwise
	 */
	public static boolean add(String name, Date date, 
			User author) {
		
		// check whether this dish type has been added
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + DishType.class.getName();
	    List<DishType> dishTypes = (List<DishType>) pm.newQuery(query).execute();
		for (DishType a : dishTypes) if (a.getName().equals(name)) {
			pm.close();
			return false;
		}

		// add a new dish type
		DishType dishType = new DishType(name, author, new Date());
		pm.makePersistent(dishType);
		pm.close();
		return true;
	}
	
	/**
	 * delete a dish type by id
	 * @param id
	 * @return the dishType deleted
	 */
	public static DishType delete(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + DishType.class.getName() + 
	    				" where ID == " + id;
	    List<DishType> dishTypes = (List<DishType>) pm.newQuery(query).execute();
	    DishType toDelete = dishTypes.get(0);
	    pm.deletePersistent(toDelete);
	    pm.close();
	    return toDelete;
	}
	
	/**
	 * change the name of a dish type to newName
	 * @param id the id of the dish type
	 * @param newName the dish type's new name
	 * 
	 */
	public static void changeDishType(Long id, String newName) {
		// find the dish type by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + DishType.class.getName() + 
	    				" where ID == " + id;
	    List<DishType> dishTypes = (List<DishType>) pm.newQuery(query).execute();
	    DishType toChange = dishTypes.get(0);
	    DishType toAdd = new DishType(newName, toChange.getAuthor(), toChange.getDate());
	    // delete the dish type and add a dish type with the new name
	    pm.deletePersistent(toChange);
	    pm.makePersistent(toAdd);
	    pm.close();
	}
	
	/**
	 * get a DishType by id
	 * @param id
	 * @return
	 */
	public static DishType getDishType(Long id) {
		// find the dish type by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + DishType.class.getName() + 
	    				" where ID == " + id;
	    List<DishType> dishTypes = (List<DishType>) pm.newQuery(query).execute();
	    DishType ret = dishTypes.get(0);
	    pm.close();
	    return ret;
	}
}
