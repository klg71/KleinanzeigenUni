package backend.commands;

import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.History;
import backend.LoginManager;
import backend.OfferManager;

public class PrintHistoryCommand extends Command {

	public PrintHistoryCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("history");
		keywords.add("History");
		keywords.add("HISTORY");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		// TODO Auto-generated method stub
		super.execute(parameters);
		History.printHistory();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	

}
