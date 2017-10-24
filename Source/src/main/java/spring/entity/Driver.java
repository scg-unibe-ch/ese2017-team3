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
}
