package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import spring.entity.Truck;
import spring.repositories.TruckRepository;

@SpringBootApplication
public class Application {

    // Add java 8 time API support for thymeleaf
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    @Autowired
    static TruckRepository truckRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
