package backend.commands;

import java.util.ArrayList;
import java.util.Map;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class ListOffersCommand extends Command {

	public ListOffersCommand(LoginManager loginManager, OfferManager offerManager,
			CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("list");
		keywords.add("List");
		keywords.add("LIST");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		for (Map.Entry<Integer, Offer> offer : offerManager.getOffers()
				.entrySet()) {
			printSmallOffer(offer.getValue());
		}
		if(offerManager.getOffers().size()==0){
			System.out.println("No Offers found!");
		}

	}

}
