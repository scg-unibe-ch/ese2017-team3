package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import spring.entity.Image;
import spring.entity.Truck;
import spring.repositories.ImageRepository;
import spring.repositories.TruckRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olulrich on 05.11.17.
 */

@Component
public class TruckLoader implements ApplicationRunner {

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private ImageRepository imageRepository;


    public void run(ApplicationArguments args) {
        //Add Trucks to DB

        if (truckRepository.findAll().size() == 0) {

            try {
                Resource bigTruck = new ClassPathResource("static/images/trucks/bigTransporter2.jpg");
                byte[] bigTruckFile = Files.readAllBytes(Paths.get(bigTruck.getURI()));
                Image big = imageRepository.save(new Image(bigTruckFile));

                Resource mediumTruck = new ClassPathResource("static/images/trucks/mediumTransporter2.jpg");
                byte[] mediumTruckFile = Files.readAllBytes(Paths.get(mediumTruck.getURI()));
                Image medium = imageRepository.save(new Image(mediumTruckFile));

                Resource smallTruck = new ClassPathResource("static/images/trucks/smallTransporter2.jpg");
                byte[] smallTruckFile = Files.readAllBytes(Paths.get(smallTruck.getURI()));
                Image small = imageRepository.save(new Image(smallTruckFile));

                Resource tinyTruck = new ClassPathResource("static/images/trucks/tinyTransporter2.jpg");
                byte[] tinyTruckFile = Files.readAllBytes(Paths.get(tinyTruck.getURI()));
                Image tiny = imageRepository.save(new Image(tinyTruckFile));

                for(int i=0; i<3; i++) {
                    Truck t = new Truck("BIG", big.getId(), true);
                    t.setLength(15.65);
                    t.setWidth(2.55);
                    t.setPayload(40000);
                    t.setDescription("A big truck with a lot of space.");
                    truckRepository.save(t);
                }

                for(int i=0; i<3; i++) {
                    Truck t = new Truck("MEDIUM", medium.getId(), true);
                    t.setLength(12);
                    t.setWidth(2.55);
                    t.setPayload(36000);
                    t.setDescription("Perfect for the transport of medium sized animals.");
                    truckRepository.save(t);
                }

                for(int i=0; i<5; i++) {
                    Truck t = new Truck("SMALL", small.getId(), true);
                    t.setLength(10);
                    t.setWidth(2.55);
                    t.setPayload(25000);
                    t.setDescription("Small but powerful.");
                    truckRepository.save(t);
                }

                for(int i=0; i<7; i++) {
                    Truck t = new Truck("TINY", tiny.getId(), true);
                    t.setLength(8);
                    t.setWidth(2.55);
                    t.setPayload(18000);
                    t.setDescription("Only for minor payloads.");
                    truckRepository.save(t);
                }

            } catch (IOException e) {
                // TODO: Error handling

            }

        }
    }
}
