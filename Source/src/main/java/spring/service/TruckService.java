package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Animal;
import spring.entity.Truck;
import spring.repositories.TruckRepository;

import java.util.*;

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

    public Truck findFittingTruck(Animal animal, int amount) {

        int totalSurfaceAreaNeeded = animal.getSurfaceArea() * amount;
        int totalWeightNeeded = animal.getWeight() * amount;

        List<Truck> availableTrucks = getAvailableTrucks();

        ArrayList<Truck> possibleTrucks = new ArrayList<>(availableTrucks.size());

        //find difference between capacity and requirements
        for (Truck truck : availableTrucks) {
            int availableSurfaceArea = truck.getSurfaceArea();

            int difference = availableSurfaceArea - totalSurfaceAreaNeeded;

            if (difference >= 0) {
                possibleTrucks.add(truck);
            }
        }

        //sort possible trucks by ascending surface area
        possibleTrucks.sort(((o1, o2) -> o1.getSurfaceArea().compareTo(o2.getSurfaceArea())));

        //check possible trucks for weight requirement and return the smallest that fits
            for (Truck truck : possibleTrucks) {

            if (truck.getPayload() >= totalWeightNeeded) {
                return truck;
            }
        }

        return null;
    }
}
