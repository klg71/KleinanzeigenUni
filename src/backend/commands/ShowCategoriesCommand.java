package backend.commands;

import java.util.ArrayList;
import java.util.Map;

import backend.Category;
import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.OfferManager;

public class ShowCategoriesCommand extends Command {

	public ShowCategoriesCommand(LoginManager loginManager, OfferManager offerManager,
			CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("categories");
		keywords.add("Categories");
		keywords.add("CATEGORIES");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		for(Map.Entry<Integer,Category> category:categoryManager.getCategories().entrySet()){
			System.out.println(category.getKey()+"\t"+category.getValue().getName());
		}

	}

}
