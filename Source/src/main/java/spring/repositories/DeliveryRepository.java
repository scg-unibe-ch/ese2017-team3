package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.entity.delivery;

import java.util.*;

/**
 * Created by olulrich on 20.10.17.
 */

@Repository
public interface DeliveryRepository extends CrudRepository<delivery, Long> {


}