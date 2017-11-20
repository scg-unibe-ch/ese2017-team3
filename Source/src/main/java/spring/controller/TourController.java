package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.userdetails.UserDetails;
import spring.entity.Address;
import spring.entity.Driver;
import spring.entity.Tour;
import spring.repositories.AddressRepository;
import spring.entity.Truck;
import spring.repositories.TourRepository;
import spring.repositories.TruckRepository;
import spring.security.UserSecurityService;
import spring.service.DriverService;
import spring.service.TourService;
import spring.service.TruckService;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by olulrich on 20.10.17.
 */

@Controller
public class TourController {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourService tourService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DriverService driverService;

    @Autowired
    private TruckService truckService;

    @Autowired
    private TruckRepository truckRepository;


    @GetMapping(path = "/upcoming")
    public String myUpcomingTours(Model model, @RequestParam(required = false, defaultValue = "Date/Time") String sortBy) {
        UserDetails user = userSecurityService.getAuthenticatedUser();

        List<Tour> upcomingTours = tourService.getSortedUpcomingTours(user.getUsername(), sortBy);
        if (!sortBy.equals("")) model.addAttribute("sortBy", sortBy);
        model.addAttribute("upcomingTours", upcomingTours);

        return "frontend/myUpcomingTours";
    }

    @GetMapping(path = "/past")
    public String myPastTours(Model model, @RequestParam(required = false, defaultValue = "Date/Time") String sortBy) {
        UserDetails user = userSecurityService.getAuthenticatedUser();

        List<Tour> pastTours = tourService.getSortedPastTours(user.getUsername(), sortBy);
        if (!sortBy.equals("")) model.addAttribute("sortBy", sortBy);
        model.addAttribute("pastTours", pastTours);

        return "frontend/myPastTours";
    }

    @GetMapping(path = "/week")
    public String myWeeklyTours(Model model) {
        UserDetails user = userSecurityService.getAuthenticatedUser();
        List<Tour> monday = tourService.getToursForDriverAndDay(user.getUsername(), DayOfWeek.MONDAY);
        List<Tour> tuesday = tourService.getToursForDriverAndDay(user.getUsername(), DayOfWeek.TUESDAY);
        List<Tour> wednesday = tourService.getToursForDriverAndDay(user.getUsername(), DayOfWeek.WEDNESDAY);
        List<Tour> thursday = tourService.getToursForDriverAndDay(user.getUsername(), DayOfWeek.THURSDAY);
        List<Tour> friday = tourService.getToursForDriverAndDay(user.getUsername(), DayOfWeek.FRIDAY);
        List<Tour> saturday = tourService.getToursForDriverAndDay(user.getUsername(), DayOfWeek.SATURDAY);
        List<Tour> sunday = tourService.getToursForDriverAndDay(user.getUsername(), DayOfWeek.SUNDAY);

        model.addAttribute("monday", monday);
        model.addAttribute("tuesday", tuesday);
        model.addAttribute("wednesday", wednesday);
        model.addAttribute("thursday", thursday);
        model.addAttribute("friday", friday);
        model.addAttribute("saturday", saturday);
        model.addAttribute("sunday", sunday);

        return "frontend/myWeeklyTours";
    }

    @GetMapping(path = "/today")
    public String myCurrentTours(Model model) {
        UserDetails user = userSecurityService.getAuthenticatedUser();

        List<Tour> tours = tourService.getCurrentToursForDriver(user.getUsername());
        model.addAttribute("tours", tours);

        return "frontend/myCurrentTours";
    }

    @RequestMapping(value="/today", method=RequestMethod.POST, params={"successful=Successful"})
    public String markAsSuccessful(@RequestParam Long tourId, @RequestParam String feedback, ModelMap model) {
        UserDetails user = userSecurityService.getAuthenticatedUser();
        Tour successfulTour = tourRepository.findOne(tourId);

        if (isAllowedToCloseTour(user, successfulTour)) {
            successfulTour.setTourState(Tour.TourState.SUCCESSFUL);
            successfulTour.setTourFeedback(feedback);
            tourRepository.save(successfulTour);
            truckService.getById(successfulTour.getTruck().getId()).setAvailable(true);
        }

        List<Tour> tours = tourService.getCurrentToursForDriver(user.getUsername());
        model.addAttribute("tours", tours);
        return "frontend/myCurrentTours";
    }

    @RequestMapping(value="/today", method=RequestMethod.POST, params={"failed=Failed"})
    public String markAsFailed(@RequestParam Long tourId, @RequestParam String feedback, ModelMap model) {
        UserDetails user = userSecurityService.getAuthenticatedUser();
        Tour failedTour = tourRepository.findOne(tourId);

        if (isAllowedToCloseTour(user, failedTour)) {
            failedTour.setTourState(Tour.TourState.FAILED);
            failedTour.setTourFeedback(feedback);
            tourRepository.save(failedTour);
            truckService.getById(failedTour.getTruck().getId()).setAvailable(true);
        }

        List<Tour> tours = tourService.getCurrentToursForDriver(user.getUsername());
        model.addAttribute("tours", tours);
        return "frontend/myCurrentTours";
    }



    // Get request on /deliveries will return a form to create a new tour
    @GetMapping(path = "/deliveries")
    public String deliveryForm(Model model) {

        //prepare a list of Drivers to select from.
        List<Driver> drivers = driverService.getDrivers();
        model.addAttribute("drivers", drivers);

        //prepare a list of Trucks to select from.
        List<Truck> trucks = truckService.getTrucks();
        model.addAttribute("trucks", trucks);

        model.addAttribute("tour", new Tour());
        return "backend/deliveries";
    }

