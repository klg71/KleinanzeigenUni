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
import persistence.DatabaseConnector;

public class OfferManager {
	private HashMap<Integer, Offer> offers;
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

	public HashMap<Integer, Offer> getOffers() {
		return offers;
	}

	public Offer addOffer(Offer newOffer) throws Exception {
		Offer offer = databaseConnector.addOffer(newOffer);
		if (!offers.containsKey(newOffer.getName())) {
			offers.put(newOffer.getId(), offer);
		} else {
			throw new Exception("Offer Creation failed: Offername exists");
		}
		return offer;
	}

	public void checkOffers() {
		ArrayList<Offer> deletedOffers = new ArrayList<Offer>();
		for (Map.Entry<Integer, Offer> entry : offers.entrySet()) {
			if (!entry.getValue().isAvailable()) {
				java.util.Date d = new java.util.Date();
				d.setHours(d.getHours() - 2);
				if (entry.getValue().getTime().getTime() < d.getTime()) {
					deletedOffers.add(entry.getValue());
				}
			}
		}
		for (Offer offer : deletedOffers) {
			deleteOffer(offer);
		}
	}

	public HashMap<Integer, Offer> searchOffers(String haystack,
			Integer category) {
		HashMap<Integer, Offer> retMap = new HashMap<Integer, Offer>();
		if (category == 0) {
			for (Map.Entry<Integer, Offer> entry : offers.entrySet()) {
				if (entry.getValue().getName().contains(haystack)) {
					retMap.put(entry.getKey(), entry.getValue());
				}
			}
			return retMap;
		} else {
			for (Map.Entry<Integer, Offer> entry : offers.entrySet()) {
				if (entry.getValue().getName().contains(haystack)
						&& entry.getValue().getCategoryID() == category) {
					retMap.put(entry.getKey(), entry.getValue());
				}
			}

			return retMap;
		}
	}

	public void disableOffer(Offer offer) {
		offer.setAvailable(false);
		try {
			databaseConnector.editOffer(offer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void enableOffer(Offer offer) {
		offer.setAvailable(true);
		try {
			databaseConnector.editOffer(offer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Offer getOfferById(Integer offerId) {
		for (Map.Entry<Integer, Offer> entry : offers.entrySet()) {
			if (entry.getValue().getId() == offerId) {
				return entry.getValue();
			}
		}
		return null;
	}

	public void editOffer(String oldname, Offer editedOffer) {
		offers.put(editedOffer.getId(), editedOffer);
		try {
			databaseConnector.editOffer(editedOffer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteOffer(Offer deletedOffer) {
		offers.remove(deletedOffer.getId());
		try {
			databaseConnector.deleteOffer(deletedOffer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<Integer, Offer> suggestOffers(User user) {
		ArrayList<Offer> allOffers = new ArrayList<Offer>();
		// Endgültiges Ergebnis
		HashMap<Integer, Offer> returnMap = new HashMap<Integer, Offer>();

		// Füge besuchten zur Liste hinzu
		for (Integer offer : user.getVisitedOffers()) {
			allOffers.add(getOfferById(offer));
		}
		// Füge alle aus Suchen hinzu
		for (Search search : user.getSearches()) {
			for (Map.Entry<Integer, Offer> entry : searchOffers(
					search.getSearchString(), 0).entrySet()) {
				allOffers.add(entry.getValue());
			}
		}

		// Generiere geordnete Liste
		HashMap<Offer, Integer> orderedMap = new HashMap<Offer, Integer>();

		// Gehe durch alle Elemente durch
		for (Offer entry : allOffers) {
			if (entry != null) {
				if (orderedMap.containsKey(entry)) {
					orderedMap.put(entry, orderedMap.get(entry) + 1);
				} else {
					orderedMap.put(entry, 1);
				}
			}
		}

		// Häufigste nach oben sortieren
		List<Map.Entry<Offer, Integer>> sortedList = new ArrayList<Map.Entry<Offer, Integer>>(
				orderedMap.entrySet());
		if (orderedMap.size() > 1) {
			Collections.sort(sortedList,
					new Comparator<Map.Entry<Offer, Integer>>() {

						@Override
						public int compare(Entry<Offer, Integer> o1,
								Entry<Offer, Integer> o2) {
							return o1.getValue() - o2.getValue();
						}
					});
		}

		// Elemente zurückgeben
		if (sortedList.size() > 0) {
			if (sortedList.size() < 3) {
				for (Entry<Offer, Integer> entry : sortedList) {
					returnMap.put(entry.getKey().getId(), entry.getKey());
				}
			} else {
				for (int i = 0; i < 3; i++) {
					returnMap.put(sortedList.get(i).getKey().getId(),
							sortedList.get(i).getKey());
				}
			}
		}
		return returnMap;
	}

}
