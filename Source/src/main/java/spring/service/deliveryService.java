package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.dao.deliveryDao;
import spring.entity.delivery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by olulrich on 20.10.17.
 */

@Service
public class deliveryService {

    @Autowired
    private deliveryDao deliveryDao;

    public ArrayList<delivery> getAllDeliveries() {
        return this.deliveryDao.getAllDeliveries();
    }
}
