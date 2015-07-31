package frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import persistence.DatabaseConnector;
import backend.CategoryManager;
import backend.CommandManager;
import backend.Crypt;
import backend.LoginManager;
import backend.OfferManager;
import backend.User;

public class Run {

	static BufferedReader br = new BufferedReader(new InputStreamReader(
			System.in));
	static LoginManager loginManager;
	static OfferManager offerManager;
	static CategoryManager categoryManager;

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
		categoryManager = new CategoryManager(databaseConnector);
		CommandManager commandManager = new CommandManager(loginManager,
				offerManager, categoryManager);
		System.out.println("Uni Kleinanzeigen");
		user = loginUser();
		if(user==null){
			return;
		}
		if (user.isLoggedIn())

		{
			System.out.println("\nShow All Commands with command: help");

			String command = "";
			offerManager.checkOffers();
			while (!command.equals("exit")) {
				if (!user.isLoggedIn()) {
					user = loginUser();
					if(user==null){
						return;
					}else {
						System.out.println("\nShow All Commands with command: help");
					}
				} else {
					System.out.println("Enter Command:");
					try {
						command = br.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!command.equals("exit")) {
						commandManager.execute(command, user);
					}
					offerManager.checkOffers();
				}
			}
		}

	}

	public static User loginUser() {
		User user = null;
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
			String password = "";
			int attempts = 0;
			do {
				if (attempts > 0) {
					System.out.println("Login unsuccessful\nPlease try again");
				}
				System.out.println("Please Enter your password:");
				try {
					password = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				attempts++;
			} while ((user = loginManager.loginUser(username,
					Crypt.getSHA1(password))) == null
					&& attempts < 3);
			if (attempts == 3 && user == null) {
				System.out
						.println("Login Attempts Exceeded!\nProgram Terminated!");
				return null;
			} else {
				if (user.isLoggedIn()) {
					System.out.println("Login successful");
				}

			}
		} else

		{
			user = registerUser(username);
			user = loginManager.loginUser(username, user.getPasswordHash());

		}
		return user;
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
			user = loginManager.registerUser(username, password, firstname,
					lastname, telefon, address);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
	}

}
