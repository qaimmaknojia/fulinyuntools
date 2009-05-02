package health;

import health.Cuisine;
import health.Function;
import health.Taste;
import health.cook.Cook;
import health.dishtype.DishType;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Dish { // Dish object
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long ID; // object ID, primary key, can not be Short or Integer

	@Persistent
	private DishType Type; // dish type

	@Persistent
	private String Name; // name

	@Persistent
	private String Alias; // alias
	
	@Persistent
	private Cuisine cuisine; // cuisine
	
	@Persistent
	private Taste taste; // taste id
	
	@Persistent
	private Cook CookType; // cook type id
	
	@Persistent
	private Short Seasons; // season combination
	
	@Persistent
	private String preparation; // preparation method
	
	@Persistent
	private Float Price; // price (CNY)
	
	@Persistent
	private Integer Time; // cooking time (min)
	
	@Persistent
	private Short Difficulty; // hardness of learning
	
	@Persistent
	private Short FitMeal; // meal fit
	
	@Persistent
	private Float EnergyContent; // energy (KJ/g)
	
	@Persistent
	private String Introduction; // introduction

	@Persistent
	private User author; // contributor
	
	@Persistent
	private Date date; // date and time of addition
	
	@Persistent
	private Function function; // function

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
	 * @return the type
	 */
	public DishType getType() {
		return Type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DishType type) {
		Type = type;
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
	 * @return the alias
	 */
	public String getAlias() {
		return Alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		Alias = alias;
	}

	/**
	 * @return the cuisine
	 */
	public Cuisine getCuisine() {
		return cuisine;
	}

	/**
	 * @param cuisine the cuisine to set
	 */
	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}

	/**
	 * @return the taste
	 */
	public Taste getTaste() {
		return taste;
	}

	/**
	 * @param taste the taste to set
	 */
	public void setTaste(Taste taste) {
		this.taste = taste;
	}

	/**
	 * @return the cookType
	 */
	public Cook getCookType() {
		return CookType;
	}

	/**
	 * @param cookType the cookType to set
	 */
	public void setCookType(Cook cookType) {
		CookType = cookType;
	}

	/**
	 * @return the seasons
	 */
	public Short getSeasons() {
		return Seasons;
	}

	/**
	 * @return the string representation of the season combination
	 */
	public String getSeasonsString() {
		StringBuilder sb = new StringBuilder();
		if ((Seasons&(1<<3)) != 0) sb.append("春");
		if ((Seasons&(1<<2)) != 0) sb.append("夏");
		if ((Seasons&(1<<2)) != 0) sb.append("秋");
		if ((Seasons&(1<<2)) != 0) sb.append("冬");
		String ret = sb.toString();
		if (ret.length() == 0) return "未知";
		else return ret;
	}
	
	/**
	 * @param seasons the seasons to set
	 */
	public void setSeasons(Short seasons) {
		Seasons = seasons;
	}

	/**
	 * @return the preparation
	 */
	public String getPreparation() {
		return preparation;
	}

	/**
	 * @param preparation the preparation to set
	 */
	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

	/**
	 * @return the price
	 */
	public Float getPrice() {
		return Price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		Price = price;
	}

	/**
	 * @return the time
	 */
	public Integer getTime() {
		return Time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Integer time) {
		Time = time;
	}

	/**
	 * @return the difficulty
	 */
	public Short getDifficulty() {
		return Difficulty;
	}

	/**
	 * @return the string representation of difficulty
	 */
	public String getDifficultyString() {
		if (Difficulty == 0) return "易";
		else if (Difficulty == 1) return "中等";
		else if (Difficulty == 2) return "难";
		else return "未知";
	}
	
	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(Short difficulty) {
		Difficulty = difficulty;
	}

	/**
	 * @return the fitMeal
	 */
	public Short getFitMeal() {
		return FitMeal;
	}

	/**
	 * @return the string presentation of the fit meals
	 */
	public String getFitMealString() {
		if (FitMeal == 0) return "早餐";
		else if (FitMeal == 1) return "正餐";
		else if (FitMeal == 2) return "都可以";
		else return "未知";
	}
	
	/**
	 * @param fitMeal the fitMeal to set
	 */
	public void setFitMeal(Short fitMeal) {
		FitMeal = fitMeal;
	}

	/**
	 * @return the energyContent
	 */
	public Float getEnergyContent() {
		return EnergyContent;
	}

	/**
	 * @param energyContent the energyContent to set
	 */
	public void setEnergyContent(Float energyContent) {
		EnergyContent = energyContent;
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
	 * @return the function
	 */
	public Function getFunction() {
		return function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(Function function) {
		this.function = function;
	}

	/**
	 * @param type
	 * @param name
	 * @param alias
	 * @param cuisine
	 * @param taste
	 * @param cookType
	 * @param seasons
	 * @param preparation
	 * @param price
	 * @param time
	 * @param difficulty
	 * @param fitMeal
	 * @param energyContent
	 * @param introduction
	 * @param author
	 * @param date
	 * @param function
	 */
	public Dish(DishType type, String name, String alias, Cuisine cuisine,
			Taste taste, Cook cookType, Short seasons, String preparation,
			Float price, Integer time, Short difficulty, Short fitMeal,
			Float energyContent, String introduction, User author, Date date,
			Function function) {
		Type = type;
		Name = name;
		Alias = alias;
		this.cuisine = cuisine;
		this.taste = taste;
		CookType = cookType;
		Seasons = seasons;
		this.preparation = preparation;
		Price = price;
		Time = time;
		Difficulty = difficulty;
		FitMeal = fitMeal;
		EnergyContent = energyContent;
		Introduction = introduction;
		this.author = author;
		this.date = date;
		this.function = function;
	}
	
	
}
