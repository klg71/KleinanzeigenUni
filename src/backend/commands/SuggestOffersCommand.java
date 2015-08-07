package backend.commands;

import java.util.ArrayList;
import java.util.Map;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class SuggestOffersCommand extends Command {

	public SuggestOffersCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("suggest");
		keywords.add("Suggest");
		keywords.add("SUGGEST");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		for (Map.Entry<Integer, Offer> offer : offerManager.suggestOffers(currentUser)
				.entrySet()) {
			printSmallOffer(offer.getValue());
		}

	}

}
