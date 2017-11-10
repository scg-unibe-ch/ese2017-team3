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

    @Column
    private String username;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (getId() != driver.getId()) return false;
        if (getUsername() != null ? !getUsername().equals(driver.getUsername()) : driver.getUsername() != null)
            return false;
        if (getName() != null ? !getName().equals(driver.getName()) : driver.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(driver.getSurname()) : driver.getSurname() != null)
            return false;
        return hiringDate != null ? hiringDate.equals(driver.hiringDate) : driver.hiringDate == null;
    }

}
