package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import spring.entity.Truck;
import spring.repositories.TruckRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olulrich on 05.11.17.
 */

@Component
public class TruckLoader implements ApplicationRunner {

    @Autowired
    private TruckRepository truckRepository;

    public void run(ApplicationArguments args) {
        //Add Trucks to DB

        if (truckRepository.findAll().size() == 0) {
            truckRepository.save(new Truck("BIG", "bigTransporter2.jpg", true));
            truckRepository.save(new Truck("BIG", "bigTransporter2.jpg", true));
            truckRepository.save(new Truck("BIG", "bigTransporter2.jpg", true));

            truckRepository.save(new Truck("MEDIUM", "mediumTransporter2.jpg", true));
            truckRepository.save(new Truck("MEDIUM", "mediumTransporter2.jpg", true));
            truckRepository.save(new Truck("MEDIUM", "mediumTransporter2.jpg", true));


            truckRepository.save(new Truck("SMALL", "smallTransporter2.jpg", true));
            truckRepository.save(new Truck("SMALL", "smallTransporter2.jpg", true));
            truckRepository.save(new Truck("SMALL", "smallTransporter2.jpg", true));
            truckRepository.save(new Truck("SMALL", "smallTransporter2.jpg", true));
            truckRepository.save(new Truck("SMALL", "smallTransporter2.jpg", true));


            truckRepository.save(new Truck("TINY", "tinyTransporter2.jpg", true));
            truckRepository.save(new Truck("TINY", "tinyTransporter2.jpg", true));
            truckRepository.save(new Truck("TINY", "tinyTransporter2.jpg", true));
            truckRepository.save(new Truck("TINY", "tinyTransporter2.jpg", true));
            truckRepository.save(new Truck("TINY", "tinyTransporter2.jpg", true));
            truckRepository.save(new Truck("TINY", "tinyTransporter2.jpg", true));
            truckRepository.save(new Truck("TINY", "tinyTransporter2.jpg", true));
        }
    }
}
