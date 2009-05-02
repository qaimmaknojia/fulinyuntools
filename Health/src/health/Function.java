package health;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Function { // Function object
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long ID; // object ID, primary key, can not be Short or Integer

	@Persistent
	private String Name; // name

	@Persistent
	private String Introduction; // introduction

	@Persistent
	private User author; // contributor
	
	@Persistent
	private Date date; // date and time of addition

	/**
	 * @return the iD
	 */
	public Long getID() {
		return ID;
	}

	/**
	 * @param id the iD to set
	 */
	public void setID(Long id) {
		ID = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return Introduction;
	}

	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		Introduction = introduction;
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
	 * @param name
	 * @param introduction
	 * @param author
	 * @param date
	 */
	public Function(String name, String introduction, User author, Date date) {
		Name = name;
		Introduction = introduction;
		this.author = author;
		this.date = date;
	}
	
}
