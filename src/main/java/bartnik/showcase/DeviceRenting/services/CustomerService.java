package bartnik.showcase.DeviceRenting.services;

import bartnik.showcase.DeviceRenting.config.MessageProvider;
import bartnik.showcase.DeviceRenting.entities.CustomerEntity;
import bartnik.showcase.DeviceRenting.repositories.CustomerRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final MessageProvider messageProvider;
    private final CustomerRepository customerRepository;

    public CustomerEntity getCustomerById(Long id) throws NotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException(messageProvider.getName().get("customer")));
    }
}
