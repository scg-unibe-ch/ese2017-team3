package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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

    @Autowired
    UserDetailsManager userDetailsManager;

    @GetMapping(path = "/drivers")
    public String driverOverview(Model model) {

        List<Driver> drivers = driverService.getDrivers();

        model.addAttribute("drivers", drivers);

        return "drivers";
    }

    @GetMapping(path = "/drivers/delete")
    public ModelAndView deleteDriver(Model model, @RequestParam String username) {

        List<Driver> drivers = driverService.getDrivers();

        Driver toDelete = driverRepository.findDriverByUsername(username);
        driverRepository.delete(toDelete);

        userDetailsManager.deleteUser(username);

        model.addAttribute("drivers", drivers);
        return new ModelAndView("redirect:/drivers");
    }
}
