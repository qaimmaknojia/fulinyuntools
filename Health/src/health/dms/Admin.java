package health.dms;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Admin { // Admin object
	
	public static long AUTH_DMS = 1; // DMS account management
	public static long AUTH_DISH = (1<<1); // dish management
	public static long AUTH_FOOD = (1<<2); // food management
	public static long AUTH_NUTRIMENT = (1<<3); // nutriment management
	public static long AUTH_USERGROUP = (1<<4); // user group management
	public static long AUTH_FUNCTION = (1<<5); // function management
	public static long AUTH_UNIT = (1<<6); // unit management
	public static long AUTH_USER = (1<<7); // user management
	public static long AUTH_ANY = (1<<8-1); // any of the above authorities
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long ID; // ID, primary key, can not be Short or Integer

	@Persistent
	private String email; // Google account
	
	@Persistent
	private Date date; // date and time of adition
	
	@Persistent
	private String name; // name
	
	@Persistent
	private String number; // number
	
	@Persistent
	private String tel; // telephone number
	
	@Persistent
	private Long auth; // authority
	
	@Persistent
	private User author; // who add this admin
	
	public Admin(String email, Date date, String name, String number, 
			String tel, Long auth, User author) {
		this.email = email;
		this.date = date;
		this.name = name;
		this.number = number;
		this.tel = tel;
		this.auth = auth;
		this.author = author;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the ID
	 */
	public Long getID() {
		return ID;
	}

	/**
	 * @param id the ID to set
	 */
	public void setID(Long id) {
		ID = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the auth
	 */
	public Long getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(Long auth) {
		this.auth = auth;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

}
