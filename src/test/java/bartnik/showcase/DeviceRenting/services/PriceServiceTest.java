package bartnik.showcase.DeviceRenting.services;

import bartnik.showcase.DeviceRenting.config.MessageProvider;
import bartnik.showcase.DeviceRenting.dto.PriceListRequestDto;
import bartnik.showcase.DeviceRenting.entities.DeviceEntity;
import bartnik.showcase.DeviceRenting.entities.PriceEntity;
import bartnik.showcase.DeviceRenting.mappers.PriceMapper;
import bartnik.showcase.DeviceRenting.repositories.PriceRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PriceServiceTest {

    @MockBean
    private MessageProvider messageProvider;

    @MockBean
    private PriceMapper priceMapper;

    @MockBean
    private PriceRepository priceRepository;

    private PriceService priceService;

    private ArrayList<PriceEntity> prices;

    @BeforeEach
    void setUp() {
        priceService = new PriceService(messageProvider, priceMapper, priceRepository);
        prices = new ArrayList<>();
        DeviceEntity device = DeviceEntity.builder()
                .id(1L)
                .name("name")
                .productionDate(LocalDate.now())
                .serialNumber("serial")
                .build();
        prices.add(PriceEntity.builder()
                .pricePerMinute(BigDecimal.valueOf(10d))
                .id(1L)
                .deviceEntity(device)
                .build());
    }

    @Test
    void setPrice_should_save_price() throws NotFoundException {
        PriceEntity price = prices.get(0);
        DeviceEntity device = DeviceEntity.builder()
                .id(2L)
                .name("name2")
                .productionDate(LocalDate.now())
                .serialNumber("serial")
                .build();
        PriceEntity price2 = PriceEntity.builder()
                .pricePerMinute(BigDecimal.valueOf(6d))
                .id(2L)
                .deviceEntity(device)
                .build();
        prices.clear();
        when(priceRepository.save(any(PriceEntity.class))).thenAnswer(p -> p.getArguments()[0]);
        when(priceRepository.findPriceByDeviceName(anyString())).thenReturn(prices.stream().findFirst());
        when(priceMapper.mapPriceRequestDtoToPriceEntity(any())).thenReturn(price2);

        PriceEntity entity = priceService.setPrice(PriceListRequestDto.builder()
                .pricePerMinute(BigDecimal.valueOf(6d))
                .deviceName("name2")
                .build());

        verify(priceRepository).findPriceByDeviceName(any());
        verify(priceRepository).save(any());
        verify(priceMapper).mapPriceRequestDtoToPriceEntity(any());

        assertEquals(price2, entity);
    }

    @Test
    void setPrice_should_override_price() throws NotFoundException {
        PriceEntity price = prices.get(0);
        DeviceEntity device = DeviceEntity.builder()
                .id(2L)
                .name("name2")
                .productionDate(LocalDate.now())
                .serialNumber("serial")
                .build();
        PriceEntity price2 = PriceEntity.builder()
                .pricePerMinute(BigDecimal.valueOf(6d))
                .id(2L)
                .deviceEntity(device)
                .build();
        when(priceRepository.save(any(PriceEntity.class))).thenAnswer(p -> p.getArguments()[0]);
        when(priceRepository.findPriceByDeviceName(anyString())).thenReturn(prices.stream().findFirst());
        when(priceMapper.mapPriceRequestDtoToPriceEntity(any())).thenReturn(price2);

        PriceEntity entity = priceService.setPrice(PriceListRequestDto.builder()
                .pricePerMinute(BigDecimal.valueOf(6d))
                .deviceName("name2")
                .build());

        verify(priceRepository).findPriceByDeviceName(any());
        verify(priceRepository).save(any());

        assertEquals(price, entity);
    }

    @Test
    void calculatePrice_should_calculate() throws NotFoundException {
        when(priceRepository.findPriceValueByName(anyString())).thenReturn(java.util.Optional.of(BigDecimal.valueOf(6d)));

        BigDecimal price = priceService.calculatePrice("name",
                LocalDateTime.of(10, 10, 10, 10, 10),
                LocalDateTime.of(10, 10, 10, 10, 20));

        verify(priceRepository).findPriceValueByName(anyString());

        assertEquals(BigDecimal.valueOf(60d), price);
    }

    @Test
    void calculatePrice_should_throw_NotFoundException() throws NotFoundException {
        prices.clear();
        Optional<PriceEntity> priceEntity = prices.stream().findFirst();
        Optional<BigDecimal> optionalPrice = Optional.ofNullable(null);
        when(priceRepository.findPriceValueByName(anyString())).thenReturn(optionalPrice);

        assertThrows(NotFoundException.class, () -> priceService.calculatePrice("name",
                LocalDateTime.of(10, 10, 10, 10, 10),
                LocalDateTime.of(10, 10, 10, 10, 20)));

        verify(priceRepository).findPriceValueByName(anyString());
    }
}