    // POST request to /deliveries with the appropriate values will create a new tour and redirect to /tours
    @PostMapping(path = "/deliveries")
    public ModelAndView deliverySubmit(@Valid @ModelAttribute Tour tour, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            List<Driver> drivers = driverService.getDrivers();
            List<Truck> trucks = truckService.getTrucks();
            model.addAttribute("drivers", drivers);
            model.addAttribute("trucks", trucks);
            return new ModelAndView("backend/deliveries");
        }
        addressRepository.save(tour.getStartAddress());
        addressRepository.save(tour.getDestinationAddress());

        if (tour.getTruck() != null) truckService.getById(tour.getTruck().getId()).setAvailable(false);
        
        tourRepository.save(tour);
        return new ModelAndView("redirect:/tours");
    }


    // GET request to /tours will return a list of all tours
    @GetMapping(path = "/tours")
    public String tourOverview(ModelMap model, @RequestParam(required = false, defaultValue = "-1") int activeIndex, @RequestParam(required = false, defaultValue = "Date/Time") String sortBy) {

        List<Tour> tours = tourService.getSortedTours(sortBy);
        if (!sortBy.equals("")) model.addAttribute("sortBy", sortBy);

        model.addAttribute("tours", tours);

        //prepare a list of Drivers to select from.
        List<Driver> drivers = driverService.getDrivers();
        model.addAttribute("drivers", drivers);

        //prepare list of trucks to select from.
        List<Truck> trucks = truckService.getTrucks();
        model.addAttribute("trucks", trucks);

        if (model.get("activeTour") == null) {
          Tour activeTour = getTourById(activeIndex, tours);
          model.addAttribute("activeTour", activeTour);
        }
        
        return "backend/tourOverview";
    }

    // GET request on /tours/delete will delete the tour with the matching index
    @GetMapping(path = "/tours/delete")
    public ModelAndView deleteTour(Model model, @RequestParam(required = true) int index) {

        List<Tour> tours = tourService.getSortedTours("");
        Tour toDelete = getTourById(index, tours);

        Truck truck = toDelete.getTruck();
        if (truck != null) {
          truck.setAvailable(true);
          truckRepository.save(truck);
        }
        toDelete.setTourState(Tour.TourState.DELETED);
        toDelete.setTruck(null);
        tourRepository.save(toDelete);

        
        tours.remove(toDelete);

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours");
    }

    @PostMapping(path = "/tours/update")
    public ModelAndView updateTour(@Valid @ModelAttribute Tour activeTour, BindingResult bindingResult, ModelMap model, RedirectAttributes redattributes) {

        if (bindingResult.hasErrors()) {
        	redattributes.addFlashAttribute("org.springframework.validation.BindingResult.activeTour", bindingResult);
        	redattributes.addFlashAttribute("activeTour", activeTour);
        	
            return new ModelAndView("redirect:/tours?activeIndex=" + activeTour.getId());
        }

        Tour oldTour = tourRepository.findOne(activeTour.getId());

        // The following attributes are not changed, because they are not part of the form in tourOverview
        //toDelete.setId(activeTour.getId());
        //oldTour.setEstimatedTime(activeTour.getEstimatedTime());
        //oldTour.setTimeFrame(activeTour.getTimeFrame());

        oldTour.getTruck().setAvailable(true);

        oldTour.setTruck(activeTour.getTruck());
        activeTour.getTruck().setAvailable(false);

        oldTour.setCargo(activeTour.getCargo());
        oldTour.setNumberOfAnimals(activeTour.getNumberOfAnimals());

        Address startAddress = oldTour.getStartAddress();
        Address destinationAddress = oldTour.getDestinationAddress();
        startAddress.copyFieldsFromAddress(activeTour.getStartAddress());
        destinationAddress.copyFieldsFromAddress(activeTour.getDestinationAddress());
//        oldTour.setStartAddress(activeTour.getStartAddress());
//        oldTour.setDestinationAddress(activeTour.getDestinationAddress());

        oldTour.setDeliveryStartDate(activeTour.getDeliveryStartDate());
        oldTour.setDeliveryStartTime(activeTour.getDeliveryStartTime());
        oldTour.setDriver(activeTour.getDriver());
        oldTour.setComment(activeTour.getComment());

        addressRepository.save(startAddress);
        addressRepository.save(destinationAddress);
        tourRepository.save(oldTour);

        List<Tour> tours = tourService.getSortedTours("");

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours?activeIndex=" + activeTour.getId());
    }

    /**
     * Checks whether a <code>User</code> identified by his <code>UserDetails</code> is allowed
     * to close e certain <code>Tour</code>.
     * @param user the <code>UserDetails</code> of the <code>User</code>
     * @param tour the <code>Tour</code> that the <code>User</code> wants to close
     * @return <code>true</code> if the <code>User</code> is allowed to close the <code>Tour</code>,
     * <br><code>false</code> otherwise.
     */
    private boolean isAllowedToCloseTour(UserDetails user, Tour tour) {
        LocalDate today = LocalDate.now();
        LocalTime now  = LocalTime.now();
        LocalDate tourStartDate = tour.getDeliveryStartDate();
        LocalTime tourStartTime = tour.getDeliveryStartTime();
        String username = user.getUsername();
        return(tour.getDriver().equals(username) && tourStartDate.isEqual(today) && tourStartTime.isBefore(now));
    }

    /**
     * Returns the tour in the list with the given id.
     *
     * @param id    positive numbers and -1
     * @param tours shouldn't be null
     * @return the first tour which have the correct id,
     * the first tour of the list if id is -1 and null
     * in all other cases.
     */
    private Tour getTourById(int id, List<Tour> tours) {
        if (tours.size() != 0) {
            if (id == -1) {
                return tours.get(0);
            } else {
                for (Tour t : tours) {
                    if (t.getId() == id) {
                        return t;
                    }
                }
            }
        }
        return null;
    }
}
