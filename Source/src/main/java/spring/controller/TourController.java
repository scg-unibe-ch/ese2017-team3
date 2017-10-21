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

        model.addAttribute("delivery", new Tour());
        return "deliveries";
    }

    @PostMapping(path = "/deliveries")
<<<<<<< HEAD:Source/src/main/java/spring/controller/TourController.java
    public String deliverySubmit(@ModelAttribute Tour delivery, Model model) {
        tourRepository.save(new Tour(delivery.getCargo()));
        model.addAttribute("deliveries", tourRepository.findAll());
=======
    public String deliverySubmit(@ModelAttribute Delivery delivery, Model model) {
        deliveryRepository.save(delivery);
        model.addAttribute("deliveries", deliveryRepository.findAll());
>>>>>>> 3137570e89e8c3ebc5fc62aa728861d2c4e40131:Source/src/main/java/spring/controller/DeliveryController.java
        return "deliveryresult";
    }
}
