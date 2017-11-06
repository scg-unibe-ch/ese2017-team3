package spring.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.entity.Tour;
import spring.repositories.TourRepository;

/**
 * Created by olulrich on 20.10.17.
 */

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;
    
    public TourRepository getTourRepository() {
        return tourRepository;
    }

    public List<Tour> getToursForDriver(String username) {
        List<Tour> tours = new ArrayList<Tour>();
        tourRepository.findByDriver(username).forEach(tours::add);
        return tours;
    }

    /**
     * Returns a <code>List</code> of <code>Tours</code> that are assigned to this driver
     * and are scheduled for today
     * @param username the username of the driver
     * @return a <code>List</code> containing the driver's <code>Tours</code> for today
     */
    public List<Tour> getCurrentToursForDriver(String username) {
        List<Tour> tours = new ArrayList<Tour>();
        LocalDate today = LocalDate.now();
        tourRepository.findByDriverAndDeliveryStartDate(username, today).forEach(tours::add);
        return tours;
    }

    /**
     * Returns a <code>List</code> of <code>Tours</code> that are assigned to this driver
     * and are scheduled for this week
     * @param username the username of the driver
     * @param day the weekday
     * @return a <code>List</code> containing the driver's <code>Tours</code> for the specified day
     */
    public List<Tour> getToursForDriverAndDay(String username, DayOfWeek day) {
        List<Tour> tours = new ArrayList<Tour>();
        LocalDate tourDate = LocalDateTime.now().with(day).toLocalDate();
        tourRepository.findByDriverAndDeliveryStartDate(username, tourDate).forEach(tours::add);
        return tours;
    }

    public List<Tour> getTours() {
    	List<Tour> tours = new ArrayList<Tour>();
    	tourRepository.findAll().forEach(tours::add);
    	return tours;
    }

    public List<Tour> getUndeletedTours() {
    	List <Tour> tours = new ArrayList<>();
    	for (Tour t : getTours()) {
    		if (t.getTourState() != Tour.TourState.DELETED) {
    			tours.add(t);
			}
		}
		return tours;
	}
    
    public List<Tour> getSortedTours(String sortBy) {
    	assert sortBy != null;
    	List<Tour> tours = getUndeletedTours();
    	tours.sort(new TourComparator(sortBy));
    	return tours;
    }
    
}

class TourComparator implements Comparator<Tour> {
	
	private String sortBy;
	
	public TourComparator(String sortBy) {
		this.sortBy = sortBy;
	}
	
	@Override
	public int compare(Tour tour1, Tour tour2) {
		if (sortBy.equals("Date/Time")) {
			if (tour1.getDeliveryStartDate().isAfter(tour2.getDeliveryStartDate())) {
    			return 1;
    		} else if (tour1.getDeliveryStartDate().isBefore(tour2.getDeliveryStartDate())) {
    			return -1;
    		} else {
				if (tour1.getDeliveryStartTime().isAfter(tour2.getDeliveryStartTime())) {
					return 1;
				} else if (tour1.getDeliveryStartTime().isBefore(tour2.getDeliveryStartTime())) {
					return -1;
				} else {
					return 0;
				}
			}
		} else if (sortBy.equals("Starting Location")) {
			return compareAddress(tour1.getStartCity(), tour1.getStartAddress(), tour1.getStartAddressNumber(), tour2.getStartCity(), tour2.getStartAddress(), tour2.getStartAddressNumber());
		} else if (sortBy.equals("Target Location")) {
			return compareAddress(tour1.getDestinationCity(), tour1.getDestinationAddress(), tour1.getDestinationAddressNumber(), tour2.getDestinationCity(), tour2.getDestinationAddress(), tour2.getDestinationAddressNumber());
		} else if (sortBy.equals("Driver")) {
			return tour1.getDriver().compareTo(tour2.getDriver());
		} else if (sortBy.equals("Cargo")) {
			return tour1.getCargo().compareTo(tour2.getCargo());
		}
		return 0;
	}
	
