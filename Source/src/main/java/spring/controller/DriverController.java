package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Driver;
import spring.entity.Tour;
import spring.repositories.AddressRepository;
import spring.repositories.DriverRepository;
import spring.repositories.TourRepository;
import spring.security.UserSecurityService;
import spring.service.DriverService;

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
    TourRepository tourRepository;
    
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
        List<Tour> toursOfDeletedDriver = tourRepository.findByDriver(username);
        
        for(Tour tour:toursOfDeletedDriver){
            tour.setTourState(Tour.TourState.INCOMPLETE);
        }
        
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
}
