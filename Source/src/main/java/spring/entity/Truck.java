package spring.entity;

import javax.persistence.*;

/**
 * Created by olulrich on 05.11.17.
 */

@Entity
public class Truck {

    public enum TruckType {BIG, MEDIUM, SMALL, TINY};

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private TruckType truckType;

    public Truck() {};

    public Truck(TruckType truckType) {
        this.truckType = truckType;
    }
}
