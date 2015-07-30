package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class EditOfferCommand extends Command {

	public EditOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("edit");
		keywords.add("Edit");
		keywords.add("EDIT");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
			Integer OfferId=0;
			String name = "";
			String description="";
			Offer offer=null;
			
			int category = 0;
			switch (parameters.size()) {
			case 0:
				System.out.println("Enter Offer ID:");
				try {
					OfferId= Integer.parseInt(br.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(offerManager.getOfferById(OfferId)!=null){
					offer=offerManager.getOfferById(OfferId);
					if(offer.getUserId()!=currentUser.getId()){
						System.out.println("You cant edit this!");
						return;
					}
				}
				else {
					System.out.println("No Offer found");
					return;
				}
				
				
			case 1:
				if (parameters.size()==1){
					OfferId= Integer.parseInt(parameters.get(0));
				}
				if(offerManager.getOfferById(OfferId)!=null){
					offer=offerManager.getOfferById(OfferId);
					if(offer.getUserId()!=currentUser.getId()){
						System.out.println("You cant edit this!");
						return;
					}
				}
				else {
					System.out.println("No Offer found");
					return;
				}
				
				System.out.println("Enter new name("+offer.getName()+"):");
				try {
					name = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case 2:
				if (parameters.size()==2){
					name= parameters.get(1);
					OfferId= Integer.parseInt(parameters.get(0));
				}
				if(offerManager.getOfferById(OfferId)!=null){
					offer=offerManager.getOfferById(OfferId);
					if(offer.getUserId()!=currentUser.getId()){
						System.out.println("You cant edit this!");
						return;
					}
				}
				else {
					System.out.println("No Offer found");
					return;
				}
				
				System.out.println("Enter new description("+offer.getDescription()+"):");
				try {
					description = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case 3:
				if (parameters.size()==3){
					description=parameters.get(2);
					name= parameters.get(1);
					OfferId= Integer.parseInt(parameters.get(0));
				}
				if(offerManager.getOfferById(OfferId)!=null){
					offer=offerManager.getOfferById(OfferId);
					if(offer.getUserId()!=currentUser.getId()){
						System.out.println("You cant edit this!");
						return;
					}
				}
				else {
					System.out.println("No Offer found");
					return;
				}
				
				boolean entered = false;
				while (!entered) {
					printCategories();
					System.out.println("Enter the Category:");
					try {
						category = Integer.parseInt(br.readLine());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (categoryManager.getCategories().containsKey(category))
						entered = true;
					else {
						System.out.println("Please Enter valid Category");
					}
				}
				break;
				
			default:
				if (parameters.size()>3){
					category=Integer.parseInt(parameters.get(3));
					description=parameters.get(2);
					name= parameters.get(1);
					OfferId= Integer.parseInt(parameters.get(0));
				}
				if(offerManager.getOfferById(OfferId)!=null){
					offer=offerManager.getOfferById(OfferId);
					if(offer.getUserId()!=currentUser.getId()){
						System.out.println("You cant edit this!");
						return;
					}
				}
				else {
					System.out.println("No Offer found");
					return;
				}
				if (!categoryManager.getCategories().containsKey(category)){
					System.out.println("Please Enter valid Category");
					return;
				}
			}
			if(offer.getUserId()!=currentUser.getId()){
				System.out.println("You cant edit this!");
				return;
			}
			String oldname=offer.getName();
			offer.setName(name);
			offer.setDescription(description);
			offer.setCategoryID(category);
			offerManager.editOffer(oldname,offer);
			System.out.println("Edited Offer: "+offer.getId());

	}

}
