package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.entity.Tour;

import java.util.List;

/**
 * Created by olulrich on 20.10.17.
 */

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {




}
