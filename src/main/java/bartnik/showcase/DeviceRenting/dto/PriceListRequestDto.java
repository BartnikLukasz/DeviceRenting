package bartnik.showcase.DeviceRenting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PriceListRequestDto {

    @NotNull
    @Digits(integer = 6, fraction = 2)
    BigDecimal pricePerMinute;

    @NotBlank
    String deviceName;
}
