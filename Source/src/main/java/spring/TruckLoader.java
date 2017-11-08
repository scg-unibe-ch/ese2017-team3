package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import spring.entity.Truck;
import spring.repositories.TruckRepository;

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
            truckRepository.save(new Truck("BIG", true));
            truckRepository.save(new Truck("BIG", true));
            truckRepository.save(new Truck("BIG", true));

            truckRepository.save(new Truck("MEDIUM", true));
            truckRepository.save(new Truck("MEDIUM", true));
            truckRepository.save(new Truck("MEDIUM", true));


            truckRepository.save(new Truck("SMALL", true));
            truckRepository.save(new Truck("SMALL", true));
            truckRepository.save(new Truck("SMALL", true));
            truckRepository.save(new Truck("SMALL", true));
            truckRepository.save(new Truck("SMALL", true));


            truckRepository.save(new Truck("TINY", true));
            truckRepository.save(new Truck("TINY", true));
            truckRepository.save(new Truck("TINY", true));
            truckRepository.save(new Truck("TINY", true));
            truckRepository.save(new Truck("TINY", true));
            truckRepository.save(new Truck("TINY", true));
            truckRepository.save(new Truck("TINY", true));
        }
    }
}
