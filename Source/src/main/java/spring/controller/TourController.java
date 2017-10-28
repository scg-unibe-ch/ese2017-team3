package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.userdetails.UserDetails;
import spring.entity.Driver;
import spring.entity.Tour;
import spring.repositories.TourRepository;
import spring.security.UserSecurityService;
import spring.service.DriverService;
import spring.service.TourService;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
    private DriverService driverService;


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

    // Get request on /deliveries will return a form to create a new tour
    @GetMapping(path = "/deliveries")
    public String deliveryForm(Model model) {

        //prepare a list of Drivers to select from.
        List<Driver> drivers = driverService.getDrivers();
        model.addAttribute("drivers", drivers);

        model.addAttribute("tour", new Tour());
        return "backend/deliveries";
    }

    // POST request to /deliveries with the appropriate values will create a new tour and redirect to /tours
    @PostMapping(path = "/deliveries")
    public ModelAndView deliverySubmit(@ModelAttribute Tour tour, Model model) {
        tourRepository.save(tour);
        return new ModelAndView("redirect:/tours");
    }

    // GET request to /tours will return a list of all tours
    @GetMapping(path = "/tours")
    public String tourOverview(Model model, @RequestParam(required = false, defaultValue = "0") int activeIndex, @RequestParam(required = false, defaultValue = "") String sortBy) {
        
    	List<Tour> tours = tourService.getSortedTours(sortBy);
        if (!sortBy.equals("")) model.addAttribute("sortBy", sortBy);
        
    	model.addAttribute("tours", tours);

        //prepare a list of Drivers to select from.
        List<Driver> drivers = driverService.getDrivers();
        model.addAttribute("drivers", drivers);

    	if (tours.size() == 0) {
    		model.addAttribute("activeTour", null);
    	} else {
    		Tour activeTour = tours.get(activeIndex);
        	model.addAttribute("activeTour", activeTour);
    	}

    	return "backend/tourOverview";
    }

    // GET request on /tours/delete will delete the tour with the matching index
    @GetMapping(path = "/tours/delete")
    public ModelAndView deleteTour(Model model, @RequestParam(required = true) int index) {

        List<Tour> tours = tourService.getSortedTours("");
        Tour toDelete = tours.get(index);

        tourRepository.delete(toDelete);

        tours.remove(index);

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours");
    }

    @PostMapping(path = "/tours/update")
    public ModelAndView updateTour(@ModelAttribute Tour activeTour, Model model) {

        List<Tour> tours;

        Tour toDelete = tourRepository.findOne(activeTour.getId() + 1);
        toDelete.setCargo(activeTour.getCargo());
        toDelete.setNumberOfAnimals(activeTour.getNumberOfAnimals());
        toDelete.setStartPersonSurname(activeTour.getContactPersonSurname());
        
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        //toDelete.setId(activeTour.getId());
        toDelete.setCargo(activeTour.getCargo());
        toDelete.setNumberOfAnimals(activeTour.getNumberOfAnimals());
        toDelete.setStartPersonName(activeTour.getStartPersonName());
        toDelete.setStartPersonSurname(activeTour.getStartPersonSurname());
        toDelete.setStartAddress(activeTour.getStartAddress());
        toDelete.setStartAddressNumber(activeTour.getStartAddressNumber());
        toDelete.setStartZip(activeTour.getStartZip());
        toDelete.setStartCity(activeTour.getStartCity());
        toDelete.setContactPersonName(activeTour.getContactPersonName());
        toDelete.setContactPersonSurname(activeTour.getContactPersonSurname());
        toDelete.setDestinationAddress(activeTour.getDestinationAddress());
        toDelete.setDestinationAddressNumber(activeTour.getDestinationAddressNumber());
        toDelete.setDestinationZip(activeTour.getDestinationZip());
        toDelete.setDestinationCity(activeTour.getDestinationCity());
        toDelete.setDeliveryStartDate(formatterDate.format(activeTour.getDeliveryStartDate()));
        toDelete.setDeliveryStartTime(formatterTime.format(activeTour.getDeliveryStartTime()));
        toDelete.setEstimatedTime(activeTour.getEstimatedTime());
        toDelete.setTimeFrame(activeTour.getTimeFrame());
        toDelete.setDriver(activeTour.getDriver());
        toDelete.setComment(activeTour.getComment());
        
        
        
        tourRepository.save(toDelete);

        tours = tourService.getSortedTours("");

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours");
    }
}
