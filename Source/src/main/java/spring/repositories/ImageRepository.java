package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

}
