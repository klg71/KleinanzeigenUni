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

	public AddOfferCommand(LoginManager loginManager, OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		String name = "";
		String description="";
		Offer offer=null;
		
		int category = 0;
		switch (parameters.size()) {
		case 0:
			
			System.out.println("Enter new name:");
			try {
				name = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 1:
			if (parameters.size()==1){
				name= parameters.get(0);
			}
			System.out.println("Enter new description:");
			try {
				description  = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case 2:
			if (parameters.size()==2){
				description=parameters.get(1);
				name= parameters.get(0);
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
			if (parameters.size()>2){
				category=Integer.parseInt(parameters.get(2));
				description=parameters.get(1);
				name= parameters.get(0);
			}
			if (!categoryManager.getCategories().containsKey(category)){
				System.out.println("Please Enter valid Category");
				return;
			}
		}
		offer=new Offer(name, description, currentUser.getId(), new Date(new java.util.Date().getTime()), 0, category, 1);
		try {
			offerManager.addOffer(offer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
