package backend;

import java.util.HashMap;
import java.util.stream.Collectors;

import persistence.DatabaseConnector;

public class OfferManager {
	private HashMap<String,Offer> offers;
	private DatabaseConnector databaseConnector;
	
	public OfferManager(DatabaseConnector databaseConnector){
		this.databaseConnector=databaseConnector;
		offers=databaseConnector.loadOffers();
	}

	public HashMap<String, Offer> getOffers() {
		return offers;
	}
	
	public void addOffer(Offer newOffer) throws Exception{
		if(!offers.containsKey(newOffer.getName())){
			offers.put(newOffer.getName(),
					databaseConnector.addOffer(newOffer));
		} else {
			throw new Exception("Offer Creation failed: Offername exists");
		}
	}
	
	public HashMap<String,Offer> searchOffers(String haystack){
		return (HashMap<String, Offer>) offers.entrySet().parallelStream().filter(entry->entry.getKey().contains(haystack)).collect(Collectors.toMap(entry->entry.getKey(),entry->entry.getValue() ));	
	}
	
	
}