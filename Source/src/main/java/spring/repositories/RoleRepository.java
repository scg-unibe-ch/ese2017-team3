package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.entity.Role;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName (String roleName);
}
