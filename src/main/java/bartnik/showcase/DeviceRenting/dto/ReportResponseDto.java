package bartnik.showcase.DeviceRenting.dto;

import bartnik.showcase.DeviceRenting.util.Period;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponseDto {

    private ArrayList<RentDto> rents;

    private BigDecimal charge;

    private Period period;
}
