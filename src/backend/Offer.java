package backend;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Offer {
	private String name;
	private String description;
	private Integer userId;
	private Integer categoryID;
	private boolean available;

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}
	private Date time;
	private SimpleDateFormat format;
	private Integer id;

	public Offer(String name, String description, Integer userId, Date time,
			Integer id,Integer categoryID,int available) {
		super();
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.time = time;
		this.id = id;
		this.categoryID=categoryID;
		format = new SimpleDateFormat("dd.MM.yyyy");
		this.available=available==1;

	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Integer getUserId() {
		return userId;
	}

	public Date getTime() {
		return time;
	}

	public String getTimeString(){
		return format.format(time);
	}
	
	@Override
	public String toString() {
		return "Offer: " + name + " : " + format.format(time)+
				" id: "+Integer.toString(id)+ "\n"+"Category: "+categoryID+"\n"
				+ description ;
	}
	public Integer getCategoryID() {
		return categoryID;
	}

}
