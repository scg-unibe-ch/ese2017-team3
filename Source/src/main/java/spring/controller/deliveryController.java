package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.entity.delivery;
import spring.service.deliveryService;

import java.util.ArrayList;

/**
 * Created by olulrich on 20.10.17.
 */

@Controller
public class deliveryController {

    @Autowired
    private deliveryService deliveryService;

    @GetMapping("/deliveries")
    public String deliveryForm(Model model) {
        model.addAttribute("delivery", new delivery());
        return "deliveries";
    }

    @PostMapping("deliveries")
    public String deliverySubmit(@ModelAttribute delivery delivery) {
        return "deliveryresult";
    }
}
