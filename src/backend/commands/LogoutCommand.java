package backend.commands;

import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.OfferManager;

public class LogoutCommand extends Command {

	public LogoutCommand(LoginManager loginManager, OfferManager offerManager,
			CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("logout");
		keywords.add("Logout");
		keywords.add("LOGOUT");
		keywords.add("logoff");
		keywords.add("LOGOFF");
		keywords.add("LOGOFF");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		currentUser.logout();
		System.out.println("You`re logged out!");

	}

}
