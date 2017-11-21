package spring.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by olulrich on 20.10.17.
 */
@Entity
public class Tour {

    public enum TourState {SUCCESSFUL, CREATED, DELETED, FAILED}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min = 1, message = "Please specify the cargo for this order.")
    private String cargo;

        @Min(value = 1, message = "Please specify the number of animals for this tour.")
//    @Pattern(regexp = "(\\d+)", message = "You entered an invalid number.")
    private int numberOfAnimals;

    @OneToOne
    @Valid
    private Address startAddress;

    @OneToOne
    @Valid
    private Address destinationAddress;

    @NotNull(message = "Please specify a start date for the tour.")
//	@Future (message = "You can't create tours in the past.")
    @DateTimeFormat(pattern = "uuuu-MM-dd")
    private LocalDate deliveryStartDate;

    @NotNull(message = "Please specify a start time for the tour.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime deliveryStartTime;

    @NotNull(message = "Please specify the estimated time for the tour.")
//    @Pattern(regexp = "(\\d+)", message = "You entered an invalid number.")
    private Double estimatedTime;

    @Size(min = 1, message = "Please specify a time frame, in which the delivery can take place.")
    private String timeFrame;

    //@Column(nullable = false)
    @NotNull(message = "There is no driver which can drive the Tour. Please consider to hire some drivers")
    private String driver;

    
    @NotNull(message = "There are no trucks left. Please consider buying more trucks :P")
	@ManyToOne
	private Truck truck;

	public Tour(String cargo) {
	    this.cargo = cargo;
	}

    @Column
    private String comment = "Tour comment";

    @Column
    private String tourFeedback = "Tour feedback";

    @Column(nullable = false)
    private TourState tourState = TourState.CREATED;

	public Truck getTruck() {
		return truck;
	}

	public void setTruck(Truck truck) {
		this.truck = truck;
	}

	public TourState getTourState() {
		return tourState;
	}

    public Tour() {
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

    public String getTourFeedback() {
        return tourFeedback;
    }

    public void setTourFeedback(String tourFeedback) {
        if (!tourFeedback.isEmpty()) {
            this.tourFeedback = tourFeedback;
        }
    }

    public void setComment(String comment) {
	    if (!comment.isEmpty()) {
            this.comment = comment;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tour tour = (Tour) o;

        if (getId() != tour.getId()) return false;
        if (getNumberOfAnimals() != tour.getNumberOfAnimals()) return false;
        if (getCargo() != null ? !getCargo().equals(tour.getCargo()) : tour.getCargo() != null) return false;
        if (getDeliveryStartDate() != null ? !getDeliveryStartDate().equals(tour.getDeliveryStartDate()) : tour.getDeliveryStartDate() != null)
            return false;
        if (getDeliveryStartTime() != null ? !getDeliveryStartTime().equals(tour.getDeliveryStartTime()) : tour.getDeliveryStartTime() != null)
            return false;
        if (getEstimatedTime() != null ? !getEstimatedTime().equals(tour.getEstimatedTime()) : tour.getEstimatedTime() != null)
            return false;
        if (getTimeFrame() != null ? !getTimeFrame().equals(tour.getTimeFrame()) : tour.getTimeFrame() != null)
            return false;
        if (getDriver() != null ? !getDriver().equals(tour.getDriver()) : tour.getDriver() != null) return false;
        if (getTruck() != null ? !getTruck().equals(tour.getTruck()) : tour.getTruck() != null) return false;
        if (getComment() != null ? !getComment().equals(tour.getComment()) : tour.getComment() != null) return false;
        if (getTourFeedback() != null ? !getTourFeedback().equals(tour.getTourFeedback()) : tour.getTourFeedback() != null)
            return false;
        return getTourState() == tour.getTourState();
    }

}
