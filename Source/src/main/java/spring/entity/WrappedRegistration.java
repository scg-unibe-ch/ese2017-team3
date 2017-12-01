package spring.entity;

import javax.validation.Valid;

public class WrappedRegistration {

    public String regCode;
    public String username;
    public String password;
    @Valid
    public Address address;

    public WrappedRegistration(String username, String password, String regCode, Address address) {
        this.username = username;
        this.password = password;
        this.regCode = regCode;
        this.address = address;
    }

    public WrappedRegistration() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
