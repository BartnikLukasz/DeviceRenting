package bartnik.showcase.DeviceRenting.services;

import bartnik.showcase.DeviceRenting.config.MessageProvider;
import bartnik.showcase.DeviceRenting.dto.PriceListRequestDto;
import bartnik.showcase.DeviceRenting.entities.PriceEntity;
import bartnik.showcase.DeviceRenting.mappers.PriceMapper;
import bartnik.showcase.DeviceRenting.repositories.PriceRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PriceService {

    private final MessageProvider messageProvider;
    private final PriceMapper priceMapper;
    private final PriceRepository priceRepository;

    public PriceEntity setPrice(PriceListRequestDto price) throws NotFoundException {
        PriceEntity priceEntity = priceRepository.findPriceByDeviceName(price.getDeviceName())
                .orElse(priceMapper.mapPriceRequestDtoToPriceEntity(price));
        priceEntity.setPricePerMinute(price.getPricePerMinute());
        priceRepository.save(priceEntity);
        return priceEntity;
    }

    private BigDecimal getPrice(String deviceName) throws NotFoundException {
        return priceRepository.findPriceValueByName(deviceName).orElseThrow(() -> new NotFoundException(messageProvider.getName().get("price")));
    }

    public BigDecimal calculatePrice(String deviceName, LocalDateTime startDate, LocalDateTime endDate) throws NotFoundException {
        return getPrice(deviceName).multiply(BigDecimal.valueOf(Duration.between(startDate, endDate).toMinutes()));
    }
}
