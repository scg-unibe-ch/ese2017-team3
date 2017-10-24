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

import java.time.format.DateTimeFormatter;
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

    @GetMapping(path = "/today")
    public String myCurrentTours(Model model) {
        UserDetails user = userSecurityService.getAuthenticatedUser();
        List<Tour> tours = tourService.getToursForDriver(user.getUsername());

        model.addAttribute("tours", tours);

        return "myCurrentTours";
    }

    // Get request on /deliveries will return a form to create a new tour
    @GetMapping(path = "/deliveries")
    public String deliveryForm(Model model) {

        //prepare a list of Drivers to select from.
        List<Driver> drivers = driverService.getDrivers();
        model.addAttribute("drivers", drivers);

        model.addAttribute("tour", new Tour());
        return "deliveries";
    }

    // POST request to /deliveries with the appropriate values will create a new tour and redirect to /tours
    @PostMapping(path = "/deliveries")
    public String deliverySubmit(@ModelAttribute Tour tour, Model model) {
        tourRepository.save(tour);
        List<Tour> tours = tourService.getTours();
        
        Tour activeTour = tours.get(0);
    	model.addAttribute("activeTour", activeTour);
        
        model.addAttribute("tours", tours);
        return "tourOverview";
    }

    // GET request to /tours will return a list of all tours
    @GetMapping(path = "/tours")
    public String tourOverview(Model model, @RequestParam(required = false, defaultValue = "0") int activeIndex) {

        List<Tour> tours = tourService.getTours();
    	model.addAttribute("tours", tours);

    	if (tours.size() == 0) {
    		model.addAttribute("activeTour", null);
    	} else {
    		Tour activeTour = tours.get(activeIndex);
        	model.addAttribute("activeTour", activeTour);
    	}

    	return "tourOverview";
    }

    // GET request on /tours/delete will delete the tour with the matching index
    @GetMapping(path = "/tours/delete")
    public ModelAndView deleteTour(Model model, @RequestParam(required = true) int index) {

        List<Tour> tours = tourService.getTours();
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
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        toDelete.setId(activeTour.getId());
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
        toDelete.setDeliveryStartDate(formatter.format(activeTour.getDeliveryStartDate()));
        toDelete.setDeliveryStartTime(formatter.format(activeTour.getDeliveryStartTime()));
        toDelete.setEstimatedTime(activeTour.getEstimatedTime());
        toDelete.setTimeFrame(activeTour.getTimeFrame());
        toDelete.setDriver(activeTour.getDriver());
        toDelete.setComment(activeTour.getComment());
        
        
        
        
        
        
        
        tourRepository.save(toDelete);

        tours = tourService.getTours();

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours");
    }
}
