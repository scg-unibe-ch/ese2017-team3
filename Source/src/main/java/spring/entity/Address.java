package spring.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Address {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@Column (nullable = true)
	private String personName;

	@Column (nullable = true)
	private String personSurname;

	@Column (nullable = true)
    private String street;

    // Street number of start location
    @Column (nullable = true)
    private String streetNumber;

    @Column (nullable = true)
    private int zip;

    @Column (nullable = true)
    private String city;

    
    public long getId() {
		return id;
	}
    
    public String getPerson() {
    	return personName + " " + personSurname;
    }
    
	public String getPersonName() {
		return personName;
	}

	public String getPersonSurname() {
		return personSurname;
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
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public void setPersonSurname(String personSurname) {
		this.personSurname = personSurname;
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
