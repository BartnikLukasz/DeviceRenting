package bartnik.showcase.DeviceRenting.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "price_list")
public class PriceEntity {

    @Id
    @GeneratedValue
    Long id;

    @NotNull
    @Column(unique = true)
    DeviceEntity deviceEntity;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    BigDecimal pricePerMinute;
}
