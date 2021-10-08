package bartnik.showcase.DeviceRenting.services;

import bartnik.showcase.DeviceRenting.config.MessageProvider;
import bartnik.showcase.DeviceRenting.entities.DeviceEntity;
import bartnik.showcase.DeviceRenting.repositories.DeviceRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeviceService {

    private final MessageProvider messageProvider;
    private final DeviceRepository deviceRepository;

    public DeviceEntity getDeviceByName(String name) throws NotFoundException {
        return deviceRepository.findByName(name).orElseThrow(() -> new NotFoundException(messageProvider.getName().get("device")));
    }
}
