package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class EditOfferCommand extends Command {

	private Offer offer;
	private String oldName;
	private String oldDesc;
	private Integer oldCat;
	
	public EditOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("edit");
		keywords.add("Edit");
		keywords.add("EDIT");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		super.execute(parameters);
		Integer OfferId = 0;
		String name = "";
		String description = "";
		offer = null;

		int category = 0;
		switch (parameters.size()) {
		case 0:
			System.out.println("Enter Offer ID:");
			try {
				OfferId = Integer.parseInt(br.readLine());
				parameters.add(Integer.toString(OfferId));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (offerManager.getOfferById(OfferId) != null) {
				offer = offerManager.getOfferById(OfferId);
				if (offer.getUserId() != currentUser.getId()) {
					System.out.println("You cant edit this!");
					return;
				}
			} else {
				System.out.println("No Offer found");
				return;
			}

		case 1:
			if (parameters.size() == 1) {
				OfferId = Integer.parseInt(parameters.get(0));
			}
			if (offerManager.getOfferById(OfferId) != null) {
				offer = offerManager.getOfferById(OfferId);
				if (offer.getUserId() != currentUser.getId()) {
					System.out.println("You cant edit this!");
					return;
				}
			} else {
				System.out.println("No Offer found");
				return;
			}

			System.out.println("Enter new name(" + offer.getName() + "):");
			try {
				name = br.readLine();
				parameters.add(name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 2:
			if (parameters.size() == 2) {
				name = parameters.get(1);
				OfferId = Integer.parseInt(parameters.get(0));
			}
			if (offerManager.getOfferById(OfferId) != null) {
				offer = offerManager.getOfferById(OfferId);
				if (offer.getUserId() != currentUser.getId()) {
					System.out.println("You cant edit this!");
					return;
				}
			} else {
				System.out.println("No Offer found");
				return;
			}

			System.out.println("Enter new description(" + offer.getDescription() + "):");
			try {
				description = br.readLine();
				parameters.add(description);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 3:
			if (parameters.size() == 3) {
				description = parameters.get(2);
				name = parameters.get(1);
				OfferId = Integer.parseInt(parameters.get(0));
			}
			if (offerManager.getOfferById(OfferId) != null) {
				offer = offerManager.getOfferById(OfferId);
				if (offer.getUserId() != currentUser.getId()) {
					System.out.println("You cant edit this!");
					return;
				}
			} else {
				System.out.println("No Offer found");
				return;
			}

			boolean entered = false;
			while (!entered) {
				printCategories();
				System.out.println("Enter the Category:");
				try {
					category = Integer.parseInt(br.readLine());
					parameters.add(Integer.toString(category));
				} catch (IOException | NumberFormatException e) {
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
			if (parameters.size() > 3) {
				try {
					category = Integer.parseInt(parameters.get(3));
				} catch (NumberFormatException e) {
					System.out.println("No valid Category");
					return;
				}
				description = parameters.get(2);
				name = parameters.get(1);
				try {
					OfferId = Integer.parseInt(parameters.get(0));
				} catch (NumberFormatException e) {
					System.out.println("No valid Offer");
					return;
				}
			}
			if (offerManager.getOfferById(OfferId) != null) {
				offer = offerManager.getOfferById(OfferId);
				if (offer.getUserId() != currentUser.getId()) {
					System.out.println("You cant edit this!");
					return;
				}
			} else {
				System.out.println("No Offer found");
				return;
			}
			if (!categoryManager.getCategories().containsKey(category)) {
				System.out.println("Please Enter valid Category");
				return;
			}
		}
		if (offer.getUserId() != currentUser.getId()) {
			System.out.println("You cant edit this!");
			return;
		}
		oldName = offer.getName();
		oldCat = offer.getCategoryID();
		oldDesc = offer.getDescription();
		
		offer.setName(name);
		offer.setDescription(description);
		offer.setCategoryID(category);
		offerManager.editOffer(offer);
		isDone=true;
		System.out.println("Edited Offer: " + offer.getId());

	}

	@Override
	public void undo() {
		if(isDone){
			offer.setName(oldName);
			offer.setDescription(oldDesc);
			offer.setCategoryID(oldCat);
			offerManager.editOffer(offer);
			System.out.println("ReEdited Offer: " + offer.getId());
		}
		
	}

}
