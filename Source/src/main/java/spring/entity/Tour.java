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


    @NotNull
    private Address startAddress;

    @NotNull
    private Address destinationAddress;

//    @Size(min = 1, message = "Please specify the name of the contact person at the start location.")
//	private String startPersonName;
//
//	@Size(min = 1, message = "Please specify the surname of the contact person at the start location.")
//	private String startPersonSurname;
//
//	@Size(min = 1, message = "Please specify the street name of the start location.")
//    private String startAddress;
//
//	@Size(min = 1, message = "Please specify the street number of the start location.")
//    private String startAddressNumber;
//
//	@NotNull (message = "Please specify the ZIP code of the start location.")
//	@Min(value = 1000, message = "You entered an invalid ZIP code.")
//	@Max(value = 9999, message = "You entered an invalid ZIP code.")
//    private int startZip;
//
//	@Size(min = 1, message = "Please specify the city of the start location.")
//    private String startCity;
//
//	@Size(min = 1, message = "Please specify the name of the contact person at the tour destination.")
//	private String contactPersonName;
//
//	@Size(min = 1, message = "Please specify the surname of the person at the tour destionation.")
//	private String contactPersonSurname;
//
//	@Size(min = 1, message = "Please specify the destination street.")
//	private String destinationAddress;
//
//	@Size(min = 1, message = "Please specify the street number of the tour destination.")
//	private String destinationAddressNumber;
//
//	@NotNull (message = "Please specify the ZIP code of the tour destination.")
//	@Min(value = 1000, message = "You entered an invalid ZIP code.")
//	@Max(value = 9999, message = "You entered an invalid ZIP code.")
//	private int destinationZip;
//
//	@Size(min = 1, message = "Please specify the city of the tour destination.")
//	private String destinationCity;

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

    //	public String getStartPersonName() {
//	    return startPersonName;
//	}
//
//	public String getStartPersonSurname() {
//	    return startPersonSurname;
//	}
//
//	public String getStartAddress() {
//	    return startAddress;
//	}
//
//	public String getStartAddressNumber() {
//	    return startAddressNumber;
//	}
//
//	public int getStartZip() {
//	    return startZip;
//	}
//
//	public String getStartCity() {
//	    return startCity;
//	}
//
//	public String getContactPersonName() {
//	    return contactPersonName;
//	}
//
//	public String getContactPersonSurname() {
//	    return contactPersonSurname;
//	}
//
//	public String getContactPerson() { return contactPersonName + " " + contactPersonSurname; }
//
//	public String getDestinationAddress() {
//	    return destinationAddress;
//	}
//
//	public String getDestinationAddressNumber() {
//	    return destinationAddressNumber;
//	}
//
//	public int getDestinationZip() {
//	    return destinationZip;
//	}
//
//	public String getDestinationCity() {
//	    return destinationCity;
//	}


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

//	public void setStartPersonName(String startPersonName) {
//	    this.startPersonName = startPersonName;
//	}
//
//	public void setStartPersonSurname(String startPersonSurname) {
//	    this.startPersonSurname = startPersonSurname;
//	}
//
//	public void setStartAddress(String startAddress) {
//	    this.startAddress = startAddress;
//	}
//
//	public void setStartAddressNumber(String startAddressNumber) {
//	    this.startAddressNumber = startAddressNumber;
//	}
//
//	public void setStartZip(int startZip) {
//	    this.startZip = startZip;
//	}
//
//	public void setStartCity(String startCity) {
//	    this.startCity = startCity;
//	}
//
//	public void setContactPersonName(String contactPersonName) {
//	    this.contactPersonName = contactPersonName;
//	}
//
//	public void setContactPersonSurname(String contactPersonSurname) {
//	    this.contactPersonSurname = contactPersonSurname;
//	}
//
//	public void setDestinationAddress(String destinationAddress) {
//	    this.destinationAddress = destinationAddress;
//	}
//
//	public void setDestinationAddressNumber(String destinationAddressNumber) {
//	    this.destinationAddressNumber = destinationAddressNumber;
//	}
//
//	public void setDestinationZip(int destinationZip) {
//	    this.destinationZip = destinationZip;
//	}
//
//	public void setDestinationCity(String destinationCity) {
//	    this.destinationCity = destinationCity;
//	}

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
