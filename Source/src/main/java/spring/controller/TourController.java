package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.entity.Driver;
import spring.entity.Tour;
import spring.repositories.AddressRepository;
import spring.repositories.TourRepository;
import spring.security.UserSecurityService;
import spring.service.DriverService;
import spring.service.TourService;

import javax.validation.Valid;
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
    private AddressRepository addressRepository;

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
    public ModelAndView deliverySubmit(@Valid @ModelAttribute Tour tour, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            List<Driver> drivers = driverService.getDrivers();
            model.addAttribute("drivers", drivers);
            return new ModelAndView("backend/deliveries");
        }
        addressRepository.save(tour.getStartAddress());
        addressRepository.save(tour.getDestinationAddress());
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
        Tour activeTour = getTourById(activeIndex, tours);
        model.addAttribute("activeTour", activeTour);
        return "backend/tourOverview";
    }

    // GET request on /tours/delete will delete the tour with the matching index
    @GetMapping(path = "/tours/delete")
    public ModelAndView deleteTour(Model model, @RequestParam(required = true) int index) {

        List<Tour> tours = tourService.getSortedTours("");
        Tour toDelete = getTourById(index, tours);

        toDelete.setTourState(Tour.TourState.DELETED);
        tourRepository.save(toDelete);

        tours.remove(toDelete);

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours");
    }

    @PostMapping(path = "/tours/update")
    public ModelAndView updateTour(@Valid @ModelAttribute Tour activeTour, BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/tours?activeIndex=" + activeTour.getId());
        }

        Tour oldTour = tourRepository.findOne(activeTour.getId());

        // The following attributes are not changed, because they are not part of the form in tourOverview
        //toDelete.setId(activeTour.getId());
        //oldTour.setEstimatedTime(activeTour.getEstimatedTime());
        //oldTour.setTimeFrame(activeTour.getTimeFrame());
        oldTour.setCargo(activeTour.getCargo());
        oldTour.setNumberOfAnimals(activeTour.getNumberOfAnimals());

        oldTour.setStartAddress(activeTour.getStartAddress());
        oldTour.setDestinationAddress(activeTour.getDestinationAddress());
//        oldTour.setStartPersonName(activeTour.getStartPersonName());
//        oldTour.setStartPersonSurname(activeTour.getStartPersonSurname());
//        oldTour.setStartAddress(activeTour.getStartAddress());
//        oldTour.setStartAddressNumber(activeTour.getStartAddressNumber());
//        oldTour.setStartZip(activeTour.getStartZip());
//        oldTour.setStartCity(activeTour.getStartCity());
//        oldTour.setContactPersonName(activeTour.getContactPersonName());
//        oldTour.setContactPersonSurname(activeTour.getContactPersonSurname());
//        oldTour.setDestinationAddress(activeTour.getDestinationAddress());
//        oldTour.setDestinationAddressNumber(activeTour.getDestinationAddressNumber());
//        oldTour.setDestinationZip(activeTour.getDestinationZip());
//        oldTour.setDestinationCity(activeTour.getDestinationCity());

        oldTour.setDeliveryStartDate(activeTour.getDeliveryStartDate());
        oldTour.setDeliveryStartTime(activeTour.getDeliveryStartTime());
        oldTour.setDriver(activeTour.getDriver());
        oldTour.setComment(activeTour.getComment());

        tourRepository.save(oldTour);

        List<Tour> tours = tourService.getSortedTours("");

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours?activeIndex=" + activeTour.getId());
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
