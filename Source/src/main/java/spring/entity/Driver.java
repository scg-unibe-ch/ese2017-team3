package spring.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by olulrich on 24.10.17.
 */

@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String username;

    @OneToOne
    private Address address;


    private LocalDate hiringDate;

    public Driver() {}

    public Driver(String username) {
        this.username = username;
    }

    public String getHiringDateString() {
        return hiringDate.toString();
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (getId() != driver.getId()) return false;
        if (getUsername() != null ? !getUsername().equals(driver.getUsername()) : driver.getUsername() != null)
            return false;
        if (address.getLastname() != null ? !address.getLastname().equals(driver.address.getLastname()) : driver.address.getLastname() != null) return false;
        if (address.getFirstname() != null ? !address.getFirstname().equals(driver.address.getFirstname()) : driver.address.getFirstname() != null)
            return false;
        return hiringDate != null ? hiringDate.equals(driver.hiringDate) : driver.hiringDate == null;
    }

}
