package bartnik.showcase.DeviceRenting.services;

import bartnik.showcase.DeviceRenting.dto.RentRequestDto;
import bartnik.showcase.DeviceRenting.dto.ReportResponseDto;
import bartnik.showcase.DeviceRenting.entities.CustomerEntity;
import bartnik.showcase.DeviceRenting.entities.DeviceEntity;
import bartnik.showcase.DeviceRenting.entities.RentEntity;
import bartnik.showcase.DeviceRenting.mappers.RentMapper;
import bartnik.showcase.DeviceRenting.repositories.RentRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RentServiceTest {

    @MockBean
    private RentMapper rentMapper;

    @MockBean
    private RentRepository rentRepository;

    private RentService rentService;

    ArrayList<RentEntity> rents;

    @BeforeEach
    void setUp() {
        rentService = new RentService(rentMapper, rentRepository);

        rents = new ArrayList<>();

        CustomerEntity customer = CustomerEntity.builder()
                .id(1L)
                .name("Name")
                .surname("Surname")
                .email("Email@email.com")
                .build();
        DeviceEntity device = DeviceEntity.builder()
                .id(1L)
                .name("name")
                .productionDate(LocalDate.now())
                .serialNumber("serial")
                .build();

        rents.add(RentEntity.builder()
                .id(1L)
                .customerEntity(customer)
                .deviceEntity(device)
                .price(BigDecimal.valueOf(200d))
                .startDateTime(LocalDateTime.now().minusMinutes(10))
                .endDateTime(LocalDateTime.now())
                .build());

    }

    @Test
    void saveRent_should_add_rent() throws NotFoundException {
        RentEntity rentEntity = rents.get(0);
        rents.clear();
        when(rentMapper.mapRentRequestDtoToRentEntity(any())).thenReturn(rentEntity);
        when(rentRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        rentService.saveRent(new RentRequestDto());

        verify(rentMapper).mapRentRequestDtoToRentEntity(any());
        verify(rentRepository).save(any());

    }

    @Test
    void createReport_should_return_rent() {
        when(rentRepository.getAllRentalsForUserInPeriod(any(), any(), any())).thenReturn(rents);

        ReportResponseDto response = rentService.createReport(1L, LocalDate.now());

        verify(rentRepository).getAllRentalsForUserInPeriod(any(), any(), any());

        assertEquals(BigDecimal.valueOf(200d), response.getCharge());
    }
}