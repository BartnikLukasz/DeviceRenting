package bartnik.showcase.DeviceRenting.repositories;

import bartnik.showcase.DeviceRenting.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.deviceEntity.name = :deviceName")
    Optional<PriceEntity> findPriceByDeviceName(@Param("deviceName") String deviceName);

    @Query("SELECT p.pricePerMinute FROM PriceEntity p WHERE p.deviceEntity.name = :deviceName")
    Optional<BigDecimal> findPriceValueByName(@Param("deviceName") String deviceName);

}
