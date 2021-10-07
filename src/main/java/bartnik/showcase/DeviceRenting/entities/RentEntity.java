package bartnik.showcase.DeviceRenting.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rent")
public class RentEntity {

    @Id
    @GeneratedValue
    Long id;

    @NotNull
    CustomerEntity customerEntity;

    @NotNull
    DeviceEntity deviceEntity;

    @NotNull
    LocalDateTime startDateTime;

    @NotNull
    LocalDateTime endDateTime;

    @NotNull
    @Digits(integer = 8, fraction = 2)
    BigDecimal price;
}
