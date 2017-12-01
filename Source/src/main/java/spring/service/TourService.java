package spring.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.entity.Address;
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
     *
     * @param username the username of the driver
     * @return a <code>List</code> containing the driver's <code>Tours</code> for today
     */
    public List<Tour> getCurrentToursForDriver(String username) {
        List<Tour> tours = new ArrayList<Tour>();
        LocalDate today = LocalDate.now();
        tourRepository.findByDriverAndDeliveryStartDate(username, today).forEach(tours::add);
        return tours;
    }

    private List<Tour> getPastToursForDriver(String username) {
        List<Tour> tours = getUndeletedToursForDriver(username);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Iterator<Tour> iterator = tours.iterator();
        while (iterator.hasNext()) {
            Tour t = iterator.next();
            LocalDate date = t.getStartDate();
            LocalTime time = t.getStartTime();
            if (date.isAfter(today) || (date.equals(today) && time.isAfter(now))) {
                iterator.remove();
            }
        }
        return tours;
    }

    private List<Tour> getUpcomingToursForDriver(String username) {
        List<Tour> tours = tourRepository.findByDriverAndTourState(username, Tour.TourState.CREATED);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Iterator<Tour> iterator = tours.iterator();
        while (iterator.hasNext()) {
            Tour t = iterator.next();
            LocalDate date = t.getStartDate();
            LocalTime time = t.getStartTime();
            if (date.isBefore(today) || (date.equals(today) && time.isBefore(now))) {
                iterator.remove();
            }
        }
        return tours;
    }
    /**
     * Returns a <code>List</code> of <code>Tours</code> that are assigned to this driver
     * and are scheduled for this week
     *
     * @param username the username of the driver
     * @param day      the weekday
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
        List<Tour> tours = new ArrayList<>();
        for (Tour t : getTours()) {
            if (t.getState() != Tour.TourState.DELETED) {
                tours.add(t);
            }
        }
        return tours;
    }

    private List<Tour> getUndeletedToursForDriver(String username) {
        List<Tour> tours = getToursForDriver(username);
        Iterator<Tour> iterator = tours.iterator();
        while (iterator.hasNext()) {
            Tour t = iterator.next();
            if (t.getState() == Tour.TourState.DELETED) {
                iterator.remove();
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

    public List<Tour> getSortedPastTours(String username, String sortBy) {
        assert sortBy != null;
        List<Tour> tours = getPastToursForDriver(username);
        tours.sort(new TourComparator(sortBy));
        return tours;
    }


    public List<Tour> getSortedUpcomingTours(String username, String sortBy) {
        assert sortBy != null;
        List<Tour> tours = getUpcomingToursForDriver(username);
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
            if (tour1.getStartDate().isAfter(tour2.getStartDate())) {
                return 1;
            } else if (tour1.getStartDate().isBefore(tour2.getStartDate())) {
                return -1;
            } else {
                if (tour1.getStartTime().isAfter(tour2.getStartTime())) {
                    return 1;
                } else if (tour1.getStartTime().isBefore(tour2.getStartTime())) {
                    return -1;
                } else {
                    return 0;
                }
            } //TODO Switch statement instead of if/else
        } else if (sortBy.equals("Starting Location")) {
            return compareAddress(tour1.getStartAddress(), tour2.getStartAddress());
        } else if (sortBy.equals("Target Location")) {
            return compareAddress(tour1.getDestinationAddress(), tour2.getDestinationAddress());
        } else if (sortBy.equals("Driver")) {
            return tour1.getDriver().compareTo(tour2.getDriver());
        } else if (sortBy.equals("Cargo")) {
            return tour1.getCargo().compareTo(tour2.getCargo());
        } else if (sortBy.equals("State")){
            return tour1.getState().compareTo(tour2.getState());
        }
        return 0;
    }

    //Compare-Address with Addresses
    private int compareAddress(Address address1, Address address2) {
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
	}

}