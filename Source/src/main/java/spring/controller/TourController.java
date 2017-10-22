package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(path = "/deliveries")
    public String deliveryForm(Model model) {

        model.addAttribute("tour", new Tour());
        return "deliveries";
    }

    @PostMapping(path = "/deliveries")
    public String deliverySubmit(@ModelAttribute Tour tour, Model model) {
        tourRepository.save(tour);
        model.addAttribute("deliveries", tourRepository.findAll());
        return "deliveryresult";
    }
    @RequestMapping(path = "/tours")
    public String tourOverview(Model model) {
        List<Tour> tours = tourService.getTours();
    	model.addAttribute("deliveries", tours);
    	return "tourOverview";
    }
}
