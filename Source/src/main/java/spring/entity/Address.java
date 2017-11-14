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
    private String name;

    @Size(min = 1, message = "Please specify the surname of the contact person at the start location.")
    private String surname;

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

    private int phone = -1;

    private String email = "";

    public Address() {
    }

    public Address(String name, String surname, String street, String streetNumber, int zip, String city, int phone, String email) {
        this.name = name;
        this.surname = surname;
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
        return name + " " + surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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


    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void copyFieldsFromAddress(Address anotherAddress) {
        this.setName(anotherAddress.getName());
        this.setSurname(anotherAddress.getSurname());
        this.setStreet(anotherAddress.getStreet());
        this.setStreetNumber(anotherAddress.getStreetNumber());
        this.setZip(anotherAddress.getZip());
        this.setCity(anotherAddress.getCity());

    }
}
