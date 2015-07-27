package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import backend.Category;
import backend.LoginManager;
import backend.Offer;
import backend.User;

public class DatabaseConnector {
	private String filename;
	private LoginManager loginManager;

	public DatabaseConnector(String filename,LoginManager loginManager) throws SQLException {
		this.filename=filename;
		this.loginManager=loginManager;
	}

	public User addUser(User newUser) throws IOException, SQLException {
		Connection connection=null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		User user=null;
		Statement statement = connection.createStatement();
		String sql = "insert into Users (username,passwordHash,firstname,lastname,address,telefon) values (\""
				+ newUser.getUsername()
				+ "\",\""
				+ newUser.getPasswordHash()
				+ "\",\""
				+ newUser.getFirstName()
				+ "\",\""
				+ newUser.getLastName()
				+ "\",\""
				+ newUser.getAddress()
				+ "\",\"" + newUser.getTelefon() + "\");";
		statement.execute(sql);

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
               user=new User(newUser.getUsername(), newUser.getPasswordHash(), newUser.getFirstName(), newUser.getLastName(), newUser.getAddress(), newUser.getTelefon(),(int) generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        statement.close();
		connection.close();
		return user;

	}

	public HashMap<String, User> loadUsers() throws FileNotFoundException,
			SQLException {
		Connection connection=null;
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
			User user = new User(resultSet.getString("username"),
					resultSet.getString("passwordHash"),
					resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					resultSet.getString("address"),
					resultSet.getString("telefon"), resultSet.getInt("id"));
			users.put(user.getUsername(), user);
		}
		connection.close();
		return users;
	}

	public Offer addOffer(Offer newOffer) throws SQLException {
		Connection connection=null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Offer offer=null;
		Statement statement = connection.createStatement();
		String sql = "insert into Offers (name,description,timestamp,userid) values (\""
				+ newOffer.getName()
				+ "\",\""
				+ newOffer.getDescription()
				+ "\",\""
				+ newOffer.getTime().getTime()
				+ "\",\""
				+ newOffer.getUserId()
				+ "\");";
		statement.execute(sql);
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			//Setzen des ID Feldes
            if (generatedKeys.next()) {
               offer=new Offer(newOffer.getName(),newOffer.getDescription(),newOffer.getUserId(),newOffer.getTime(),(int) generatedKeys.getLong(1),0);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
		statement.close();
		connection.close();
		return offer;
		
	}

	public HashMap<String, Offer> loadOffers() throws SQLException {
		Connection connection=null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		HashMap<String, Offer> offers = new HashMap<String, Offer>();
		Statement statement = connection.createStatement();
		String sql = "select * from Offers";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			Offer offer = null;
			try {
				offer = new Offer(resultSet.getString("name"),
						resultSet.getString("description"),
						resultSet.getInt("userid"),
						resultSet.getDate("timestamp"),
						resultSet.getInt("id"),resultSet.getInt("category"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			offers.put(offer.getName(), offer);
		}
		connection.close();
		return offers;
	}

	public void loadVisits(User user) throws SQLException {
		
		ArrayList<Integer> visits=new ArrayList<Integer>();
		
		Connection connection=null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "select * from visiting where UserId="+user.getId()+";";
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			try {http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=90
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
		Connection connection=null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "insert into visiting (UserId,OfferId) values (\""+user.getId()+"\",\""+offer.getId()+"\");";
		System.out.println(sql);
		statement.execute(sql);

		statement.close();
		connection.close();
		
	}
	
	public void addSearch(User user,String search) throws SQLException{
		Connection connection=null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement statement = connection.createStatement();
		String sql = "insert into searches (User,Search) values (\""+user.getId()+"\",\""+search+"\");";
		System.out.println(sql);
		statement.execute(sql);

		statement.close();
		connection.close();
		
	}

	public ArrayList<Category> loadCategories() throws SQLException {

		ArrayList<Category> categories=new ArrayList<Category>();
		
		Connection connection=null;
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
				categories.add(new Category(resultSet.getString("Name"), resultSet.getString("Description"), resultSet.getInt("id")));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		connection.close();
		return categories;
	}
}
