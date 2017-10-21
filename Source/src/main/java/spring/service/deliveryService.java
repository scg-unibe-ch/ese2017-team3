package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.repositories.DeliveryRepository;
import spring.entity.delivery;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by olulrich on 20.10.17.
 */

@Service
public class deliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public DeliveryRepository getDeliveryRepository() {
        return deliveryRepository;
    }
}
