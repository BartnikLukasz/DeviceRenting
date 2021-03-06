package bartnik.showcase.DeviceRenting.repositories;

import bartnik.showcase.DeviceRenting.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
