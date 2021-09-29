package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class LoanApplicationServiceTest {

    @Mock
    CustomerRepository mockCustomerRepository;

    @InjectMocks
    LoanApplicationService loanApplicationService;

    @InjectMocks
    CustomerService customerService;

    Customer customerTest;
    CustomerDTO customerDTOTest;

    @BeforeEach
    void setup() {
        this.customerTest = new Customer();
        this.customerDTOTest = new CustomerDTO();
    }

    // TODO Loan application service tests
    @Test
    void applyToLoan() {

    }

    @Test
    void loanApplicationResult() {
    }

    @Test
    void getApplicationResult() {
    }

    @Test
    void getAllTransactionsByDate() {
    }
}