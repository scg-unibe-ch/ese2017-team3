package spring;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TourRepositoryAndEntityTest {

    // TODO: Add startAddress and endAddress to tour, as soon as Address entity works as expected

    private Tour tour = new Tour();
    private Truck truck = new Truck();
    private Driver driver = new Driver();
    private Address driverAddress = new Address();
    private Address startAddress = new Address();
    private Address endAddress = new Address();

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Before
    public void setUp() {

        driverAddress.setCity("Zürich");
        driverAddress.setLastname("Alfredo");
        driverAddress.setFirstname("Gorgonzola");
        driverAddress.setZip(2222);
        driverAddress.setStreet("Bergstrasse");
        driverAddress.setStreetNumber("100");

        startAddress.setCity("Narnia");
        startAddress.setFirstname("Gowen");
        startAddress.setLastname("Geter");
        startAddress.setZip(1001);
        startAddress.setStreet("Rhubarb Hill");
        startAddress.setStreetNumber("12A");

        endAddress.setCity("Bielefeld");
        endAddress.setFirstname("Sam");
        endAddress.setLastname("Sung");
        endAddress.setZip(1001);
        endAddress.setStreet("Galaxy Avenue");
        endAddress.setStreetNumber("8E");

        addressRepository.save(driverAddress);
        addressRepository.save(startAddress);
        addressRepository.save(endAddress);

        driver.setUsername("Alfi");
        driver.setHiringDate(LocalDate.now());
        driver.setAddress(driverAddress);

        driverRepository.save(driver);

        truck.setAvailable(true);
        truck.setTruckType("BIG");
        truckRepository.save(truck);

        tour.setCargo("Monkey");
        tour.setDriver(driver.getUsername());
        tour.setNumberOfAnimals(22);
        tour.setEstimatedTime(2.0);
        tour.setDeliveryStartDate(LocalDate.now());
        tour.setDeliveryStartTime(LocalTime.now());
        tour.setTruck(truck);
        tour.setStartAddress(startAddress);
        tour.setDestinationAddress(endAddress);
        tour.setTimeFrame("05:00 - 05:02");
        tourRepository.save(tour);
    }

    @Test
    public void saveAndRetrieve() {
        Tour found = tourRepository.findOne(new Long(1));
        assertEquals(found, tour);
    }

}
