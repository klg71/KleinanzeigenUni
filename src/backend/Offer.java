package backend;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Offer {
	private String name;
	private String description;
	private Integer userId;
	private Date time;
	private SimpleDateFormat format;
	private Integer id;

	public Offer(String name, String description, Integer userId, Date time,
			Integer id) {
		super();
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.time = time;
		this.id = id;
		format = new SimpleDateFormat("dd.MM.yyyy");

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

	@Override
	public String toString() {
		return "Offer: " + name + " : " + format.format(time)+
				" id: "+Integer.toString(id)+ "\n"
				+ description ;
	}

}
