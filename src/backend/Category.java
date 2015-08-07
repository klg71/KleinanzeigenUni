package backend;

public class Category {
	private String name;
	private String description;
	private Integer id;
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Integer getId() {
		return id;
	}
	public Category(String name, String description, Integer id) {
		super();
		this.name = name;
		this.description = description;
		this.id = id;
	}
	
}
