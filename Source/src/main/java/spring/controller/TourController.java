package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.entity.Tour;
import spring.repositories.TourRepository;

/**
 * Created by olulrich on 20.10.17.
 */

@Controller
public class TourController {

    @Autowired
    private TourRepository tourRepository;

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
    	model.addAttribute("deliveries", tourRepository.findAll());
    	return "tourOverview";
    }
}