	//TODO: Die Touren sollten zwei Address-Objekte besitzen anstatt mehrere attribute
	//      Für diese Methode werden dann nur noch 2 Parameter benötigt, vom Typ "Address"
	//      (Es gäbe noch weitere Vorteile, z.B. die Klasse "Tour" wäre übersichtlicher)
	private int compareAddress(String city1, String street1, String streetnumber1, String city2, String street2, String streetnumber2) {
		int cityCompare = city1.compareTo(city2);
		int streetCompare = street1.compareTo(street1);
		if (cityCompare == 0) {
			if (streetCompare == 0) {
				try {
					int s1 = Integer.parseInt(streetnumber1);
					int s2 = Integer.parseInt(streetnumber2);
					return (s1 > s2) ? 1 : (s1 < s2) ? -1 : 0;
				} catch (NumberFormatException nfe) {
					// handles address numbers like for example '35A', '35a', '35 a', '35/2' etc.
					Pattern pattern = Pattern.compile("(\\d+)(\\W*)(\\w*)");
					Matcher m1 = pattern.matcher(streetnumber1);
					Matcher m2 = pattern.matcher(streetnumber2);

					if (m1.find() && m2.find()) {
						int s1 = Integer.parseInt(m1.group(1));
						int s2 = Integer.parseInt(m1.group(1));
						if (s1 != s2) {
							return (s1 > s2) ? 1 : (s1 < s2) ? -1 : 0;
						} else {
							String addressDetail1 = m1.group(3);
							String addressDetail2 = m1.group(3);
							String p1 = "(\\d+)";
							String p2 = "([a-zA-Z]+)";

							if (addressDetail1.matches(p1) && addressDetail2.matches(p1)) {
								int a1 = Integer.parseInt(addressDetail1);
								int a2 = Integer.parseInt(addressDetail2);
								return (a1 > a2) ? 1 : (a1 < a2) ? -1 : 0;
							} else if (addressDetail1.matches(p2) && addressDetail2.matches(p2)) {
								return addressDetail1.compareTo(addressDetail2);
							}
						}
					}
				}
				// Same street (in same city) but different numbering rule (e.g. '35A' vs. '35/1') -> must be input error
				throw new NumberFormatException("Could not compare the following addresses:\n "
												+ street1 + " " + streetnumber1 + ",\n "
												+ street2 + " " + streetnumber2);
			} else return streetCompare;
		} else return cityCompare;
	}
	
	
	
	//Compare-Address with Addresses
	/*private int compareAddress(Address address1, Address address2) {
		int cityCompare = address1.getCity().compareTo(address2.getCity());
		int streetCompare = address1.getStreet().compareTo(address2.getStreet());
		if (cityCompare == 0) {
			if (streetCompare == 0) {
				try {
					int s1 = Integer.parseInt(address1.getStreetNumber());
					int s2 = Integer.parseInt(address2.getStreetNumber());
					return (s1 > s2) ? 1 : (s1 < s2) ? -1 : 0;
				} catch (NumberFormatException nfe) {
					// handles address numbers like for example '35A', '35a', '35 a', '35/2' etc.
					Pattern pattern = Pattern.compile("(\\d+)(\\W*)(\\w*)");
					Matcher m1 = pattern.matcher(address1.getStreetNumber());
					Matcher m2 = pattern.matcher(address2.getStreetNumber());

					if (m1.find() && m2.find()) {
						int s1 = Integer.parseInt(m1.group(1));
						int s2 = Integer.parseInt(m1.group(1));
						if (s1 != s2) {
							return (s1 > s2) ? 1 : (s1 < s2) ? -1 : 0;
						} else {
							String addressDetail1 = m1.group(3);
							String addressDetail2 = m1.group(3);
							String p1 = "(\\d+)";
							String p2 = "([a-zA-Z]+)";

							if (addressDetail1.matches(p1) && addressDetail2.matches(p1)) {
								int a1 = Integer.parseInt(addressDetail1);
								int a2 = Integer.parseInt(addressDetail2);
								return (a1 > a2) ? 1 : (a1 < a2) ? -1 : 0;
							} else if (addressDetail1.matches(p2) && addressDetail2.matches(p2)) {
								return addressDetail1.compareTo(addressDetail2);
							}
						}
					}
				}
				// Same street (in same city) but different numbering rule (e.g. '35A' vs. '35/1') -> must be input error
				throw new NumberFormatException("Could not compare the following addresses:\n "
												+ address1.getStreet() + " " + address1.getStreetNumber() + ",\n "
												+ address2.getStreet() + " " + address2.getStreetNumber());
			} else return streetCompare;
		} else return cityCompare;
	}*/
	
}