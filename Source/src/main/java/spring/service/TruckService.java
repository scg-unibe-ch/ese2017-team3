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
}
