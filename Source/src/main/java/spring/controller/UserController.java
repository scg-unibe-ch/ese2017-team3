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
        return new ModelAndView("RegistrationForm", "wrappedUser", new WrappedRegistration());

    }

    @PreAuthorize("@userSecurityService.canCreate()")
    @PostMapping(path = "/register")
    public ModelAndView create(@Valid @ModelAttribute WrappedRegistration wrappedRegistration, /*@RequestParam String username, @RequestParam String password, @RequestParam String regCode,*/ BindingResult bindingResult) {

        // NOTE users need an authority, otherwise they are treated as non-existing

        if (wrappedRegistration.password.length() <= 6 || wrappedRegistration.username.length() <= 6)
            bindingResult.addError(new ObjectError("username", "username must be at least 6 characters"));
        bindingResult.addError(new ObjectError("password", "password must be at least 6 characters"));

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/register");
        }

        if (!wrappedRegistration.regCode.equals("") && !wrappedRegistration.regCode.equals("asdf123")) {
            return new ModelAndView("error");
        }

        if (userDetailsManager.userExists(wrappedRegistration.username)) {
            return new ModelAndView("duplicateUser");

        } else if (wrappedRegistration.regCode.equals("asdf123")) {
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
            return new ModelAndView("redirect:/address");
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
        String oldPassword = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getPassword();
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
