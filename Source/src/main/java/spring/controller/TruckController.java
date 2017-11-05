package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.entity.Truck;
import spring.repositories.TruckRepository;
import spring.service.TruckService;

import java.util.List;

/**
 * Created by olulrich on 05.11.17.
 */

@Controller
public class TruckController {

    @Autowired
    TruckService truckService;

    @GetMapping(path = "/trucks")
    public String truckOverview(Model model) {

        List<Truck> truckList = truckService.getTrucks();

        model.addAttribute("trucks", truckList);

        return "backend/trucks";
    }
}
