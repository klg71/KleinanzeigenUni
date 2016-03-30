package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class ShowOfferCommand extends Command {

	public ShowOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("show");
		keywords.add("Show");
		keywords.add("SHOW");
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
			} catch (IOException | NumberFormatException e) {
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

		Offer offer = offerManager.getOfferById(OfferId);
		if (offer == null) {
			System.out.println("This ID does not exist\n");
		} else {
			loginManager.visitOffer(currentUser, offer);
			printOffer(offer);
		}

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
