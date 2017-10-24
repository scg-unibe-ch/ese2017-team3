package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.Driver;

/**
 * Created by olulrich on 24.10.17.
 */
public interface DriverRepository extends CrudRepository<Driver, Long> {
}
