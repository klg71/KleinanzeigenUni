package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class EnableOfferCommand extends Command {
	
	private Offer offer;
	public EnableOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("enable");
		keywords.add("Enable");
		keywords.add("ENABLE");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		super.execute(parameters);
		Integer OfferId = 0;
		switch (parameters.size()) {
		case 0:
			System.out.println("Please enter OfferId:");
			try {
				String input = br.readLine();
				OfferId = Integer.parseInt(input);
				parameters.add(OfferId.toString());
			} catch (IOException|NumberFormatException e) {
				System.out.println("No valid Offer");
				return;
			}
			break;
		default:
			try {
				OfferId = Integer.parseInt(parameters.get(0));
			} catch (NumberFormatException e) {
				System.out.println("No valid Offer");
				return;
			}
		}

		offer = offerManager.getOfferById(OfferId);
		if (offer == null) {
			System.out.println("This ID does not exist\n");
			return;
		}
		if (offer.isAvailable()) {
			System.out.println("Already Enabled!");
			return;
		}
		if (currentUser.getId() != offer.getUserId()) {
			System.out.println("You cant edit this!");
			return;
		}
		offerManager.enableOffer(offer);
		isDone=true;
		System.out.println("Enabled Offer: " + offer.getId());

	}

	@Override
	public void undo() {
		if(isDone){
			offerManager.disableOffer(offer);
			System.out.println("Disabled Offer: " + offer.getId());
		}
		
	}

}
