package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Address;
import spring.entity.Driver;
import spring.repositories.AddressRepository;
import spring.repositories.DriverRepository;
import spring.security.UserSecurityService;
import spring.service.DriverService;

import javax.validation.Valid;
import java.util.Collections;
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
    AddressRepository addressRepository;

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    private UserSecurityService userSecurityService;


    @GetMapping(path = "/drivers")
    public String driverOverview(Model model) {

        List<Driver> drivers = driverService.getDrivers();

        model.addAttribute("drivers", drivers);

        return "backend/drivers";
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

    @GetMapping(path = "/drivers/promote")
    public ModelAndView promoteDriver(Model model, @RequestParam String username) {

        List<Driver> drivers = driverService.getDrivers();

        Driver promotedDriver = driverRepository.findDriverByUsername(username);
        driverRepository.delete(promotedDriver);

        UserDetails driverWithNewRole = userDetailsManager.loadUserByUsername(username);

        userDetailsManager.deleteUser(username);

        User user = new User(driverWithNewRole.getUsername(), driverWithNewRole.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        userDetailsManager.createUser(user);

        model.addAttribute("drivers", drivers);
        return new ModelAndView("redirect:/drivers");
    }

    @GetMapping(path = "/address")
    public String setupAddressData(Model model) {

        model.addAttribute("address", new Address());

        return "AddressForm";
    }


    // POST request to /deliveries with the appropriate values will create a new tour and redirect to /tours
    @PostMapping(path = "/address")
    public ModelAndView addressSubmit(@Valid @ModelAttribute Address address, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("AddressForm");
        }
        UserDetails user = userSecurityService.getAuthenticatedUser();

        Driver driver = driverRepository.findDriverByUsername(user.getUsername());

        driver.setAddress(address);
        addressRepository.save(address);
        driverRepository.save(driver);
        return new ModelAndView("redirect:/today");
    }
}
