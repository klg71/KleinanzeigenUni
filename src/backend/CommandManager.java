package backend;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backend.commands.AddOfferCommand;
import backend.commands.DeleteOfferCommand;
import backend.commands.DisableOfferCommand;
import backend.commands.EditOfferCommand;
import backend.commands.EditUserCommand;
import backend.commands.EnableOfferCommand;
import backend.commands.HelpCommand;
import backend.commands.LastOfferCommand;
import backend.commands.ListOffersCommand;
import backend.commands.LogoutCommand;
import backend.commands.PrintHistoryCommand;
import backend.commands.SearchOfferCommand;
import backend.commands.ShowCategoriesCommand;
import backend.commands.ShowOfferCommand;
import backend.commands.SuggestOffersCommand;

public class CommandManager {
	private ArrayList<Command> commands;
	private LoginManager loginManager;
	private OfferManager offerManager;
	private Command lastCommand;
	private CategoryManager categoryManager;
	public CommandManager(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super();
		this.loginManager = loginManager;
		this.offerManager = offerManager;
		this.categoryManager = categoryManager;
		commands=new ArrayList<Command>();
		commands.add(new HelpCommand(loginManager, offerManager, categoryManager));
		commands.add(new ListOffersCommand(loginManager, offerManager, categoryManager));
		commands.add(new SearchOfferCommand(loginManager, offerManager, categoryManager));
		commands.add(new ShowOfferCommand(loginManager, offerManager, categoryManager));
		commands.add(new LastOfferCommand(loginManager, offerManager, categoryManager));
		commands.add(new AddOfferCommand(loginManager, offerManager, categoryManager));
		commands.add(new EnableOfferCommand(loginManager, offerManager, categoryManager));
		commands.add(new DisableOfferCommand(loginManager, offerManager, categoryManager));
		commands.add(new SuggestOffersCommand(loginManager, offerManager, categoryManager));
		commands.add(new EditOfferCommand(loginManager, offerManager, categoryManager));
		commands.add(new DeleteOfferCommand(loginManager, offerManager, categoryManager));
		commands.add(new ShowCategoriesCommand(loginManager, offerManager, categoryManager));
		commands.add(new LogoutCommand(loginManager, offerManager, categoryManager));
		commands.add(new EditUserCommand(loginManager, offerManager, categoryManager));
		commands.add(new PrintHistoryCommand(loginManager, offerManager, categoryManager));
	}
	public void execute(String input,User currentUser){
		if(input.equals("undo")){
			History.addCommand(lastCommand,true);
			lastCommand.undo();
			return;
		}
		ArrayList<String> splitted=new ArrayList<String>();
		Pattern pattern=Pattern.compile("([A-Za-z0-9]+)|(\\\"[^\"]+\\\")");
		Matcher m=pattern.matcher(input);
		while(m.find()){
			splitted.add(m.group().replace("\"", ""));
		}
		String keyword=splitted.get(0);
		
		splitted.remove(0);
		ArrayList<String> parameters=splitted;
		for(Command command:commands){
			if(command.checkKeyword(keyword)){
				command.setCurrentUser(currentUser);
				command.execute(parameters);
				lastCommand=command;
				History.addCommand(command,false);
				return;
			}
		}
		System.out.println("No Command with keyword: "+keyword+" found");
		
	}
	
}
