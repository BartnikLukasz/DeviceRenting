package bartnik.showcase.DeviceRenting.mappers;

import bartnik.showcase.DeviceRenting.dto.PriceListRequestDto;
import bartnik.showcase.DeviceRenting.entities.PriceEntity;
import bartnik.showcase.DeviceRenting.services.DeviceService;
import javassist.NotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PriceMapper {

    @Autowired
    protected DeviceService deviceService;

    @Mapping(target = "pricePerMinute", ignore = true)
    @Mapping(target = "deviceEntity", expression = "java(deviceService.getDeviceByName(request.getDeviceName()))")
    public abstract PriceEntity mapPriceRequestDtoToPriceEntity(PriceListRequestDto request) throws NotFoundException;
}
