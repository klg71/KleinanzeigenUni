package frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import persistence.DatabaseConnector;
import backend.Crypt;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;
import backend.User;

public class Run {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static LoginManager loginManager;
	static OfferManager offerManager;

	public static void main(String[] args) {
		DatabaseConnector databaseConnector = null;
		User user = null;

		try {
			databaseConnector = new DatabaseConnector("data.db", loginManager);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		loginManager = new LoginManager(databaseConnector);
		offerManager = new OfferManager(databaseConnector);
		System.out.println("Login Manager Demo");
		System.out.println("Please Enter your username:");
		br = new BufferedReader(new InputStreamReader(System.in));
		String username = "";
		try {
			username = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (loginManager.containsUser(username)) {
			System.out.println("Please Enter your password:");
			String password = "";
			try {
				password = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if ((user = loginManager.loginUser(username, Crypt.getSHA1(password))) != null) {
				System.out.println("Login succesful");
			} else {
				System.out.println("Login unsuccesful");
			}
		} else {
			user = registerUser(username);
			loginManager.loginUser(username, Crypt.getSHA1(user.getPasswordHash()));

		}
		if (user.isLoggedIn()) {
			System.out.println("\nEnter Command:");

			String command = "";
			while (!command.equals("exit")) {
				try {
					command = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (command.equals("help")) {
					printCommands();
				}
				if (command.equals("list")) {
					listOffers();
				}
				if (command.equals("search")) {
					searchOffers();
				}
				if (command.equals("show")) {
					showOffer(user);
				}
				if (command.equals("add")) {
					addOffer(user);
				}
				if (command.equals("last")) {
					showLastOffers(user);
				}
				if (!command.equals("exit"))
					System.out.println("Enter Command:");
			}
		}

	}

	public static void printCommands() {
		System.out.println("Available Commands:");
		System.out.println("help\t Print help");
		System.out.println("list\t Prints all available Offers");
		System.out.println("search\t Searches in all offers and prints the result");
		System.out.println("show\t Prints a specific offer with address and telefon of Offercreator");
		System.out.println("add\t Adds a offer");
		System.out.println("suggest\t Suggests you Offers based on your searched and visited");
		System.out.println("last\t Lists the Offers you visited last");
		System.out.println("show\t Prints a specific offer");
		System.out.println("exit\t Exits the programm");
	}

	public static void listOffers() {
		for (Map.Entry<String, Offer> offer : offerManager.getOffers().entrySet()) {
			System.out.println(offer.getValue().toString() + "\n");
		}
	}

	public static void searchOffers() {
		System.out.println("Offer Search enter the searchstring:");
		String haystack = "";
		try {
			haystack = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Map.Entry<String, Offer> offer : offerManager.searchOffers(haystack).entrySet()) {
			System.out.println(offer.getValue()+"\n");
		}
	}

	public static void showLastOffers(User user) {
		for (Integer offer : user.getVisitedOffers()) {
			System.out.println(offerManager.getOfferById(offer)+"\n");
		}
	}

	public static void printOffer(Offer offer) {
		User user = loginManager.getUserFromID(offer.getUserId());
		System.out.println(offer);
		System.out.println(user.getFirstName() + " " + user.getLastName() + "\nAddr:" + user.getAddress() + "\nTel:"
				+ user.getTelefon()+"\n");

	}

	public static void showOffer(User user) {
		System.out.println("Please enter OfferId:");
		Integer OfferId = 0;
		try {
			String input = br.readLine();
			OfferId = Integer.parseInt(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Offer offer = offerManager.getOfferById(OfferId);
		if (offer == null) {
			System.out.println("This ID does not exist\n");
		} else {
			loginManager.visitOffer(user, offer);
			printOffer(offer);
		}
	}

	public static void addOffer(User user) {
		System.out.println("Offer Creation enter the offer name:");
		String name = "";
		try {
			name = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter the Description:");
		String description = "";
		try {
			description = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			offerManager
					.addOffer(new Offer(name, description, user.getId(), new Date(new java.util.Date().getTime()), 0,0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static User registerUser(String username) {
		System.out.println("User Registration enter your password:");
		String password = "";
		try {
			password = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter your firstname:");
		String firstname = "";
		try {
			firstname = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter your lastname:");
		String lastname = "";
		try {
			lastname = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter your telefon number:");
		String telefon = "";
		try {
			telefon = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter your address:");
		String address = "";
		try {
			address = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = null;
		try {
			user = loginManager.registerUser(username, password, firstname, lastname, telefon, address);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

}
