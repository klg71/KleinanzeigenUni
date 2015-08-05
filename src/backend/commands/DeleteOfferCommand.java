package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class DeleteOfferCommand extends Command {

	public DeleteOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("delete");
		keywords.add("Delete");
		keywords.add("DELETE");
		keywords.add("del");
		keywords.add("DEL");
		keywords.add("Del");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		int offerId = 0;
		Offer offer = null;
		switch (parameters.size()) {
		case 0:
			System.out.println("Enter Offer ID:");
			try {
				offerId = Integer.parseInt(br.readLine());
			} catch (IOException | NumberFormatException e) {
				System.out.println("No valid Offer");
				return;
			}
			if (offerManager.getOfferById(offerId) != null) {
				offer = offerManager.getOfferById(offerId);
			} else {
				System.out.println("No Offer found");
				return;
			}
			break;
		default:
			try {
				offerId = Integer.parseInt(parameters.get(0));
			} catch (NumberFormatException e) {
				System.out.println("No valid Offer");
				return;
			}
			if (offerManager.getOfferById(offerId) != null) {
				offer = offerManager.getOfferById(offerId);
			} else {
				System.out.println("No Offer found");
				return;
			}

		}
		if (offer.getUserId() != currentUser.getId()) {
			System.out.println("You cant delete this!");
			return;
		}
		offerManager.deleteOffer(offer);
		System.out.println("Offer: " + offer.getId() + " deleted!");

	}

}
