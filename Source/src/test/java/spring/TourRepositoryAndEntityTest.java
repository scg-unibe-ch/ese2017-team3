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
import spring.repositories.TourRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TourRepositoryAndEntityTest {

    // TODO: Add startAddress and endAddress to tour, as soon as Address entity works as expected

    private Tour tour = new Tour();
    private Truck truck = new Truck();
    private Driver driver = new Driver();
    private Address startAddress = new Address();
    private Address endAddress = new Address();

    @Autowired
    private TourRepository tourRepository;

    @Before
    public void setUp() {
        driver.setUsername("Alfi");
        driver.setId(1);
        driver.setHiringDate(LocalDate.now());
        driver.getAddress().setLastname("Alfredo");
        driver.getAddress().setFirstname("Gorgonzola");

        truck.setAvailable(true);
        truck.setId(1);
        truck.setTruckType("BIG");

        startAddress.setId(1);
        startAddress.setCity("Narnia");
        startAddress.setLastname("Gowen");
        startAddress.setFirstname("Geter");
        startAddress.setZip(1001);
        startAddress.setStreet("Rhubarb Hill");
        startAddress.setStreetNumber("12A");

        endAddress.setId(1);
        endAddress.setCity("Bielefeld");
        endAddress.setLastname("Sam");
        endAddress.setFirstname("Sung");
        endAddress.setZip(1001);
        endAddress.setStreet("Galaxy Avenue");
        endAddress.setStreetNumber("8E");

        tour.setCargo("Monkey");
        tour.setDriver(driver.getUsername());
        tour.setNumberOfAnimals(22);
        tour.setEstimatedTime(2.0);
        tour.setDeliveryStartDate(LocalDate.now());
        tour.setDeliveryStartTime(LocalTime.now());
        tour.setTruck(truck);
//        tour.setStartAddress(startAddress);
//        tour.setDestinationAddress(endAddress);
        tour.setTimeFrame("05:00 - 05:02");
    }

    @Test
    public void saveAndRetrieve() {
        tourRepository.save(tour);
        Tour found = tourRepository.findOne(new Long(1));
        assertTrue(found.equals(tour));
    }

}
