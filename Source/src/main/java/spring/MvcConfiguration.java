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
        if (checkIfAdmin()){
            registry.addViewController("/").setViewName("tourOverview");
        }else{
            registry.addViewController("/").setViewName();
        }


        registry.addViewController("/login").setViewName("user/LoginForm");
        registry.addViewController("/error").setViewName("error");
    }

    private boolean checkIfAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = userDetailsManager.loadUserByUsername(authentication.getName());
        for (GrantedAuthority userPermissions : currentUser.getAuthorities()){
            if(userPermissions.getAuthority().equals("ADMIN")){
                return true;
            }

        }
        return false;

    }


}
