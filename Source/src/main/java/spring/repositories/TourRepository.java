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

	public List<Tour> findByDriverAndStartDate(String username, LocalDate startDate);

	public List<Tour> findByState(Tour.State state);

	public List<Tour> findByDriverAndState(String username, Tour.State state);

	public List<Tour> findByDriverAndStateOrState(String username, Tour.State t1, Tour.State t2);



}
