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
}
