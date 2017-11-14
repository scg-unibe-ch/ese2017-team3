package spring.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.entity.Tour;

/**
 * Created by olulrich on 20.10.17.
 */

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {

	public List<Tour> findAll();

	public List<Tour> findByDriver(String username);

	public List<Tour> findByDriverAndDeliveryStartDate(String username, LocalDate startDate);

	public List<Tour> findByTourState(Tour.TourState tourState);

	public List<Tour> findByDriverAndTourState(String username, Tour.TourState tourState);

	public List<Tour> findByDriverAndTourStateOrTourState(String username, Tour.TourState t1, Tour.TourState t2);

}
