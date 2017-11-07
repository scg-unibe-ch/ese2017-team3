package spring.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by olulrich on 20.10.17.
 */
@Entity
public class Tour {

    public enum TourState {SUCCESSFUL, CREATED, DELETED, FAILED}

    ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min = 1, message = "Please specify the cargo for this order.")
    private String cargo;

    @Min(value = 1, message = "Please specify the number of animals for this tour.")
    private int numberOfAnimals;


    @OneToOne
    private Address startAddress;

    @OneToOne
    private Address destinationAddress;

    @NotNull(message = "Please specify a start date for the tour.")
//	@Future (message = "You can't create tours in the past.")
    @DateTimeFormat(pattern = "uuuu-MM-dd")
    private LocalDate deliveryStartDate;

    @NotNull(message = "Please specify a start time for the tour.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime deliveryStartTime;

    @NotNull(message = "Please specify the estimated time for the tour.")
    private Double estimatedTime;

    @Size(min = 1, message = "Please specify a time frame, in which the delivery can take place.")
    private String timeFrame;

    @Column(nullable = false)
    private String driver;

    @Column
    private String comment;

    @Column(nullable = false)
    private TourState tourState = TourState.CREATED;

    public Tour(String cargo) {
        this.cargo = cargo;
    }

    public Tour() {
    }

    public TourState getTourState() {
        return tourState;
    }

    public void setTourState(TourState tourState) {
        this.tourState = tourState;
    }

    public long getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public Address getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(Address startAddress) {
        this.startAddress = startAddress;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public LocalDate getDeliveryStartDate() {
        return deliveryStartDate;
    }

    public LocalTime getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public Double getEstimatedTime() {
        return estimatedTime;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public String getDriver() {
        return driver;
    }

    public String getComment() {
        return comment;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setNumberOfAnimals(int numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }

    public void setDeliveryStartDate(LocalDate startDate) {
        this.deliveryStartDate = startDate;
    }

    public void setDeliveryStartTime(LocalTime startTime) {
        this.deliveryStartTime = startTime;
    }

    public void setEstimatedTime(Double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
