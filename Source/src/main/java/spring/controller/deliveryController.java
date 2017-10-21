package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.entity.delivery;
import spring.repositories.DeliveryRepository;
import spring.service.deliveryService;

import java.util.ArrayList;

/**
 * Created by olulrich on 20.10.17.
 */

@Controller
public class deliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping(path = "/deliveries")
    public String deliveryForm(Model model) {

        model.addAttribute("delivery", new delivery());
        return "deliveries";
    }

    @PostMapping(path = "/deliveries")
    public String deliverySubmit(@ModelAttribute delivery delivery, Model model) {
        deliveryRepository.save(new delivery(delivery.getCargo()));
        model.addAttribute("deliveries", deliveryRepository.findAll());
        return "deliveryresult";
    }
}
