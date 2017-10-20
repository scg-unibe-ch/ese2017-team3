package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.entity.delivery;
import spring.service.deliveryService;

import java.util.ArrayList;

/**
 * Created by olulrich on 20.10.17.
 */

@RestController
public class deliveryController {

    @Autowired
    private deliveryService deliveryService;

    @RequestMapping(method = RequestMethod.GET, value = "/deliveries")
    public String getAllDeliveries(@RequestParam(value = "cargo_1", required = false, defaultValue = "unknown") Model model) {

        ArrayList<delivery> deliveries = deliveryService.getAllDeliveries();

        model.addAttribute("cargo_1", deliveries.get(0).getCargo());

        return "deliveries";
    }
}
