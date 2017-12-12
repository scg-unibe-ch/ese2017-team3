package spring;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.entity.*;
import spring.repositories.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EntityTest {


    private Tour tour = new Tour();
    private Truck truck = new Truck();
    private Driver driver = new Driver();
    private Address driverAddress = new Address();
    private Address startAddress = new Address();
    private Address endAddress = new Address();
    private Animal cow = new Animal();

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Before
    public void setUp() {

        driverAddress.setCity("Zürich");
        driverAddress.setLastname("Alfredo");
        driverAddress.setFirstname("Gorgonzola");
        driverAddress.setZip("2222");
        driverAddress.setStreet("Bergstrasse");
        driverAddress.setStreetNumber("100");

        cow.setSpecies("Cow");
        cow.setLength(180);
        cow.setWidth(100);
        cow.setWeight(1100);
        animalRepository.save(cow);

        startAddress.setCity("Narnia");
        startAddress.setFirstname("Gowen");
        startAddress.setLastname("Geter");
        startAddress.setZip("1001");
        startAddress.setStreet("Rhubarb Hill");
        startAddress.setStreetNumber("12A");

        endAddress.setCity("Bielefeld");
        endAddress.setFirstname("Sam");
        endAddress.setLastname("Sung");
        endAddress.setZip("1001");
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
        truck.setTruckType("TestTruck");
        truckRepository.save(truck);

        tour.setCargo(cow.getSpecies());
        tour.setDriver(driver.getUsername());
        tour.setNumberOfAnimals(22);
        tour.setEstimatedTime(2.0);
        tour.setStartDate(LocalDate.now());
        tour.setStartTime(LocalTime.now());
        tour.setTruck(truck);
        tour.setStartAddress(startAddress);
        tour.setDestinationAddress(endAddress);
        tour.setTimeFrame("05:00 - 05:02");
        tourRepository.save(tour);
    }

    @After
    public void resetTables() {
        tourRepository.delete(tour);
        animalRepository.delete(cow);
        truckRepository.delete(truck);
        driverRepository.delete(driver);
        addressRepository.delete(startAddress);
        addressRepository.delete(endAddress);
        addressRepository.delete(driverAddress);
    }

    @Test
    public void retrieveSameTour() {
        Tour found = tourRepository.findOne(tour.getId());
        assertEquals(tour, found);
    }

    @Test
    public void retrieveSameAnimal() {
        Animal found = animalRepository.findOne(cow.getId());
        assertEquals(cow, found);
    }

    @Test
    public void retrieveSameDriver() {
        Driver found = driverRepository.findOne(driver.getId());
        assertEquals(driver, found);
    }

    @Test
    public void retrieveSameTruck() {
        Truck found = truckRepository.findOne(truck.getId());
        assertEquals(truck, found);
    }

    @Test
    public void retrieveSameAddresses() {
        Iterable<Address> found = addressRepository.findAll();
        List<Address> saved = new ArrayList<>();
        saved.add(startAddress);
        saved.add(endAddress);
        saved.add(driverAddress);

        Iterator<Address> iterator = found.iterator();
        while (iterator.hasNext()) {
            Address addr = iterator.next();
            assertTrue("Retrieved Address from repository equals a saved one.", saved.contains(addr));
        }
    }
}
