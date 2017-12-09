package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.entity.Animal;

import java.util.List;

/**
 * Created by olulrich on 09.12.17.
 */

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long> {

    List<Animal> findAll();

    Animal findAnimalBySpecies(String species);
}
