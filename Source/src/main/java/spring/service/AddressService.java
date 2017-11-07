package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.repositories.AddressRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 5.11.17.
 */

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
}
