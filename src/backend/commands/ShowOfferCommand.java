package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class ShowOfferCommand extends Command {

	
	public ShowOfferCommand(LoginManager loginManager, OfferManager offerManager,
			CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("show");
		keywords.add("Show");
		keywords.add("SHOW");
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
		} else {
			loginManager.visitOffer(currentUser, offer);
			printOffer(offer);
		}
		
	}

}
