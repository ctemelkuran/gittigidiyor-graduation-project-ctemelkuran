package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.exceptions.CustomerNotFoundException;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository mockCustomerRepository;

    @Mock
    CustomerMapper mockCustomerMapper;

    @InjectMocks
    CustomerService customerService;

    Customer customerTest;
    CustomerDTO customerDTOTest;

    @BeforeEach
    void setup() {
        this.customerTest = new Customer();
        this.customerDTOTest = new CustomerDTO();
    }

    @Test
    void saveCustomer() { // TODO Customer service tests "why mapper returns null"
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdNumber(52362256090L);
        when(mockCustomerRepository.existsByIdNumber(anyLong())).thenReturn(Boolean.FALSE);
        when(mockCustomerMapper.mapFromCustomerDTOtoCustomer(any())).thenReturn(customerTest);
        when(mockCustomerRepository.save(any())).thenReturn(customerTest);
        // when
        CustomerDTO actual = this.customerService.saveCustomer(customerDTO).get();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(customerDTO, actual),
                () -> assertEquals(customerDTO.getIdNumber(), actual.getIdNumber())
        );
    }

    @Test
    void getAllCustomers() {
        List<Customer> expected = new ArrayList<>();
        when(mockCustomerRepository.findAll()).thenReturn(expected);

        //Set<CustomerDTO> actual = customerService.getAllCustomers();

        //TODO assertTrue(!actual.isEmpty());
    }

    @Test
    void updateShouldThrowExceptionWhenIdNotExists() {
        CustomerDTO dto = new CustomerDTO();
        Customer customer = new Customer();
        dto.setId(1L);

        when(mockCustomerRepository.findById(dto.getId())).thenReturn(Optional.of(customer));

        assertThrows(CustomerNotFoundException.class, () ->{
            customerService.updateCustomer(dto, 2L);
        });

    }

    @Test
    void updateTest() {
        Customer customer = new Customer();
        customer.setId(1L);
        CustomerDTO customerDTO = new CustomerDTO();

        when(mockCustomerMapper.mapFromCustomerDTOtoCustomer(any())).thenReturn(customer);
        when(mockCustomerRepository.save(any())).thenReturn(customer);

        CustomerDTO expected = mockCustomerMapper.mapFromCustomerToCustomerDTO(customer);
        customerDTO.setId(1L);
        CustomerDTO actual = customerService.updateCustomer(customerDTO, 1L).get();

        assertEquals(expected, actual);


    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }
}