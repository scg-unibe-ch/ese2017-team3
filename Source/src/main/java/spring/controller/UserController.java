package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.entity.Address;
import spring.entity.Driver;
import spring.entity.WrappedRegistration;
import spring.repositories.AddressRepository;
import spring.repositories.DriverRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;


@Controller
@RequestMapping(path = "/user")
public class UserController {
    
    @Autowired
    private UserDetailsManager userDetailsManager;
    
    @Autowired
    private DriverRepository driverRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @PreAuthorize("@userSecurityService.canCreate()")
    @GetMapping(path = "/register")
    public ModelAndView createForm() {
        return new ModelAndView("RegistrationForm", "wrappedRegistration", new WrappedRegistration());
        
    }
    
    @PreAuthorize("@userSecurityService.canCreate()")
    @PostMapping(path = "/register")
    public ModelAndView create(@Valid @ModelAttribute WrappedRegistration wrappedRegistration, /*@RequestParam String username, @RequestParam String password, @RequestParam String regCode,*/ BindingResult bindingResult) {
        
        
        if(wrappedRegistration.username.length() <= 6) {
            bindingResult.addError(new FieldError("wrappedRegistration", "username", "username must be at least 6 characters long"));
        }
    
        if(userDetailsManager.userExists(wrappedRegistration.username)) {
            bindingResult.addError(new FieldError("wrappedRegistration","username", "user with username " + wrappedRegistration.username + " already exists"));
        }
        
        if(wrappedRegistration.password.length() <= 6) {
            bindingResult.addError(new FieldError("wrappedRegistration","password", "password must be at least 6 characters long"));
        }
        
        if(!wrappedRegistration.regCode.equals("") && !wrappedRegistration.regCode.equals("asdf123")) {
            bindingResult.addError(new FieldError("wrappedRegistration","regCode", "this registration code you entered doesn't exist."));
        }
        
        if(wrappedRegistration.address.getEmail().length()<=5){
            bindingResult.addError(new FieldError("wrappedRegistration", "address.email","E-Mail address must no be void!"));
        }
        
        if(wrappedRegistration.address.getPhone().length()<=5){
            bindingResult.addError(new FieldError("wrappedRegistration", "address.phone", "Phone number must not be void!"));
        }
        
        if(bindingResult.hasErrors()) {
            return new ModelAndView("RegistrationForm", "wrappedRegistration", wrappedRegistration);
        }
        
            // NOTE users need an authority, otherwise they are treated as non-existing

        
        if(wrappedRegistration.regCode.equals("asdf123")) {
            User user = new User(wrappedRegistration.username, wrappedRegistration.password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            userDetailsManager.createUser(user);
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new ModelAndView("redirect:/tours");
        } else {
            User user = new User(wrappedRegistration.username, wrappedRegistration.password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_DRIVER")));
            userDetailsManager.createUser(user);
            Address address = wrappedRegistration.address;
            
            Driver driver = new Driver();
            driver.setUsername(wrappedRegistration.username);
            driver.setHiringDate(LocalDate.now());
            driver.setAddress(address);
            
            addressRepository.save(address);
            driverRepository.save(driver);
            
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new ModelAndView("redirect:/today");
        }
    }
    
    @PreAuthorize("@userSecurityService.canRead(#username)")
    @GetMapping(path = "/{username}")
    public ModelAndView read(@PathVariable(value = "username") String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        return new ModelAndView("user/read", "user", user);
    }
    
    @PreAuthorize("@userSecurityService.canUpdate(#username)")
    @PutMapping(path = "/{username}")
    public ModelAndView update(@PathVariable(value = "username") String username, @RequestParam String password) {
        String oldPassword = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword();
        userDetailsManager.changePassword(oldPassword, password);
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        return new ModelAndView("redirect:/user/{username}", "username", user.getUsername());
    }

//    @PreAuthorize("@userSecurityService.canDelete(#username)")
//    @DeleteMapping(path = "/{username}")
//    public ModelAndView delete(@PathVariable(value = "username") String username) {
//        userDetailsManager.deleteUser(username);
//        return new ModelAndView("redirect:/");
//    }

}
