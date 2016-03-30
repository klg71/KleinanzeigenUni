package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import backend.Category;
import backend.LoginManager;
import backend.Offer;
import backend.Search;
import backend.User;

public class DatabaseConnector {
	private String filename;

	public DatabaseConnector(String filename) throws SQLException {
		this.filename = filename;
	}

	public User addUser(User newUser) throws IOException, SQLException {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		User user = null;
		Statement statement = connection.createStatement();
		String sql = "insert into Users (username,passwordHash,firstname,lastname,address,telefon) values (\""
				+ newUser.getUsername() + "\",\"" + newUser.getPasswordHash() + "\",\"" + newUser.getFirstName()
				+ "\",\"" + newUser.getLastName() + "\",\"" + newUser.getAddress() + "\",\"" + newUser.getTelefon()
				+ "\");";
		statement.execute(sql);

		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				user = new User(newUser.getUsername(), newUser.getPasswordHash(), newUser.getFirstName(),
						newUser.getLastName(), newUser.getAddress(), newUser.getTelefon(),
						(int) generatedKeys.getLong(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}
		statement.close();
		connection.close();
		return user;

	}

	public HashMap<String, User> loadUsers() throws FileNotFoundException, SQLException {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		HashMap<String, User> users = new HashMap<String, User>();
		Statement statement = connection.createStatement();
		String sql = "select * from Users";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			User user = new User(resultSet.getString("username"), resultSet.getString("passwordHash"),
					resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("address"),
					resultSet.getString("telefon"), resultSet.getInt("id"));
			users.put(user.getUsername(), user);
		}
		connection.close();
		return users;
	}

	public Offer addOffer(Offer newOffer) throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Offer offer = null;
		String availableString="0";
		if(newOffer.isAvailable())
			availableString="1";
		Statement statement = connection.createStatement();
		String sql = "insert into Offers (name,description,timestamp,userid,category,available) values (\"" + newOffer.getName()
				+ "\",\"" + newOffer.getDescription() + "\",\"" + newOffer.getTime().getTime() + "\",\""
				+ newOffer.getUserId() + "\",\"" + newOffer.getCategoryID() + "\",\"" + availableString + "\");";
		statement.execute(sql);
		int available=0;
		if(newOffer.isAvailable())
			available=1;
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			// Setzen des ID Feldes
			if (generatedKeys.next()) {
				offer = new Offer(newOffer.getName(), newOffer.getDescription(), newOffer.getUserId(),
						newOffer.getTime(), (int) generatedKeys.getLong(1), newOffer.getCategoryID(),available);
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}
		statement.close();
		connection.close();
		return offer;

	}

	public void editOffer(Offer editedOffer) throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String available = "";
		if (editedOffer.isAvailable())
			available = "1";
		else
			available = "0";
		String sql = "update Offers set name='" + editedOffer.getName() + "', description='"
				+ editedOffer.getDescription() + "', category=" + editedOffer.getCategoryID().toString()
				+ ", available=" + available + " where id=" + editedOffer.getId() + ";";
		statement.execute(sql);
		
		connection.close();
	}
	
	public void deleteOffer(Offer deletedOffer) throws SQLException{
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "delete from Offers where id=" + deletedOffer.getId() + ";";
		statement.execute(sql);
		
		connection.close();
	}

	public HashMap<Integer, Offer> loadOffers() throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		HashMap<Integer, Offer> offers = new HashMap<Integer, Offer>();
		Statement statement = connection.createStatement();
		String sql = "select * from Offers";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			Offer offer = null;
			try {
				offer = new Offer(resultSet.getString("name"), resultSet.getString("description"),
						resultSet.getInt("userid"), resultSet.getDate("timestamp"), resultSet.getInt("id"),
						resultSet.getInt("category"),resultSet.getInt("available"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			offers.put(offer.getId(), offer);
		}
		connection.close();
		return offers;
	}

	public void loadVisits(User user) throws SQLException {

		ArrayList<Integer> visits = new ArrayList<Integer>();

		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "select * from visiting where UserId=" + user.getId() + ";";
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			try {
				visits.add(resultSet.getInt("OfferId"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		user.setVisitedOffers(visits);
		connection.close();

	}

	public void addVisit(User user, Offer offer) throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "insert into visiting (UserId,OfferId) values (\"" + user.getId() + "\",\"" + offer.getId()
				+ "\");";
		statement.execute(sql);

		statement.close();
		connection.close();

	}

	public void addSearch(User user, String search) throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "insert into searches (User,Search) values (\"" + user.getId() + "\",\"" + search + "\");";
		statement.execute(sql);

		statement.close();
		connection.close();

	}

	public void loadSearches(User user) throws SQLException {
		ArrayList<Search> searches = new ArrayList<Search>();

		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "select * from searches where User=" + user.getId() + ";";
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			try {
				searches.add(new Search(resultSet.getString("Search"),resultSet.getInt("Category")));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		user.setSearches(searches);
		connection.close();
	}

	public HashMap<Integer, Category> loadCategories() throws SQLException {

		HashMap<Integer, Category> categories = new HashMap<Integer, Category>();

		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		Statement statement = connection.createStatement();
		String sql = "select * from categories;";
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			try {
				categories.put(resultSet.getInt("id"), new Category(resultSet.getString("Name"),
						resultSet.getString("Description"), resultSet.getInt("id")));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		connection.close();
		return categories;
	}

	public void editUser(User editedUser) throws SQLException {
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "update Users set firstname='" + editedUser.getFirstName() + "', lastname='"
				+ editedUser.getLastName() + "', address='" + editedUser.getAddress()
				+ "', passwordHash='" + editedUser.getPasswordHash()+ "', telefon='" + editedUser.getTelefon() + "' where id=" + editedUser.getId() + ";";
		statement.execute(sql);
		
		connection.close();
		
	}
}
