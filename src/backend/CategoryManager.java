package backend;

import java.sql.SQLException;
import java.util.HashMap;

import persistence.DatabaseConnector;
import persistence.DatabaseConnectorHolder;

public class CategoryManager {
	private HashMap<Integer,Category> categories;
	private DatabaseConnector databaseConnector;
	
	public CategoryManager() throws Exception{

		this.databaseConnector=DatabaseConnectorHolder.getInstance();
		try {
			categories=databaseConnector.loadCategories();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<Integer, Category> getCategories() {
		return categories;
	}
	
}
