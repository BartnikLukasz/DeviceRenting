package bartnik.showcase.DeviceRenting.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "price_list")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "device_id", referencedColumnName = "id", unique = true)
    private DeviceEntity deviceEntity;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    private BigDecimal pricePerMinute;
}
