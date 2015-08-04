package persistence;

public class DatabaseConnectorHolder {
	private static DatabaseConnector databaseConnector;
	public static void initialize(DatabaseConnector databaseConnector){
		DatabaseConnectorHolder.databaseConnector=databaseConnector;
	}
	public static DatabaseConnector getInstance() throws Exception{
		if(databaseConnector==null){
			throw new Exception("Error Database not initialized");
		}
		return databaseConnector;
	}
}
