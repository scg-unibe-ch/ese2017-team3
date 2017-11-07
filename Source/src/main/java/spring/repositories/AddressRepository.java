package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.Address;

/**
 * Created by julian on 5.11.17
 */
public interface AddressRepository extends CrudRepository<Address, Long> {


}
