package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.userdetails.UserDetails;
import spring.entity.*;
import spring.repositories.AddressRepository;
import spring.repositories.AnimalRepository;
import spring.repositories.TourRepository;
import spring.repositories.TruckRepository;
import spring.security.UserSecurityService;
import spring.service.DriverService;
import spring.service.TourService;
import spring.service.TruckService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MINUTES;

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

    @Autowired
    private AnimalRepository animalRepository;


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

        List<Tour> tours = tourService.getCurrentSortedToursForDriver(user.getUsername());
        model.addAttribute("tours", tours);

        return "frontend/myCurrentTours";
    }

    @RequestMapping(value = "/today", method = RequestMethod.POST, params = {"successful=Successful"})
    public String markAsSuccessful(@RequestParam Long tourId, @RequestParam String feedback, ModelMap model) {
        UserDetails user = userSecurityService.getAuthenticatedUser();
        Tour successfulTour = tourRepository.findOne(tourId);

        if (isAllowedToCloseTour(user, successfulTour)) {
            successfulTour.setState(Tour.State.SUCCESSFUL);
            successfulTour.setTourFeedback(feedback);
            successfulTour.setDeliveryTime(computeDeliveryTime(successfulTour));
            tourRepository.save(successfulTour);
            truckService.getById(successfulTour.getTruck().getId()).setAvailable(true);
        }

        List<Tour> tours = tourService.getCurrentToursForDriver(user.getUsername());
        model.addAttribute("tours", tours);
        return "frontend/myCurrentTours";
    }

    @RequestMapping(value = "/today", method = RequestMethod.POST, params = {"failed=Failed"})
    public String markAsFailed(@RequestParam Long tourId, @RequestParam String feedback, ModelMap model) {
        UserDetails user = userSecurityService.getAuthenticatedUser();
        Tour failedTour = tourRepository.findOne(tourId);

        if (isAllowedToCloseTour(user, failedTour)) {
            failedTour.setState(Tour.State.FAILED);
            failedTour.setTourFeedback(feedback);
            failedTour.setDeliveryTime(computeDeliveryTime(failedTour));
            tourRepository.save(failedTour);
            truckService.getById(failedTour.getTruck().getId()).setAvailable(true);
        }

        List<Tour> tours = tourService.getCurrentToursForDriver(user.getUsername());
        model.addAttribute("tours", tours);
        return "frontend/myCurrentTours";
    }


    // Get request on /deliveries will return a form to create a new tour
    @GetMapping(path = "/deliveries")
    public String deliveryForm(ModelMap model) {

        //prepare a list of Drivers to select from.
        List<Driver> drivers = driverService.getDrivers();
        model.addAttribute("drivers", drivers);

        //prepare a list of Animals to select from.
        List<Animal> animalList = animalRepository.findAll();
        model.addAttribute("animals", animalList);

        //prepare a list of Truck types to select from.
        //HashMap<String, Integer> truckTypes = getAvailableTruckTypesAndNumbers("NONE");
        //model.addAttribute("truckTypes", truckTypes);

        if (model.get("tour") == null) {
        	model.addAttribute("tour", new Tour());
        }
        return "backend/deliveries";
    }

    // POST request to /deliveries with the appropriate values will create a new tour and redirect to /tours
    @PostMapping(path = "/deliveries")
    public ModelAndView deliverySubmit(@Valid @ModelAttribute Tour tour,
                                       BindingResult bindingResult,
                                       ModelMap model,
                                       RedirectAttributes redattributes) {
        if (tour.getStartDate() != null && tour.getStartDate().isBefore(LocalDate.now())) {
            bindingResult.addError(new FieldError("tour", "startDate", "Date lies in the past. Enter a valid Date."));
        }
        if (tour.getStartDate() != null && tour.getStartTime() != null
        		&& tour.getStartDate().isEqual(LocalDate.now()) && tour.getStartTime().isBefore(LocalTime.now())) {
            bindingResult.addError(new FieldError("tour", "startTime", "Time already passed. Enter a time in the future"));
        }


        List<Animal> animalList = animalRepository.findAll();

        Truck chosen;

        for (Animal a : animalList) {
            if (Objects.equals(tour.getCargo(), a.getSpecies())) {

                chosen = truckService.findFittingTruck(a, tour.getNumberOfAnimals());

                if (chosen == null) {
                    bindingResult.addError(new FieldError("tour", "cargo", "No Truck can fit all these animals. Please split the tour up into multiple smaller Trucks!"));

                    List<Driver> drivers = driverService.getDrivers();
                    model.addAttribute("drivers", drivers);

                    redattributes.addFlashAttribute("org.springframework.validation.BindingResult.tour", bindingResult);
                    redattributes.addFlashAttribute("tour", tour);

                    return new ModelAndView("redirect:/deliveries");
                } else {
                    tour.setTruck(chosen);
                    chosen.setAvailable(false);
                }
            }
        }


        addressRepository.save(tour.getStartAddress());
        addressRepository.save(tour.getDestinationAddress());

        tourRepository.save(tour);
        return new ModelAndView("redirect:/tours");
    }


    // GET request to /tours will return a list of all tours
    @GetMapping(path = "/tours")
    public String tourOverview(ModelMap model,
                               @RequestParam(required = false, defaultValue = "-1") int activeIndex,
                               @RequestParam(required = false, defaultValue = "Date/Time") String sortBy) {

        List<Tour> tours = tourService.getSortedTours(sortBy);
        if (!sortBy.equals("")) model.addAttribute("sortBy", sortBy);

        model.addAttribute("tours", tours);
        
        Tour activeTour;
        if (model.get("activeTour") == null) {
            activeTour = getTourById(activeIndex, tours);
            model.addAttribute("activeTour", activeTour);
        } else {
            activeTour = (Tour) model.get("activeTour");
        }
        if (activeTour.isSuccessful() || activeTour.isFailed()) {
            model.addAttribute("estimationFeedback", getEstimationFeedback(activeTour));
            model.addAttribute("estimationAccuracyClass", getEstimationAccuracyClass(activeTour));
        }

        //prepare a list of Truck types to select from.
        HashMap<String, Integer> truckTypes = getAvailableTruckTypesAndNumbers(activeTour.getTruck().getTruckType());
        model.addAttribute("truckTypes", truckTypes);

        //prepare a list of Drivers to select from.
        List<Driver> drivers = driverService.getDrivers();
        model.addAttribute("drivers", drivers);

        return "backend/tourOverview";
    }

    // GET request on /tours/delete will delete the tour with the matching index
    @GetMapping(path = "/tours/delete")
    public ModelAndView deleteTour(Model model, @RequestParam(required = true) int index) {

        List<Tour> tours = tourService.getSortedTours("");
        Tour toDelete = getTourById(index, tours);

        Truck truck = toDelete.getTruck();
        truck.setAvailable(true);
        truckRepository.save(truck);
        toDelete.setState(Tour.State.DELETED);
        tourRepository.save(toDelete);

        tours.remove(toDelete);

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours");
    }

    @PostMapping(path = "/tours/update")
    public ModelAndView updateTour(@Valid @ModelAttribute Tour activeTour,
                                   BindingResult bindingResult,
                                   ModelMap model,
                                   @RequestParam("truckType") String truckType,
                                   RedirectAttributes redattributes) {

        // Attach "old" truck to activeTour (isn't done automatically, since the select truck input is not associated
        // with the tour anymore).
        Truck truck = tourRepository.findOne(activeTour.getId()).getTruck();
        activeTour.setTruck(truck);

        if (activeTour.getStartDate().isBefore(LocalDate.now())) {
            bindingResult.addError(new FieldError("tour", "startDate", "Date lies in the past. Enter a valid Date."));
        }
        if (activeTour.getStartDate().isEqual(LocalDate.now()) && activeTour.getStartTime().isBefore(LocalTime.now())) {
            bindingResult.addError(new FieldError("tour", "startTime", "Time already passed. Enter a time in the future"));
        }

        boolean truckError = truckType == null || (truckService.getAvailableTrucksOfType(truckType).isEmpty() && !truck.getTruckType().equals(truckType));
        if (bindingResult.hasErrors() || truckError) {
            if (truckError) {
                redattributes.addFlashAttribute("truckErrorMessage",
                        "The chosen truck type is not available anymore, please try choosing another truck type.");
            }
            
            redattributes.addFlashAttribute("org.springframework.validation.BindingResult.activeTour", bindingResult);
            redattributes.addFlashAttribute("activeTour", activeTour);
            
            return new ModelAndView("redirect:/tours?activeIndex=" + activeTour.getId());
        }

        Tour oldTour = tourRepository.findOne(activeTour.getId());

        if (!oldTour.getTruck().getTruckType().equals(truckType)) {
            oldTour.getTruck().setAvailable(true);
            Truck chosen = truckService.getAvailableTrucksOfType(truckType).get(0);
            oldTour.setTruck(chosen);
            chosen.setAvailable(false);
        }

        oldTour.setCargo(activeTour.getCargo());
        oldTour.setNumberOfAnimals(activeTour.getNumberOfAnimals());

        Address startAddress = oldTour.getStartAddress();
        Address destinationAddress = oldTour.getDestinationAddress();
        startAddress.copyFieldsFromAddress(activeTour.getStartAddress());
        destinationAddress.copyFieldsFromAddress(activeTour.getDestinationAddress());

        oldTour.setStartDate(activeTour.getStartDate());
        oldTour.setStartTime(activeTour.getStartTime());
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
     *
     * @param user the <code>UserDetails</code> of the <code>User</code>
     * @param tour the <code>Tour</code> that the <code>User</code> wants to close
     * @return <code>true</code> if the <code>User</code> is allowed to close the <code>Tour</code>,
     * <br><code>false</code> otherwise.
     */
    private boolean isAllowedToCloseTour(UserDetails user, Tour tour) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalDate tourStartDate = tour.getStartDate();
        LocalTime tourStartTime = tour.getStartTime();
        String username = user.getUsername();
        return (tour.getDriver().equals(username) && tourStartDate.isEqual(today) && tourStartTime.isBefore(now));
    }

    // Return a HashMap with available, distinct truck types and the amount of available trucks.
    // The current truck type will also be in the HashMap, even if there are no more trucks of this truck type left
    // (since the truck is already assigned to the tour).
    private HashMap<String, Integer> getAvailableTruckTypesAndNumbers(String currentTruckType) {
        List<Truck> trucks = truckService.getAvailableTrucks();
        HashMap<String, Integer> truckTypes = new HashMap<>();
        if (!currentTruckType.equals("NONE")) {
            truckTypes.put(currentTruckType, 1);
        }
        for (Truck t : trucks) {
            String type = t.getTruckType();
            if (truckTypes.containsKey(type)) {
                int amount = truckTypes.get(type);
                truckTypes.replace(type, amount + 1);
            } else {
                truckTypes.put(type, 1);
            }
        }
        return truckTypes;
    }

    private String getEstimationFeedback(Tour tour) {
        long percentage = getEstimationPercentage(tour);
        long absolutePercentage = Math.abs(percentage);

        if (percentage > 0) {
            if (percentage <= 25) {
                return "Your estimation was quite good. The tour took "
                        + percentage + "% less time than expected.";
            } else if (percentage <= 50) {
                return "The delivery was faster than you expected. The tour took "
                        + percentage + "% less time than estimated.";
            } else {
                return "What happened? Your estimation was way bigger than the actual delivery time. " +
                        "The tour took " + percentage + "% less time than expected.";
            }
        } else {
            if (absolutePercentage <= 25) {
                return "Your estimation was quite good. The tour took "
                        + absolutePercentage + "% more time than expected.";
            } else if (absolutePercentage <= 50) {
                return "The delivery was slower than expected. The tour took "
                        + absolutePercentage + "% more time than estimated.";
            } else {
                return "What happened? Your estimation was way smaller than the actual delivery time. "
                        + "The tour took " + absolutePercentage + "% more time than expected.";
            }
        }
    }

    private String getEstimationAccuracyClass(Tour tour) {
        long absolutePercentage = Math.abs(getEstimationPercentage(tour));

        if (absolutePercentage <= 25) {
            return "is-success";
        } else if (absolutePercentage <=  50) {
            return "is-warning";
        } else {
            return "is-danger";
        }
    }

    private long getEstimationPercentage(Tour tour) {
        Double difference = tour.getEstimatedTime() - tour.getDeliveryTime();
        return Math.round(difference / tour.getEstimatedTime() * 100);
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

    private double computeDeliveryTime(Tour tour) {
        LocalDateTime start = LocalDateTime.of(tour.getStartDate(), tour.getStartTime());
        LocalDateTime now = LocalDateTime.now();
        double hours = (float) Duration.between(start, now).toMinutes() / 60;
        return Math.round(hours*100.0)/100.0;
    }
}
