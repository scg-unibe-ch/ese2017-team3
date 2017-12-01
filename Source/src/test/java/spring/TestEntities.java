package spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spring.entity.Address;
import spring.entity.Driver;
import spring.entity.Tour;
import spring.entity.Truck;
import spring.repositories.AddressRepository;
import spring.repositories.DriverRepository;
import spring.repositories.TourRepository;
import spring.repositories.TruckRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestEntities {
    
    @Autowired
    private TourRepository tourRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private TruckRepository truckRepository;
    
    @Autowired
    private DriverRepository driverRepository;
    
    
    Address address1 = new Address("Max", "Mad", "Madness", "122", 2222, "Madcity", "0001232", "Mad@max.com");
    Address address2 = new Address("Man", "Iron", "Steelstr", "12323", 3455, "Weaponfeast", "0123345", "iron.man@men.com");
    Address address3 = new Address("Pirate", "Carrebean", "Carrebeansea", "-12", 0000, "underseas", "65465445", "johny.depp@trottel.com");
    Address address4 = new Address("Prime", "Optimus", "Robotsstr", "85d", 2355, "Earth", "-694686", "optimus.prime@autobots.com");
    Address address5 = new Address("Bee", "Bumble", "Robotsstr", "85c", 2355, "Earth", "123412", "bumble.bee@autobots.com");
    Address address6 = new Address("Hai", "Iron", "Robotsstr", "85a", 2355, "Earth", "23542354", "ironhai@autobots.com");
    
    
    Driver driver1 = new Driver("Max");
    Driver driver2 = new Driver("Iron");
    Driver driver3 = new Driver("Johny");
    Driver driver4 = new Driver("Optimus");
    Driver driver5 = new Driver("Bumblebee");
    Driver driver6 = new Driver("Ironhai");
    
    Truck truck1 = truckRepository.findAll().get((int) Math.random() * truckRepository.findAll().size());
    Truck truck2 = truckRepository.findAll().get((int) Math.random() * truckRepository.findAll().size());
    Truck truck3 = truckRepository.findAll().get((int) Math.random() * truckRepository.findAll().size());
    Truck truck4 = truckRepository.findAll().get((int) Math.random() * truckRepository.findAll().size());
    Truck truck5 = truckRepository.findAll().get((int) Math.random() * truckRepository.findAll().size());
    
    Tour tour1 = new Tour("Monkey", 22, address1, address2, LocalDate.now(), LocalTime.now(), 2.0, "22:49-23:05", driver1.getUsername(),
            truck1,  Tour.TourState.INCOMPLETE);
    Tour tour2 = new Tour("Cow", 10, address1, address4, LocalDate.MIN, LocalTime.now(), 2.5, "22:49-23:05", driver2.getUsername(),
            truck1,  Tour.TourState.CREATED);
    Tour tour3 = new Tour("Sheep",  2, address3, address5, LocalDate.MAX, LocalTime.now(), 2.0, "22:49-23:05", driver3.getUsername(),
            truck1,  Tour.TourState.SUCCESSFUL);
    Tour tour4 = new Tour("Panda",  2, address4, address5, LocalDate.MAX, LocalTime.now(), 2.0, "22:49-23:05", driver4.getUsername(),
            truck1,  Tour.TourState.DELETED);
    Tour tour5 = new Tour("Sheep",  2, address6, address5, LocalDate.now(), LocalTime.now(), 2.0, "22:49-23:05", driver5.getUsername(),
            truck1,  Tour.TourState.FAILED);
    @Test
    public void saveEntities() {
        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);
        addressRepository.save(address4);
        addressRepository.save(address5);
        addressRepository.save(address6);
        
        driverRepository.save(driver1);
        driverRepository.save(driver2);
        driverRepository.save(driver3);
        driverRepository.save(driver4);
        driverRepository.save(driver5);
        driverRepository.save(driver6);
        
        tourRepository.save(tour1);
        tourRepository.save(tour2);
        tourRepository.save(tour3);
        tourRepository.save(tour4);
        tourRepository.save(tour5);
        
    }
    @Test
    public void deleteEntities(){
        if(tourRepository.exists(tour1.getId())){
            tourRepository.delete(tour1);
        }
        if(tourRepository.exists(tour2.getId())){
            tourRepository.delete(tour2);
        }
        if(tourRepository.exists(tour3.getId())){
            tourRepository.delete(tour3);
        }
        if(tourRepository.exists(tour4.getId())){
            tourRepository.delete(tour4);
        }
        if(tourRepository.exists(tour5.getId())){
            tourRepository.delete(tour5);
        }
        
        if(driverRepository.exists(driver1.getId())){
            driverRepository.delete(driver1);
        }
        if(driverRepository.exists(driver2.getId())){
            driverRepository.delete(driver2);
        }
        if(driverRepository.exists(driver3.getId())){
            driverRepository.delete(driver3);
        }
        if(driverRepository.exists(driver4.getId())){
            driverRepository.delete(driver4);
        }
        if(driverRepository.exists(driver5.getId())){
            driverRepository.delete(driver5);
        }
        if(driverRepository.exists(driver6.getId())){
            driverRepository.delete(driver6);
        }
        
        if(addressRepository.exists(address1.getId())){
            addressRepository.delete(address1);
        }
        if(addressRepository.exists(address2.getId())){
            addressRepository.delete(address2);
        }
        if(addressRepository.exists(address3.getId())){
            addressRepository.delete(address3);
        }
        if(addressRepository.exists(address4.getId())){
            addressRepository.delete(address4);
        }
        if(addressRepository.exists(address5.getId())){
            addressRepository.delete(address5);
        }
        if(addressRepository.exists(address6.getId())){
            addressRepository.delete(address6);
        }
    
    }
    
    
}
