package backend;

public class Search {
	private String searchString;
	private Integer category;
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Search(String searchString, Integer category) {
		super();
		this.searchString = searchString;
		this.category = category;
	}
	
}
