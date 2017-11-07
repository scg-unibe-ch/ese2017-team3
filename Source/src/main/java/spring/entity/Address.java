package spring.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Address {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

}
