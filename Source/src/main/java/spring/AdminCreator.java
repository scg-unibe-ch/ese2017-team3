package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminCreator implements ApplicationRunner {

    @Autowired
    private UserDetailsManager userDetailsManager;

    public void run(ApplicationArguments args) {

        if (!userDetailsManager.userExists("admin")) {
            User admin = new User("admin", "anitrans", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            userDetailsManager.createUser(admin);
        }
    }
}
