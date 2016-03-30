package backend.commands;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class AddOfferCommand extends Command {
	private Offer offer;
	
	public AddOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("add");
		keywords.add("Add");
		keywords.add("ADD");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		super.execute(parameters);
		String name = "";
		String description = "";
		offer = null;

		int category = 0;
		switch (parameters.size()) {
		case 0:

			System.out.println("Enter new name:");
			try {
				name = br.readLine();
				parameters.add(name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 1:
			if (parameters.size() == 1) {
				name = parameters.get(0);
			}
			System.out.println("Enter new description:");
			try {
				description = br.readLine();
				parameters.add(description);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 2:
			if (parameters.size() == 2) {
				description = parameters.get(1);
				name = parameters.get(0);
			}
			boolean entered = false;
			while (!entered) {
				printCategories();
				System.out.println("Enter the Category:");
				try {
					category = Integer.parseInt(br.readLine());
					parameters.add(Integer.toString(category));
				} catch (IOException |NumberFormatException e) {
					System.out.println("No valid Category");
					return;
				}
				if (categoryManager.getCategories().containsKey(category))
					entered = true;
				else {
					System.out.println("Please Enter valid Category");
				}
			}
			break;

		default:
			if (parameters.size() > 2) {
				try {
					category = Integer.parseInt(parameters.get(2));
				} catch (NumberFormatException e) {
					System.out.println("No valid Category");
					return;
				}
				description = parameters.get(1);
				name = parameters.get(0);
			}
			if (!categoryManager.getCategories().containsKey(category)) {
				System.out.println("Please Enter valid Category");
				return;
			}
		}
		offer = new Offer(name, description, currentUser.getId(), new Date(new java.util.Date().getTime()), 0, category,
				1);
		try {
			offer = offerManager.addOffer(offer);
			System.out.println("Added Offer ID: " + offer.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void undo() {
		offerManager.deleteOffer(offer);
		System.out.println("Deleted Offer ID: " + offer.getId());
		
	}

}
