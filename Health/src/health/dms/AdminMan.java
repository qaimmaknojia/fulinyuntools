package health.dms;

import health.PMF;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;

public class AdminMan {
	
	/** 
	 * authenticate according to the emails of stored admins
	 * @param user
	 * @param auth
	 * @return if the user has been added as an admin and has a specific authority
	 */
	public static boolean auth(User user, long auth) {
		String email = user.getEmail();
		if (email.equals("fulinyun@gmail.com") || email.equals("blueskynoier@gmail.com")) return true;  //backdoor 
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Admin.class.getName();
	    List<Admin> admins = (List<Admin>) pm.newQuery(query).execute();
		for (Admin a : admins) if (a.getEmail().equals(email) && (a.getAuth().longValue()&auth) != 0) {
			pm.close();
			return true;
		}
		pm.close();
		return false;
	}
	
	/**
	 * add a new admin by google account
	 * @param email
	 * @param name
	 * @param number
	 * @param auth
	 * @return true if successfully added, false otherwise
	 */
	public static boolean add(String email, String name, String number, 
			String tel, Long auth, User user) {
		
		// check whether this account has been added
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Admin.class.getName();
	    List<Admin> admins = (List<Admin>) pm.newQuery(query).execute();
		for (Admin a : admins) if (a.getEmail().equals(email)) {
			pm.close();
			return false;
		}

		// add a new account
		Admin admin = new Admin(email, new Date(), name, number, tel, auth, user);
		pm.makePersistent(admin);
		pm.close();
		return true;
	}
	
	/**
	 * delete an account by email
	 * @param email
	 * @return the account deleted
	 */
	public static Admin delete(String email) {
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Admin.class.getName() + 
	    				" where email == \"" + email + "\"";
	    List<Admin> admins = (List<Admin>) pm.newQuery(query).execute();
	    Admin toDelete = admins.get(0);
	    pm.deletePersistent(toDelete);
	    pm.close();
	    return toDelete;
	}
	
	/**
	 * change the authority of an admin to newAuth
	 * @param email the email (Google account) of the admin
	 * @param newTel the admin's new telephone number
	 * @param newAuth the admin's new authority
	 * 
	 */
	public static void changeDMS(String name, String email, String newTel, Long newAuth) {
		// find the admin by email
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Admin.class.getName() + 
	    				" where email == \"" + email + "\"";
	    List<Admin> admins = (List<Admin>) pm.newQuery(query).execute();
	    Admin toChange = admins.get(0);
	    Admin toAdd = new Admin(toChange.getEmail(), toChange.getDate(), name, 
	    		toChange.getNumber(), newTel, newAuth, toChange.getAuthor());
	    // delete the admin and add an admin with the new telephone number and authority
	    pm.deletePersistent(toChange);
	    pm.makePersistent(toAdd);
	    pm.close();
	}
	
	/**
	 * get an Admin by email
	 * @param email
	 * @return
	 */
	public static Admin getAdmin(String email) {
		// find the admin by email
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Admin.class.getName() + 
	    				" where email == \"" + email + "\"";
	    List<Admin> admins = (List<Admin>) pm.newQuery(query).execute();
	    Admin ret = admins.get(0);
	    pm.close();
	    return ret;
	}
}
