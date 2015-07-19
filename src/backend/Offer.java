package backend;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Offer {
	private String name;
	private String description;
	private User user;
	private Date time;
	private SimpleDateFormat format;
	private Integer id;

	public Offer(String name, String description, User user, Date time,
			Integer id) {
		super();
		this.name = name;
		this.description = description;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public Date getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "Offer " + name + " : " + format.format(time)+
				" id: "+Integer.toString(id)+ "\n"
				+ description + "\n\n From:" + user.getFirstName() + " "
				+ user.getLastName() + " Tel:" + user.getTelefon();
	}

}