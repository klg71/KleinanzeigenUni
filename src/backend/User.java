package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
	private String username;
	private String passwordHash;
	private String firstName;
	private String lastName;
	private String address;
	private String telefon;

	private Integer id;
	private boolean loggedIn;
	
	private ArrayList<Integer> visitedOffers;
	private ArrayList<String> searches;
	
	
	public User(String username, String passwordHash, String firstName,
			String lastName, String address, String telefon, Integer id) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.telefon = telefon;
		this.id = id;
		this.loggedIn=false;
		visitedOffers=new ArrayList<Integer>();
		searches=new ArrayList<String>();
	}
	
	public ArrayList<String> getSearches() {
		return searches;
	}

	public void setVisitedOffers(ArrayList<Integer> visitedOffers){
		this.visitedOffers=visitedOffers;
	}

	public ArrayList<Integer> getVisitedOffers() {
		return visitedOffers;
	}

	public User(String fileString){
		String[] splitted=fileString.split(":");
		this.username=splitted[0];
		this.passwordHash=splitted[1];
	}
	public String getUsername() {
		return username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void changePassword(String oldPassword,String newPassword) throws Exception{
		if(Crypt.getSHA1(oldPassword).equals(passwordHash)){
			passwordHash=Crypt.getSHA1(newPassword);
		} else {
			throw new Exception("Password change failed: wrong old password");
		}
	}
	
	public boolean login(String password){
		if(password.equals(passwordHash)) {
			loggedIn=true;
		}
		return loggedIn;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getId() {
		return id;
	}
	

	public boolean isLoggedIn() {
		return loggedIn;
	}


	@Override
	public String toString() {
		return "User [username=" + username + ", passwordHash=" + passwordHash
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + ", telefon=" + telefon + ", id="
				+ id + "]";
	}


	public String getAddress() {
		return address;
	}


	public String getTelefon() {
		return telefon;
	}
	
	public void addVisit(Integer offerId){
		visitedOffers.add(offerId);
	}	
	public void addSearch(String search){
		searches.add(search);
	}
	
}
