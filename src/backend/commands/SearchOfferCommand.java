package backend.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class SearchOfferCommand extends Command {

	
	public SearchOfferCommand(LoginManager loginManager, OfferManager offerManager,
			CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);

		keywords.add("search");
		keywords.add("Search");
		keywords.add("SEARCH");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		super.execute(parameters);
		String haystack = "";
		int category = 0;
		switch (parameters.size()) {
		case 0:
			System.out.println("Offer Search enter the searchstring:");
			try {
				haystack = br.readLine();
				parameters.add(haystack);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 1:
			if(parameters.size()==1){
				haystack = parameters.get(0);
			}
			boolean entered = false;
			while (!entered) {
				printCategories();
				System.out.println("0\tAll\tFür alle Kategorien");
				System.out.println("Enter the Category:");
				try {
					category = Integer.parseInt(br.readLine());
					parameters.add(Integer.toString(category));
				} catch (IOException|NumberFormatException e) {
					System.out.println("No valid Category");
					return;
				}
				if (categoryManager.getCategories().containsKey(category) || category == 0)
					entered = true;
				else {
					System.out.println("Please Enter valid Category");
				}
			}
			break;
		default:
			haystack = parameters.get(0);
			try {
			category = Integer.parseInt(parameters.get(1));
			} catch (NumberFormatException e) {
				System.out.println("No valid Category");
				return;
			}
		}

		Map<Integer, Offer> results = offerManager.searchOffers(haystack, category);
		for (Map.Entry<Integer, Offer> offer : results.entrySet()) {
			printSmallOffer(offer.getValue());
		}
		if (results.size() == 0) {
			System.out.println("No Offers found");
		}

		loginManager.searchOffers(currentUser, haystack);

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
