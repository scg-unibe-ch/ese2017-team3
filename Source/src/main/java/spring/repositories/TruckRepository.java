package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.entity.Truck;

import java.util.List;

/**
 * Created by olulrich on 05.11.17.
 */

@Repository
public interface TruckRepository extends CrudRepository<Truck, Long> {

    List<Truck> findAll();

    List<Truck> findAllByOrderByTruckTypeDesc();

    List<Truck> findByTruckType(String truckType);

    List<Truck> findByTruckTypeAndAndAvailable(String truckType, boolean available);

    List<Truck> findByAvailable(boolean available);

}
