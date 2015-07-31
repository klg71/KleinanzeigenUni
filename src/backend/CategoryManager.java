package backend;

import java.sql.SQLException;
import java.util.HashMap;

import persistence.DatabaseConnector;

public class CategoryManager {
	private HashMap<Integer,Category> categories;
	private DatabaseConnector databaseConnector;
	
	public CategoryManager(DatabaseConnector databaseConnector){
		try {
			categories=databaseConnector.loadCategories();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.databaseConnector=databaseConnector;
	}

	public HashMap<Integer, Category> getCategories() {
		return categories;
	}
	
}
