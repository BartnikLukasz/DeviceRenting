package bartnik.showcase.DeviceRenting.dto;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceListRequestDto {

    @NotNull
    @Digits(integer = 6, fraction = 2)
    BigDecimal pricePerMinute;

    @NotBlank
    String deviceName;
}
