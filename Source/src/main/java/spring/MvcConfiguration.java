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
import spring.security.AuthSuccessHandler;
import spring.security.UserSecurityService;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/tourOverview").setViewName("tourOverview");
        registry.addViewController("/login").setViewName("LoginForm");
        registry.addViewController("/error").setViewName("error");
    }
}
