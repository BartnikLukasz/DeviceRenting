package bartnik.showcase.DeviceRenting.services;

import bartnik.showcase.DeviceRenting.config.MessageProvider;
import bartnik.showcase.DeviceRenting.entities.DeviceEntity;
import bartnik.showcase.DeviceRenting.repositories.DeviceRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DeviceServiceTest {

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private MessageProvider messageProvider;

    private DeviceService deviceService;

    private ArrayList<DeviceEntity> devices = new ArrayList<>();

    @BeforeEach
    void setUp() {
        deviceService = new DeviceService(messageProvider, deviceRepository);

        devices.add(DeviceEntity.builder()
        .id(1L)
        .name("name")
        .productionDate(LocalDate.now())
        .serialNumber("serial")
        .build());
    }

    @Test
    void getDeviceByName_should_get_device() throws NotFoundException {
        when(deviceRepository.findByName(anyString())).thenReturn(Optional.of(devices.get(0)));
        when(messageProvider.getName()).thenReturn(Map.of("device", "Device"));

        DeviceEntity device = deviceService.getDeviceByName("name");

        verify(deviceRepository).findByName(anyString());

        assertEquals(devices.get(0), device);
    }

    @Test
    void getDeviceByName_should_throw_NotFoundException(){
        devices.clear();
        when(deviceRepository.findByName(anyString())).thenReturn(devices.stream().findFirst());
        when(messageProvider.getName()).thenReturn(Map.of("device", "Device"));

        assertThrows(NotFoundException.class, () -> deviceService.getDeviceByName("name"));

        verify(deviceRepository).findByName(anyString());
        verify(messageProvider).getName();
    }
}