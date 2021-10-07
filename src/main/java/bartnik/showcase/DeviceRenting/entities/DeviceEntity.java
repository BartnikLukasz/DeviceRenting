package bartnik.showcase.DeviceRenting.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "devices")
public class DeviceEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @NotBlank
    private String serialNumber;

    private LocalDate productionDate;
}
