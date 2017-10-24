package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.entity.Driver;
import spring.repositories.DriverRepository;
import spring.service.DriverService;

import java.util.List;

/**
 * Created by olulrich on 24.10.17.
 */

@Controller
public class DriverController {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    DriverService driverService;

    @GetMapping(path = "/drivers")
    public String driverOverview(Model model) {

        List<Driver> drivers = driverService.getDrivers();

        model.addAttribute("drivers", drivers);

        return "drivers";
    }
}
