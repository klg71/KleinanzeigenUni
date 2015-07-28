package backend.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;
import backend.User;

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
		String haystack = "";
		int category = 0;
		switch (parameters.size()) {
		case 0:
			System.out.println("Offer Search enter the searchstring:");
			try {
				haystack = br.readLine();
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			category = Integer.parseInt(parameters.get(1));
		}

		Map<String, Offer> results = offerManager.searchOffers(haystack, category);
		for (Map.Entry<String, Offer> offer : results.entrySet()) {
			printSmallOffer(offer.getValue());
		}
		if (results.size() == 0) {
			System.out.println("No Offers found");
		}

		loginManager.searchOffers(currentUser, haystack);

	}

}
