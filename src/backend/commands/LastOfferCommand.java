package backend.commands;

import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.OfferManager;

public class LastOfferCommand extends Command {

	public LastOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("last");
		keywords.add("Last");
		keywords.add("LAST");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		for (Integer offer : currentUser.getVisitedOffers()) {
			if(offer!=null){
				printSmallOffer(offerManager.getOfferById(offer));
			}
		}

	}

}
