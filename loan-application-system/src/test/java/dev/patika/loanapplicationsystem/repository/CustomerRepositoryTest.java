package dev.patika.loanapplicationsystem.repository;

import dev.patika.loanapplicationsystem.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    // Without using Mockito repository directly will be tested
    @Autowired
    CustomerRepository customerRepository;

/*
    @Test
    void shouldCheckWhenCustomerIdNumberExists() {
        // given
        Customer customer = new Customer("Arthur Dent", 53436704844L,5000,"05352586936",550);
        customerRepository.save(customer);

        // when
        boolean expected = customerRepository.existsByIdNumber(customer.getIdNumber());

        // then
        assertTrue(expected);
    }
*/

    @Test
    void findCustomerByIdNumber() {
    }
}