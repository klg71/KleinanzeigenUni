package backend.commands;

import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.OfferManager;
import backend.User;

public class HelpCommand extends Command {

	public HelpCommand(LoginManager loginManager, OfferManager offerManager,
			CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("help");
		keywords.add("Help");
		keywords.add("HELP");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		System.out.println("Available Commands:");
		System.out.println("help\t Print help");
		System.out.println("list\t Prints all available Offers");
		System.out
				.println("search\t Searches in all offers and prints the result");
		System.out
.println("show\t Prints a specific offer with address and telefon of Offercreator");
		System.out.println("add\t Adds a offer");
		System.out.println("edit\t Edits a specific offer");
		System.out.println("delete\t Deletes a specific offer");
		System.out.println("disable\\enable\t Disables\\Enables a specific offer");
		System.out
				.println("suggest\t Suggests you Offers based on your searched and visited");
		System.out.println("last\t Lists the Offers you visited last");
		System.out.println("categories\t Prints all categories");
		System.out.println("logout\t User Logout");
		System.out.println("exit\t Exits the programm");
	}

}
