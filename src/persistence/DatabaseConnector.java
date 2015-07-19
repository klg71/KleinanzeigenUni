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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import backend.Offer;
import backend.User;

public class DatabaseConnector {
	private String filename;

	public DatabaseConnector(String filename) throws SQLException {
		this.filename=filename;
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
		String sql = "insert into Offers (name,description,timestamp) values (\""
				+ newOffer.getName()
				+ "\",\""
				+ newOffer.getDescription()
				+ "\",\""
				+ newOffer.getTime().getTime()
				+ "\");";
		statement.execute(sql);
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
               offer=new Offer(newOffer.getName(),newOffer.getDescription(),newOffer.getUser(),newOffer.getTime(),(int) generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
		statement.close();
		connection.close();
		return offer;
		
	}

	public HashMap<String, Offer> loadOffers() {
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
			User user = new User(resultSet.getString("username"),
					resultSet.getString("passwordHash"),
					resultSet.getString("firstname"),
					resultSet.getString("lastname"),
					resultSet.getString("address"),
					resultSet.getString("telefon"), resultSet.getInt("id"));
			users.put(user.getUsername(), user);
		}
		connection.close();
		return offers;
	}
}
