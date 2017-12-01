package spring.entity;

import org.springframework.security.core.userdetails.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;

public class UserRegistrationWrapper {

//    @NotNull
//    @Pattern(regexp = "^asdf123", message = "Not a valid code")
    public String regCode;

    @Valid
    public Address address;

    public String username;
    public String password;

    public UserRegistrationWrapper(String username, String password, String regCode) {
        this.username = username;
        this.password = password;
        this.regCode = regCode;
    }

    public UserRegistrationWrapper() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }
}
