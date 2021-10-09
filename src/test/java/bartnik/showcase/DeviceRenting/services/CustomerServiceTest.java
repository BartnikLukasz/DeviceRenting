package bartnik.showcase.DeviceRenting.services;

import bartnik.showcase.DeviceRenting.config.MessageProvider;
import bartnik.showcase.DeviceRenting.entities.CustomerEntity;
import bartnik.showcase.DeviceRenting.repositories.CustomerRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private MessageProvider messageProvider;

    private CustomerService customerService;

    private ArrayList<CustomerEntity> customers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(messageProvider, customerRepository);

        customers.add(CustomerEntity.builder()
                .id(1L)
                .name("Name")
                .surname("Surname")
                .email("Email@email.com")
                .build());
    }

    @Test
    void getCustomerById_should_get_customer() throws NotFoundException {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customers.get(0)));
        when(messageProvider.getName()).thenReturn(Map.of("customer", "Customer"));

        CustomerEntity customer = customerService.getCustomerById(1L);

        verify(customerRepository).findById(anyLong());

        assertEquals(customers.get(0), customer);
    }

    @Test
    void getCustomerById_should_throw_NotFoundException() {
        customers.clear();
        when(customerRepository.findById(anyLong())).thenReturn(customers.stream().findFirst());
        when(messageProvider.getName()).thenReturn(Map.of("customer", "Customer"));

        assertThrows(NotFoundException.class, () -> customerService.getCustomerById(1L));

        verify(customerRepository).findById(anyLong());
        verify(messageProvider).getName();
    }
}