package backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backend.commands.HelpCommand;
import backend.commands.ListOffersCommand;
import backend.commands.SearchOfferCommand;
import backend.commands.ShowOfferCommand;

public class CommandManager {
	private ArrayList<Command> commands;
	private LoginManager loginManager;
	private OfferManager offerManager;
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
	}
	public void execute(String input,User currentUser){

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
				return;
			}
		}
		System.out.println("No Command with keyword: "+keyword+" found");
		
	}
	
}
