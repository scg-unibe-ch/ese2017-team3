package spring.dao;

import org.springframework.stereotype.Repository;
import spring.entity.delivery;

import java.util.*;

/**
 * Created by olulrich on 20.10.17.
 */

@Repository
public class deliveryDao {


    private static ArrayList<delivery> deliveries = new ArrayList<>();


    public ArrayList<delivery> getAllDeliveries() {

        deliveries.add(new delivery(1, "cow"));
        deliveries.add(new delivery(1, "chicken"));
        deliveries.add(new delivery(1, "sheep"));

        return this.deliveries;
    }
}
