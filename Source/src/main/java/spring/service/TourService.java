package spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.entity.Tour;
import spring.repositories.TourRepository;

/**
 * Created by olulrich on 20.10.17.
 */

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    public TourRepository getTourRepository() {
        return tourRepository;
    }
    
    public List<Tour> getTours() {
    	List<Tour> tours = new ArrayList<Tour>();
    	tourRepository.findAll().forEach(tours::add);
    	return tours;
    }
    
    
}
