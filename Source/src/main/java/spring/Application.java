package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import spring.entity.Truck;
import spring.repositories.TruckRepository;
import spring.storage.StorageProperties;
import spring.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

    // Add java 8 time API support for thymeleaf
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(20971520);   // 20MB
//        multipartResolver.setMaxInMemorySize(1048576);  // 1MB
//        multipartResolver.setDefaultEncoding("utf-8");
//        return multipartResolver;
//    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Autowired
    static TruckRepository truckRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
