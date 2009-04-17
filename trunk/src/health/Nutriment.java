package health;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Nutriment { // 营养素对象
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long ID; // 营养素ID, primary key不能是Short或者Integer

	@Persistent
	private Short Type; // 营养素类别

	@Persistent
	private String Name; // 营养素名

	@Persistent
	private Float EnergyContent; // 能量含量(KJ/g)
	
	@Persistent
	private String Introduction; // 介绍

	@Persistent
	private User author; // 添加者
	
	@Persistent
	private Date date; //添加时间
	
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
