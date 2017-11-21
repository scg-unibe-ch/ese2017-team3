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
                    truckRepository.save(new Truck("BIG", big.getId(), true));
                }

                for(int i=0; i<3; i++) {
                    truckRepository.save(new Truck("MEDIUM", medium.getId(), true));
                }

                for(int i=0; i<5; i++) {
                    truckRepository.save(new Truck("SMALL", small.getId(), true));
                }

                for(int i=0; i<7; i++) {
                    truckRepository.save(new Truck("TINY", tiny.getId(), true));
                }

            } catch (IOException e) {
                // TODO: Error handling

            }

        }
    }
}
