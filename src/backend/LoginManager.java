package backend;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import persistence.DatabaseConnector;
import persistence.DatabaseConnectorHolder;

public class LoginManager {
	private DatabaseConnector databaseConnector;
	private HashMap<String,User> users;
	
	public LoginManager() throws Exception{
		this.databaseConnector=DatabaseConnectorHolder.getInstance();
		try {
			users=databaseConnector.loadUsers();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Map.Entry<String, User> user:users.entrySet()){
			try {
				databaseConnector.loadVisits(user.getValue());
				databaseConnector.loadSearches(user.getValue());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public User getUserFromID(int id){
		for(Entry<String, User> user:users.entrySet()){
			if(user.getValue().getId()==id){
				return user.getValue();
			}
		}
		return null;
	}
	
	public void visitOffer(User user,Offer offer){
		user.addVisit(offer.getId());
		try {
			databaseConnector.addVisit(user,offer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchOffers(User user,String search){
		try {
			databaseConnector.addSearch(user, search);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User registerUser(String username, String password,String firstname,String lastname,String telefon,String address) throws Exception{
		User user=new User(username,Crypt.getSHA1(password),firstname,lastname,address,telefon,0);
		if(!users.containsKey(username)){
			users.put(username,databaseConnector.addUser(user) );
		} else {
			throw new Exception("User registration failed: username exists");
		}
		return user;
	}
	public User loginUser(String username,String passwordHash) {
		if (users.get(username).login(passwordHash)){
			return users.get(username);
		}
		else return null;
	}
	
	public boolean containsUser(String username){
		return users.containsKey(username);
	}

	public HashMap<String, User> getUsers() {
		// TODO Auto-generated method stub
		return users;
	}

	
}
