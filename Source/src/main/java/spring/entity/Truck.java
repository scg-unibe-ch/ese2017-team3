package spring.entity;

import java.util.List;

import javax.persistence.*;

/**
 * Created by olulrich on 05.11.17.
 */

@Entity
public class Truck {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String truckType;

    @Column(nullable = false)
    private Boolean available;

    @Column(nullable = false)
    private long imageId;

    @Column
    private double length;

    @Column
    private double width;

    @Column
    private double payload;

    @Column
    private String description = "";

    @OneToMany(mappedBy = "truck")
    public List<Tour> tours;


    public Truck() {};

    public Truck(String truckType, boolean available) {
        this.truckType = truckType;
        this.available = available;
    }

    public Truck(String truckType, long imageId, boolean available) {
        this.truckType = truckType;
        this.imageId = imageId;
        this.available = available;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getPayload() {
        return payload;
    }

    public void setPayload(double payload) {
        this.payload = payload;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Truck truck = (Truck) o;

        if (getId() != truck.getId()) return false;
        if (getImageId() != truck.getImageId()) return false;
        if (getTruckType() != null ? !getTruckType().equals(truck.getTruckType()) : truck.getTruckType() != null)
            return false;
        return getAvailable() != null ? getAvailable().equals(truck.getAvailable()) : truck.getAvailable() == null;
    }

}
