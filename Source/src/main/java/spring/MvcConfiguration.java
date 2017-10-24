package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import spring.security.UserSecurityService;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    UserDetailsManager userDetailsManager;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (checkIfAdmin())
            registry.addViewController("/").setViewName("tourOverview");
        registry.addViewController("/login").setViewName("user/LoginForm");
    }

    private boolean checkIfAdmin(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDetailsManager.loadUserByUsername(authentication.getName());
        currentUser.getAuthorities();
        String name = auth.getName();
        if (GrantedAuthority userAuthorities = userDetailsManager.loadUserByUsername(name).getAuthorities())
             //get logged in username
    }


}
