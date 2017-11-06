package spring.entity;

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
    public String truckType;

    public Truck() {};

    public Truck(String truckType) {
        this.truckType = truckType;
    }
}
