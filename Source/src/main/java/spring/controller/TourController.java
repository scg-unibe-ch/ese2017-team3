package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Tour;
import spring.repositories.TourRepository;
import spring.service.TourService;

import java.util.List;

/**
 * Created by olulrich on 20.10.17.
 */

@Controller
public class TourController {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourService tourService;

    // Get request on /deliveries will return a form to create a new tour
    @GetMapping(path = "/deliveries")
    public String deliveryForm(Model model) {

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
        tourRepository.delete(toDelete);

        tourRepository.save(activeTour);

        tours = tourService.getTours();

        model.addAttribute("tours", tours);
        return new ModelAndView("redirect:/tours");
    }
}
