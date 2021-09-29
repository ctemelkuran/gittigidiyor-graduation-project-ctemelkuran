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
    void saveCustomerTest() {
        // given
        customerDTOTest.setIdNumber(52362256090L);
        when(mockCustomerRepository.existsByIdNumber(anyLong())).thenReturn(Boolean.FALSE);
        when(mockCustomerMapper.mapFromCustomerDTOtoCustomer(any())).thenReturn(customerTest);
        when(mockCustomerRepository.save(any())).thenReturn(customerTest);
        when(mockCustomerMapper.mapFromCustomerToCustomerDTO(any())).thenReturn(customerDTOTest);
        // when
        CustomerDTO actual = this.customerService.saveCustomer(customerDTOTest).get();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(customerDTOTest, actual),
                () -> assertEquals(customerDTOTest.getIdNumber(), actual.getIdNumber())
        );
    }

    @Test
    void getAllCustomersTest() {
        List<Customer> expected = new ArrayList<>();
        when(mockCustomerRepository.findAll()).thenReturn(expected);

        Set<CustomerDTO> actual = customerService.getAllCustomers();

        assertTrue(actual.isEmpty());
    }

    @Test
    void updateShouldThrowExceptionWhenIdNotExists() {
        customerDTOTest.setId(1L);

        assertThrows(CustomerNotFoundException.class, () ->{
            customerService.updateCustomer(customerDTOTest, 2L);
        });

    }


/*    @Test
    void updateTest() {
        //given

        when(mockCustomerRepository.existsByIdNumber(anyLong())).thenReturn(Boolean.TRUE);
        when(mockCustomerRepository.existsByIdNumber(anyLong())).thenReturn(Boolean.TRUE);
        when(mockCustomerMapper.mapFromCustomerDTOtoCustomer(any())).thenReturn(customerTest);

        when(mockCustomerRepository.save(customerTest)).thenReturn(customerTest);
        when(mockCustomerMapper.mapFromCustomerToCustomerDTO(customerTest)).thenReturn(customerDTOTest);

        CustomerDTO expected = mockCustomerMapper.mapFromCustomerToCustomerDTO(customerTest);
        CustomerDTO actual = customerService.updateCustomer(customerDTOTest, anyLong()).get();

        assertEquals(expected, actual);

    }*/



}