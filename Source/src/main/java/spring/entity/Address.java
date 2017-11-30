package spring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min = 1, message = "Please specify the name of the contact person at the start location.")
    private String lastname;

    @Size(min = 1, message = "Please specify the surname of the contact person at the start location.")
    private String firstname;

    @Size(min = 1, message = "Please specify the street name of the start location.")
    private String street;

    @Size(min = 1, message = "Please specify the street number of the start location.")
    private String streetNumber;

    @NotNull(message = "Please specify the ZIP code of the start location.")
    @Min(value = 1000, message = "You entered an invalid ZIP code.")
    @Max(value = 9999, message = "You entered an invalid ZIP code.")
    private int zip;

    @Size(min = 1, message = "Please specify the city of the start location.")
    private String city;

    private String phone = "";

    private String email = "";

    public Address() {
    }

    public Address(String lastname, String firstname, String street, String streetNumber, int zip, String city, String phone, String email) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zip = zip;
        this.city = city;

        this.phone = phone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getPerson() {
        return lastname + " " + firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public int getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setStreet(String address) {
        this.street = address;
    }

    public void setStreetNumber(String addressNumber) {
        this.streetNumber = addressNumber;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void copyFieldsFromAddress(Address anotherAddress) {
        this.setLastname(anotherAddress.getLastname());
        this.setFirstname(anotherAddress.getFirstname());
        this.setStreet(anotherAddress.getStreet());
        this.setStreetNumber(anotherAddress.getStreetNumber());
        this.setZip(anotherAddress.getZip());
        this.setCity(anotherAddress.getCity());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (getId() != address.getId()) return false;
        if (getZip() != address.getZip()) return false;
        if (getLastname() != null ? !getLastname().equals(address.getLastname()) : address.getLastname() != null)
            return false;
        if (getFirstname() != null ? !getFirstname().equals(address.getFirstname()) : address.getFirstname() != null)
            return false;
        if (getStreet() != null ? !getStreet().equals(address.getStreet()) : address.getStreet() != null) return false;
        if (getStreetNumber() != null ? !getStreetNumber().equals(address.getStreetNumber()) : address.getStreetNumber() != null)
            return false;
        if (getCity() != null ? !getCity().equals(address.getCity()) : address.getCity() != null) return false;
        if (getPhone() != null ? !getPhone().equals(address.getPhone()) : address.getPhone() != null) return false;
        return getEmail() != null ? getEmail().equals(address.getEmail()) : address.getEmail() == null;
    }

}
