package backend;

import java.util.ArrayList;

public abstract class Command {
	protected ArrayList<String> keywords;
	protected int numberParameters;
	protected LoginManager loginManager;
	protected OfferManager offerManager;
	protected CategoryManager categoryManager;
	
	
	public Command(int numberParameters,LoginManager loginManager,OfferManager offerManager,CategoryManager categoryManager){
		this.numberParameters=numberParameters;
		this.loginManager=loginManager;
		this.offerManager=offerManager;
		this.categoryManager=categoryManager;
		keywords=new ArrayList<String>();
	}
	
	public abstract void execute(ArrayList<String> parameters);
	public boolean checkKeyword(String keyword){
		return keywords.contains(keyword);
	}
}
