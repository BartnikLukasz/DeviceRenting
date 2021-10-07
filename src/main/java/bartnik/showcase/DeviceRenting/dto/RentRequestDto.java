package bartnik.showcase.DeviceRenting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RentRequestDto {

    @NotNull
    Long customerId;

    @NotBlank
    String deviceName;

    @NotNull
    LocalDateTime startDateTime;

    @NotNull
    LocalDateTime endDateTime;
}
