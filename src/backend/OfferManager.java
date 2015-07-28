package backend;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	public HashMap<String, Offer> searchOffers(String haystack, Integer category) {
		HashMap<String, Offer> retMap = new HashMap<String, Offer>();
		if (category == 0) {
			for (Map.Entry<String, Offer> entry : offers.entrySet()) {
				if (entry.getKey().contains(haystack)) {
					retMap.put(entry.getKey(), entry.getValue());
				}
			}
			return retMap;
		} else {
			for (Map.Entry<String, Offer> entry : offers.entrySet()) {
				if (entry.getKey().contains(haystack)&&entry.getValue().getCategoryID()==category) {
					retMap.put(entry.getKey(), entry.getValue());
				}
			}
			
			return retMap;
		}
	}
	
	public void disableOffer(Offer offer){
		offer.setAvailable(false);
		try {
			databaseConnector.editOffer(offer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void enableOffer(Offer offer){
		offer.setAvailable(true);
		try {
			databaseConnector.editOffer(offer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Offer getOfferById(Integer offerId) {
		for (Map.Entry<String, Offer> entry : offers.entrySet()) {
			if (entry.getValue().getId() == offerId) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public void editOffer(Offer editedOffer){
		try {
			databaseConnector.editOffer(editedOffer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteOffer(Offer deletedOffer){
		offers.remove(deletedOffer);
		try {
			databaseConnector.deleteOffer(deletedOffer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, Offer> suggestOffers(User user) {
		// Endgültiges Ergebnis
		HashMap<String, Offer> returnMap = new HashMap<String, Offer>();
		// Alle relevanten Angebote
		HashMap<String, Offer> fullMap = new HashMap<String, Offer>();

		// Füge besuchten zur Liste hinzu
		for (Integer offer : user.getVisitedOffers()) {
			fullMap.put(getOfferById(offer).getName(), getOfferById(offer));
		}
		// Füge alle aus Suchen hinzu
		for (String search : user.getSearches()) {
			fullMap.putAll(searchOffers(search,0));
		}

		// Generiere geordnete Liste
		HashMap<Offer, Integer> orderedMap = new HashMap<Offer, Integer>();

		// Gehe durch alle Elemente durch
		for (Map.Entry<String, Offer> entry : fullMap.entrySet()) {
			if (orderedMap.containsKey(entry.getValue())) {
				orderedMap.put(entry.getValue(),
						orderedMap.get(entry.getValue()) + 1);
			} else {
				orderedMap.put(entry.getValue(), 1);
			}
		}

		// Häufigste nach oben sortieren
		List<Map.Entry<Offer, Integer>> sortedList = new LinkedList<Map.Entry<Offer, Integer>>(
				orderedMap.entrySet());
		Collections.sort(sortedList,
				new Comparator<Map.Entry<Offer, Integer>>() {

					@Override
					public int compare(Entry<Offer, Integer> o1,
							Entry<Offer, Integer> o2) {
						return o1.getValue() - o2.getValue();
					}
				});

		// Elemente zurückgeben
		if (sortedList.size() < 3) {
			for (Entry<Offer, Integer> Entry : sortedList) {
				returnMap.put(Entry.getKey().getName(), Entry.getKey());
			}
		} else {
			for (int i = 0; i < 3; i++) {
				returnMap.put(sortedList.get(i).getKey().getName(), sortedList
						.get(i).getKey());
			}
		}
		return returnMap;
	}

}
