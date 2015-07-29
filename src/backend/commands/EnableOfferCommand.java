package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class EnableOfferCommand extends Command {

	public EnableOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("enable");
		keywords.add("Enable");
		keywords.add("ENABLE");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		Integer OfferId = 0;
		switch(parameters.size()){
		case 0:
			System.out.println("Please enter OfferId:");
			try {
				String input = br.readLine();
				OfferId = Integer.parseInt(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			OfferId=Integer.parseInt(parameters.get(0));
		}
		
		Offer offer = offerManager.getOfferById(OfferId);
		if (offer == null) {
			System.out.println("This ID does not exist\n");
			return;
		}
		if(offer.isAvailable()){
			System.out.println("Already Enabled!");
			return;
		}
		if(currentUser.getId()!=offer.getUserId()){
			System.out.println("You cant edit this!");
			return;
		}
		offerManager.enableOffer(offer);
		System.out.println("Enabled Offer: "+offer.getId());
		

	}

}
