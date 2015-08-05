package backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public abstract class Command {
	protected ArrayList<String> keywords;
	protected int numberParameters;
	protected LoginManager loginManager;
	protected OfferManager offerManager;
	protected CategoryManager categoryManager;
	protected User currentUser;
	protected BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	
	public Command(LoginManager loginManager,OfferManager offerManager,CategoryManager categoryManager){
		this.loginManager=loginManager;
		this.offerManager=offerManager;
		this.categoryManager=categoryManager;
		br = new BufferedReader(new InputStreamReader(System.in));
		keywords=new ArrayList<String>();
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public abstract void execute(ArrayList<String> parameters);
	public boolean checkKeyword(String keyword){
		return keywords.contains(keyword);
	}
	
	public void printOffer(Offer offer) {
		User user = loginManager.getUserFromID(offer.getUserId());
		printSmallOffer(offer);
		System.out.println(user.getFirstName() + " " + user.getLastName()
		+ "\nAddr:" + user.getAddress() + "\nTel:" + user.getTelefon()
		+ "\n");

	}
	
	public void printSmallOffer(Offer offer){
		if(!offer.isAvailable()){
			System.out.println("DISABLED");
		}
		String category="";
		if(categoryManager.getCategories().get(offer.getCategoryID())!=null){
			category=categoryManager.getCategories().get(offer.getCategoryID()).getName();
		}
		System.out.println("Offer: " + offer.getName() + " : " + offer.getTimeString()+
				" id: "+Integer.toString(offer.getId())+ "\n"+category+"\n"
				+ offer.getDescription());
	}
	public void printCategories() {
		for (Map.Entry<Integer, Category> category : categoryManager
				.getCategories().entrySet()) {
			System.out.println(category.getKey() + "\t"
					+ category.getValue().getName() + "\t"
					+ category.getValue().getDescription());
		}

	}

	
}
