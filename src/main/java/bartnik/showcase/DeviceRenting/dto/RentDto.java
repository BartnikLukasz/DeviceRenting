package bartnik.showcase.DeviceRenting.dto;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentDto {

    @NotBlank
    private String customerFullName;

    @NotBlank
    private String deviceName;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @NotNull
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;
}
