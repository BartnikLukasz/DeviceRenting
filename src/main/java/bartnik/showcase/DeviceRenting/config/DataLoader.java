package bartnik.showcase.DeviceRenting.config;

import bartnik.showcase.DeviceRenting.entities.CustomerEntity;
import bartnik.showcase.DeviceRenting.entities.DeviceEntity;
import bartnik.showcase.DeviceRenting.entities.PriceEntity;
import bartnik.showcase.DeviceRenting.entities.RentEntity;
import bartnik.showcase.DeviceRenting.repositories.CustomerRepository;
import bartnik.showcase.DeviceRenting.repositories.DeviceRepository;
import bartnik.showcase.DeviceRenting.repositories.PriceRepository;
import bartnik.showcase.DeviceRenting.repositories.RentRepository;
import bartnik.showcase.DeviceRenting.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final DeviceRepository deviceRepository;
    private final PriceRepository priceRepository;
    private final CustomerRepository customerRepository;
    private final RentRepository rentRepository;
    private final PriceService priceService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        DeviceEntity deviceEntity = DeviceEntity.builder()
                .name("Pralka")
                .productionDate(LocalDate.of(2010, 10, 10))
                .serialNumber("serial")
                .build();
        PriceEntity priceEntity = PriceEntity.builder()
                .deviceEntity(deviceEntity)
                .pricePerMinute(BigDecimal.valueOf(10d))
                .build();
        CustomerEntity customerEntity = CustomerEntity.builder()
                .name("Name")
                .email("email@email.com")
                .surname("Surname")
                .build();

        deviceRepository.save(deviceEntity);
        priceRepository.save(priceEntity);
        customerRepository.save(customerEntity);

        RentEntity rentEntity = RentEntity.builder()
                .deviceEntity(deviceEntity)
                .customerEntity(customerEntity)
                .price(priceService.calculatePrice(
                        deviceEntity.getName(),
                        LocalDateTime.of(2021, 10, 10, 10, 10),
                        LocalDateTime.of(2021, 10, 10, 11, 11)))
                .startDateTime(LocalDateTime.of(2021, 10, 10, 10, 10))
                .endDateTime(LocalDateTime.of(2021, 10, 10, 11, 11))
                .build();

        rentRepository.save(rentEntity);
    }
}
