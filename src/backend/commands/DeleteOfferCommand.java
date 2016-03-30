package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class DeleteOfferCommand extends Command {

	private Offer offer;
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
		super.execute(parameters);
		int offerId = 0;
		offer = null;
		switch (parameters.size()) {
		case 0:
			System.out.println("Enter Offer ID:");
			try {
				offerId = Integer.parseInt(br.readLine());
				parameters.add(Integer.toString(offerId));
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
			offer=null;
			System.out.println("You cant delete this!");
			return;
		}
		offerManager.deleteOffer(offer);
		System.out.println("Offer: " + offer.getId() + " deleted!");

	}

	@Override
	public void undo() {
		try {
			offerManager.addOffer(offer);
			System.out.println("Readded: " + offer.getId() + " offer!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
