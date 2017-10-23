package spring.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by olulrich on 20.10.17.
 */
@Entity
public class Tour {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

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
    private int startZip;

    @Column (nullable = true)
    private String startCity;

    public LocalDate getDeliveryStartDate() {
        return deliveryStartDate;
    }


    public void setDeliveryStartDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        this.deliveryStartDate = LocalDate.parse(dateString, formatter);
    }

    public LocalTime getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.deliveryStartTime = LocalTime.parse(timeString, formatter);
    }

    @Column (nullable = true)
    private String destinationAddress;

    @Column (nullable = true)
    private int destinationAddressNumber;

    @Column (nullable = true)
    private int destinationZip;

    @Column (nullable = true)
    private String destinationCity;

    @Column (nullable = true)
    private String startPersonName;

    @Column (nullable = true)
    private String startPersonSurname;

    @Column (nullable = true)
    private String contactPersonName;

    @Column (nullable = true)
    private String contactPersonSurname;

    @Column (nullable = true)
    private float estimatedTime;

    @Column (nullable = true)
    private String driver;

    @Column (nullable = true)
    private String comment;

    @Column (nullable = true)
    @DateTimeFormat(pattern="uuuu-MM-dd")
    private LocalDate deliveryStartDate;

    @Column (nullable = true)
    @DateTimeFormat(pattern="HH:mm")
    private LocalTime deliveryStartTime;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    @Column (nullable = true)
    private String timeFrame;

    public Tour(String cargo) {
        this.cargo = cargo;
    }

    public Tour(){}

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

    public void setStartZip(int startZip) {
        this.startZip = startZip;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setDestinationAddressNumber(int destinationAddressNumber) {
        this.destinationAddressNumber = destinationAddressNumber;
    }

    public void setDestinationZip(int destinationZip) {
        this.destinationZip = destinationZip;
    }

    public void setEstimatedTime(float estimatedTime) {
        this.estimatedTime = estimatedTime;
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

    public long getId() {
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

    public int getStartZip() {
        return startZip;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public int getDestinationAddressNumber() {
        return destinationAddressNumber;
    }

    public int getDestinationZip() {
        return destinationZip;
    }

    public float getEstimatedTime() {
        return estimatedTime;
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
