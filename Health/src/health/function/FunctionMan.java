package health.function;

import health.PMF;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;

public class FunctionMan {
	
	/**
	 * add a new function by name
	 * @param name
	 * @param date
	 * @param author
	 * @return true if successfully added, false otherwise
	 */
	public static boolean add(String name, String introduction, Date date, User author) {
		
		// check whether this function has been added
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Function.class.getName();
	    List<Function> functions = (List<Function>) pm.newQuery(query).execute();
		for (Function a : functions) if (a.getName().equals(name)) {
			pm.close();
			return false;
		}

		// add a new cooking method
		Function function = new Function(name, introduction, author, new Date());
		pm.makePersistent(function);
		pm.close();
		return true;
	}
	
	/**
	 * delete a cooking method by id
	 * @param id
	 * @return the cooking method deleted
	 */
	public static Function delete(Long id) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Function.class.getName() + 
	    				" where ID == " + id;
	    List<Function> functions = (List<Function>) pm.newQuery(query).execute();
	    Function toDelete = functions.get(0);
	    pm.deletePersistent(toDelete);
	    pm.close();
	    return toDelete;
	}
	
	/**
	 * change the name and introduction of a function to newName 
	 * and newIntroduction
	 * @param id the id of the function
	 * @param newName the function's new name
	 * @param newIntroduction the function's new introduction
	 */
	public static void changeFunction(Long id, String newName, 
			String newIntroduction) {
		// find the function by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Function.class.getName() + 
	    				" where ID == " + id;
	    List<Function> functions = (List<Function>) pm.newQuery(query).execute();
	    Function toChange = functions.get(0);
	    Function toAdd = new Function(newName, newIntroduction, 
	    		toChange.getAuthor(), toChange.getDate());
	    // delete the function and add a function with the new name and introduction
	    pm.deletePersistent(toChange);
	    pm.makePersistent(toAdd);
	    pm.close();
	}
	
	/**
	 * get a function by id
	 * @param id
	 * @return
	 */
	public static Function getFunction(Long id) {
		// find the cooking method by id
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Function.class.getName() + 
	    				" where ID == " + id;
	    List<Function> functions = (List<Function>) pm.newQuery(query).execute();
	    Function ret = functions.get(0);
	    pm.close();
	    return ret;
	}
}
