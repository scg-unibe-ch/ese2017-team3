package spring.entity;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by olulrich on 20.10.17.
 */
@Entity
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = true)
    private int numberOfAnimals;


    @Column(nullable = true)
    private String startAddress;

    // Street number of start location
    @Column(nullable = true)
    private int startAddressNumber;

    @Column(nullable = true)
    private int startZIP;

    @Column(nullable = true)
    private String startCity;

    @Column(nullable = true)
    private String destinationAddress;

    @Column(nullable = true)
    private int destinationAddressNumber;

    @Column(nullable = true)
    private int destinationZIP;

    @Column(nullable = true)
    private String destinationCity;

<<<<<<< HEAD:Source/src/main/java/spring/entity/Tour.java
    @Column(nullable = true)
    private String destinationPersonName;
=======
    @Column (nullable = true)
    private String startPersonName;

    @Column (nullable = true)
    private String startPersonSurname;

    @Column (nullable = true)
    private String contactPersonName;
>>>>>>> 3137570e89e8c3ebc5fc62aa728861d2c4e40131:Source/src/main/java/spring/entity/Delivery.java

    @Column(nullable = true)
    private String destinationPersonSurname;

    @Column(nullable = true)
    private float estimatedTime;

    @Column (nullable = true)
    private String driver;

    // Calendar object that contains the date and the time when the delivery should be started
    @Column(nullable = true)
    private Calendar startingTime;

    @Column(nullable = false)
    private State state = State.CREATED;


    public Tour(String cargo) {
        this.cargo = cargo;
    }

    public Tour(String cargo, int numberOfAnimals, String startPersonName, String startPersonSurname, String startAddress,
                int startAddressNumber, int startZIP, String startCity, String destinationPersonName, String destinationPersonSurname,
                String destinationAddress, int destinationAddressNumber, int destinationZIP, String destinationCity, float estimatedTime, Calendar startingTime) {
        this.cargo = cargo;
        this.numberOfAnimals = numberOfAnimals;
        this.startPersonName = startPersonName;
        this.startPersonSurname = startPersonSurname;
        this.startAddress = startAddress;
        this.startAddressNumber = startAddressNumber;
        this.startZIP = startZIP;
        this.startCity = startCity;
        this.destinationPersonName = destinationPersonName;
        this.destinationPersonSurname = destinationPersonSurname;
        this.destinationAddress = destinationAddress;
        this.destinationAddressNumber = destinationAddressNumber;
        this.destinationZIP = destinationZIP;
        this.destinationCity = destinationCity;
        this.estimatedTime = estimatedTime;
        this.startingTime = startingTime;
    }


    //Default Constructor for the Sake of JPA
    protected Tour() {
    }

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

    public void setDestinationPersonName(String destinationPersonName) {
        this.destinationPersonName = destinationPersonName;
    }

    public void setDestinationPersonSurname(String destinationPersonSurname) {
        this.destinationPersonSurname = destinationPersonSurname;
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

    public String getDestinationPersonName() {
        return destinationPersonName;
    }

    public String getDestinationPersonSurname() {
        return destinationPersonSurname;
    }

    @Override
    public String toString() {
        return "Tour with the id: " + this.id + " goes from: \n" + this.startAddress + " " + this.startAddressNumber + "\n" +
                this.startZIP + " " + this.startCity + "\n\n to: \n" + this.destinationAddress + " " + this.destinationAddressNumber + "\n" +
                this.destinationZIP + " " + this.destinationCity + "\ncontaining" + this.numberOfAnimals + " " + this.cargo +
                " and starts/started on: " + this.startingTime.toString() + " with an estimated duration of: " + this.estimatedTime + "";
    }

    public enum State {
        SUCCESSFUL, FAILED, DELETED, CREATED
    }

    public String getStartPersonName() {
        return startPersonName;
    }

    public void setStartPersonName(String startPersonName) {
        this.startPersonName = startPersonName;
    }

    public String getStartPersonSurname() {
        return startPersonSurname;
    }

    public void setStartPersonSurname(String startPersonSurname) {
        this.startPersonSurname = startPersonSurname;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}

