package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Driver;
import spring.repositories.DriverRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olulrich on 24.10.17.
 */

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<Driver>();
        driverRepository.findAll().forEach(drivers::add);
        return drivers;
    }
}
