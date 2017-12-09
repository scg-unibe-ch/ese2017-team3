package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Truck;
import spring.repositories.TruckRepository;

import java.util.List;

/**
 * Created by olulrich on 05.11.17.
 */

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    public List<Truck> getTrucks() {
        return truckRepository.findAll();
    }

    public List<Truck> getSortedTrucks() {return truckRepository.findAllByOrderByTruckTypeDesc(); }

    public Truck getById(Long id) {
        return truckRepository.findOne(id);
    }

    // Returns a list of all available trucks of the specified truck type
    public List<Truck> getAvailableTrucksOfType(String truckType) {
        return truckRepository.findByTruckTypeAndAndAvailable(truckType, true);
    }

    public List<Truck> getAvailableTrucks() {
        return truckRepository.findByAvailable(true);
    }
}
