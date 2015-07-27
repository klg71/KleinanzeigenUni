package backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import persistence.DatabaseConnector;

public class OfferManager {
	private HashMap<String, Offer> offers;
	private DatabaseConnector databaseConnector;

	public OfferManager(DatabaseConnector databaseConnector) {
		this.databaseConnector = databaseConnector;
		try {
			offers = databaseConnector.loadOffers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, Offer> getOffers() {
		return offers;
	}

	public void addOffer(Offer newOffer) throws Exception {
		if (!offers.containsKey(newOffer.getName())) {
			offers.put(newOffer.getName(), databaseConnector.addOffer(newOffer));
		} else {
			throw new Exception("Offer Creation failed: Offername exists");
		}
	}

	public HashMap<String, Offer> searchOffers(String haystack) {
		HashMap<String, Offer> retMap = new HashMap<String, Offer>();
		for (Map.Entry<String, Offer> entry : offers.entrySet()) {
			if (entry.getKey().contains(haystack)) {
				retMap.put(entry.getKey(), entry.getValue());
			}
		}
		return retMap;
		// return (HashMap<String, Offer>)
		// offers.entrySet().parallelStream().filter(entry->entry.getKey().contains(haystack)).collect(Collectors.toMap(entry->entry.getKey(),entry->entry.getValue()
		// ));
	}

	public Offer getOfferById(Integer offerId) {
		for (Map.Entry<String, Offer> entry : offers.entrySet()) {
			if (entry.getValue().getId() == offerId) {
				return entry.getValue();
			}
		}
		return null;
	}

	public HashMap<String, Offer> suggestOffers(User user) {
		HashMap<String, Offer> retMap = new HashMap<String, Offer>();
		for (Integer offer : user.getVisitedOffers()) {
			retMap.put(getOfferById(offer).getName(), getOfferById(offer));
		}
		for (String search : user.getSearches()) {
			retMap.putAll(searchOffers(search));
		}

		HashMap<String, Offer> removedMap = new HashMap<String, Offer>();
		boolean found = false;
		for (Map.Entry<String, Offer> entry : retMap.entrySet()) {
			for (Map.Entry<String, Offer> entry1 : removedMap.entrySet()) {
				if (entry1.getValue() == entry.getValue()) {
					found = true;
				}
			}
			if (!found) {
				removedMap.put(entry.getKey(), entry.getValue());
			}
			found = false;
		}
		if (removedMap.size() < 3) {
			return removedMap;
		} else {
			Random rand = new Random();
			ArrayList<Integer> suggestPos = new ArrayList<Integer>();
			Integer number = 0;
			while (suggestPos.size() < 3) {
				number = rand.nextInt(retMap.size());
				for (Integer numberInList : suggestPos) {
					if (numberInList == number)
						found = true;
				}
				if (!found)
					suggestPos.add(number);
				found = false;
			}

			HashMap<String, Offer> suggestMap = new HashMap<String, Offer>();
			Integer i = 0;
			for (Integer suggest : suggestPos) {
				i=0;
				for (Map.Entry<String, Offer> entry : removedMap.entrySet()) {
					if(i==suggest){
						suggestMap.put(entry.getKey(),entry.getValue());
					}
				}
				i++;
			}
			return suggestMap;
		}
	}

}
