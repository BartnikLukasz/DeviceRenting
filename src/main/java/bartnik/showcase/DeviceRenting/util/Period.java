package bartnik.showcase.DeviceRenting.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Period {

    private LocalDate from;
    private LocalDate to;

    @Override
    public String toString() {
        return StringUtils.join(this.from.toString(), " - ", this.toString());
    }
}
