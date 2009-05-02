package health;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Nutriment { // Nutriment object
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long ID; // object ID, primary key, can not be Short or Integer

	@Persistent
	private Short Type; // type, 0 for macronutrient, 1 for micronutrient

	@Persistent
	private String Name; // name

	@Persistent
	private Float EnergyContent; // energy (KJ/g)
	
	@Persistent
	private String Introduction; // introduction

	@Persistent
	private User author; // contributor
	
	@Persistent
	private Date date; // date and time of addition
	
	public Nutriment(Short Type, String Name, Float EnergyContent,
			String Introduction, User author, Date date) {
		this.Type = Type;
		this.Name = Name;
		this.EnergyContent = EnergyContent;
		this.Introduction = Introduction;
		this.author = author;
		this.date = date;
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
	 * @return the Type
	 */
	public Short getType() {
		return Type;
	}

	/**
	 * @param type the Type to set
	 */
	public void setType(Short type) {
		Type = type;
	}

	/**
	 * @return the Name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name the Name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the EnergyContent
	 */
	public Float getEnergyContent() {
		return EnergyContent;
	}

	/**
	 * @param energyContent the EnergyContent to set
	 */
	public void setEnergyContent(Float energyContent) {
		EnergyContent = energyContent;
	}

	/**
	 * @return the Introduction
	 */
	public String getIntroduction() {
		return Introduction;
	}

	/**
	 * @param introduction the Introduction to set
	 */
	public void setIntroduction(String introduction) {
		Introduction = introduction;
	}
	
	
}
