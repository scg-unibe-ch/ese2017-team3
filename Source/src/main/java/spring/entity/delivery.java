package spring.entity;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by olulrich on 20.10.17.
 */
@Entity
public class delivery {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column (nullable = false)
    private String cargo;

    @Column (nullable = true)
    private int numberOfAnimals;

    @Column (nullable = true)
    private String startAddress;

    // Street number of start location
    @Column (nullable = true)
    private int startAddressNumber;

    @Column (nullable = true)
    private int startZIP;

    @Column (nullable = true)
    private String startCity;

    @Column (nullable = true)
    private String destinationAddress;

    @Column (nullable = true)
    private int destinationAddressNumber;

    @Column (nullable = true)
    private int destinationZIP;

    @Column (nullable = true)
    private String destinationCity;

    @Column (nullable = true)
    private String contactPersonName;

    @Column (nullable = true)
    private String contactPersonSurname;

    @Column (nullable = true)
    private float estimatedTime;

    // Calendar object that contains the date and the time when the delivery should be started
    @Column (nullable = true)
    private Calendar startingTime;


    public delivery(String cargo) {
        this.cargo = cargo;
    }

    public delivery(){}

    // Setter methods

    public void setId(int id) {
        this.id = id;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setNumberOfAnimals(int numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public void setStartAddressNumber(int startAddressNumber) {
        this.startAddressNumber = startAddressNumber;
    }

    public void setStartZIP(int startZIP) {
        this.startZIP = startZIP;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setDestinationAddressNumber(int destinationAddressNumber) {
        this.destinationAddressNumber = destinationAddressNumber;
    }

    public void setDestinationZIP(int destinationZIP) {
        this.destinationZIP = destinationZIP;
    }

    public void setEstimatedTime(float estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setStartingTime(Calendar startingTime) {
        this.startingTime = startingTime;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public void setContactPersonSurname(String contactPersonSurname) {
        this.contactPersonSurname = contactPersonSurname;
    }

    // Getter methods

    public int getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public int getStartAddressNumber() {
        return startAddressNumber;
    }

    public int getStartZIP() {
        return startZIP;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public int getDestinationAddressNumber() {
        return destinationAddressNumber;
    }

    public int getDestinationZIP() {
        return destinationZIP;
    }

    public float getEstimatedTime() {
        return estimatedTime;
    }

    public Calendar getStartingTime() {
        return startingTime;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public String getContactPersonSurname() {
        return contactPersonSurname;
    }
}
