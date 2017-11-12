package spring.entity;

import javax.persistence.*;

/**
 * Created by olulrich on 05.11.17.
 */

@Entity
public class Truck {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;

    @Column(nullable = false)
    public String truckType;

    @Column(nullable = false)
    public Boolean available;

    @OneToOne(mappedBy = "truck")
    public Tour tour;

    public Truck() {};

    public Truck(String truckType, boolean available) {
        this.truckType = truckType;
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

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Truck truck = (Truck) o;

        if (getId() != truck.getId()) return false;
        if (getTruckType() != null ? !getTruckType().equals(truck.getTruckType()) : truck.getTruckType() != null)
            return false;
        return getAvailable() != null ? getAvailable().equals(truck.getAvailable()) : truck.getAvailable() == null;
    }
}