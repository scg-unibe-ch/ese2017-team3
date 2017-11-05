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
        truckRepository.save(new Truck(Truck.TruckType.BIG));
        truckRepository.save(new Truck(Truck.TruckType.BIG));
        truckRepository.save(new Truck(Truck.TruckType.BIG));


        truckRepository.save(new Truck(Truck.TruckType.MEDIUM));
        truckRepository.save(new Truck(Truck.TruckType.MEDIUM));
        truckRepository.save(new Truck(Truck.TruckType.MEDIUM));


        truckRepository.save(new Truck(Truck.TruckType.SMALL));
        truckRepository.save(new Truck(Truck.TruckType.SMALL));
        truckRepository.save(new Truck(Truck.TruckType.SMALL));
        truckRepository.save(new Truck(Truck.TruckType.SMALL));
        truckRepository.save(new Truck(Truck.TruckType.SMALL));


        truckRepository.save(new Truck(Truck.TruckType.TINY));
        truckRepository.save(new Truck(Truck.TruckType.TINY));
        truckRepository.save(new Truck(Truck.TruckType.TINY));
        truckRepository.save(new Truck(Truck.TruckType.TINY));
        truckRepository.save(new Truck(Truck.TruckType.TINY));
        truckRepository.save(new Truck(Truck.TruckType.TINY));
        truckRepository.save(new Truck(Truck.TruckType.TINY));
    }
}
