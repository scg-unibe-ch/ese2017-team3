package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.entity.Delivery;
import spring.repositories.DeliveryRepository;
import spring.service.DeliveryService;

import java.util.ArrayList;

/**
 * Created by olulrich on 20.10.17.
 */

@Controller
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping(path = "/deliveries")
    public String deliveryForm(Model model) {

        model.addAttribute("delivery", new Delivery());
        return "deliveries";
    }

    @PostMapping(path = "/deliveries")
    public String deliverySubmit(@ModelAttribute Delivery delivery, Model model) {
        deliveryRepository.save(new Delivery(delivery.getCargo()));
        model.addAttribute("deliveries", deliveryRepository.findAll());
        return "deliveryresult";
    }
}
