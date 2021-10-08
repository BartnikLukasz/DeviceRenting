package bartnik.showcase.DeviceRenting.repositories;

import bartnik.showcase.DeviceRenting.entities.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {

    @Query(value = "SELECT r FROM RentEntity r " +
            "WHERE r.customerEntity.id = :customerId " +
            "AND (r.endDateTime BETWEEN :periodFrom AND :periodTo)")
    ArrayList<RentEntity> getAllRentalsForUserInPeriod(@Param("customerId") Long customerId, @Param("periodFrom") LocalDateTime periodFrom, @Param("periodTo") LocalDateTime periodTo);
}
