package spring.entity;

import javax.persistence.*;

@Entity
public class Address {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
	
	@Column (nullable = true)
	private String name;

	@Column (nullable = true)
	private String surname;

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
