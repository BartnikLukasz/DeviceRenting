package bartnik.showcase.DeviceRenting.mappers;

import bartnik.showcase.DeviceRenting.dto.RentDto;
import bartnik.showcase.DeviceRenting.dto.RentRequestDto;
import bartnik.showcase.DeviceRenting.entities.RentEntity;
import bartnik.showcase.DeviceRenting.services.CustomerService;
import bartnik.showcase.DeviceRenting.services.DeviceService;
import bartnik.showcase.DeviceRenting.services.PriceService;
import javassist.NotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RentMapper {

    @Autowired
    protected DeviceService deviceService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected PriceService priceService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerEntity", expression = "java(customerService.getCustomerById(request.getCustomerId()))")
    @Mapping(target = "deviceEntity", expression = "java(deviceService.getDeviceByName(request.getDeviceName()))")
    @Mapping(target = "price", expression = "java(priceService.calculatePrice(request.getDeviceName(), request.getStartDateTime(), request.getEndDateTime()))")
    public abstract RentEntity mapRentRequestDtoToRentEntity(RentRequestDto request) throws NotFoundException;

    @Mapping(target = "customerFullName", expression= "java(" +
            "org.apache.commons.lang3.StringUtils.join(" +
            "entity.getCustomerEntity().getName(), ' ', " +
            "entity.getCustomerEntity().getSurname()))")
    @Mapping(target = "deviceName", source = "deviceEntity.name")
    public abstract RentDto mapRentEntityToRentDto(RentEntity entity);
}
