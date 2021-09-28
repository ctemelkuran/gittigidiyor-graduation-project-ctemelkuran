package dev.patika.loanapplicationsystem.controller;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.LoanApplicationLogger;
import dev.patika.loanapplicationsystem.service.CustomerService;
import dev.patika.loanapplicationsystem.service.LoanApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    // Mocked required service, because the original service must not get called.
    @Mock
    CustomerService mockCustomerService;
    @Mock
    LoanApplicationService mockLoanAppService;

    // Mock required injection for Controller
    @InjectMocks
    CustomerController customerController;


    @Test
    void should_ReturnNotNullAndEquals_When_givenDtoNotNull() {
        // given
        CustomerDTO dto = new CustomerDTO();
        Optional<CustomerDTO> expected = Optional.of(dto);
        // Any object is given to saveCustomer method, for primitive types anyInt etc. is used.
        Mockito.when(mockCustomerService.saveCustomer(Mockito.any())).thenReturn(expected);

        // when
        CustomerDTO actual = this.customerController.saveCustomer(dto).getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.get(), actual)
        );

    }

    @Test
    void should_ReturnEquals_When_dtoEmpty() {
        // given
        when(mockCustomerService.saveCustomer(any())).thenReturn(Optional.empty());

        // when
        CustomerDTO dto = new CustomerDTO();
        ResponseEntity<CustomerDTO> actual = this.customerController.saveCustomer(dto);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void getAllCustomers() {
        // given
        when(mockCustomerService.getAllCustomers()).thenReturn(anySet());
        // when
        ResponseEntity<Set<CustomerDTO>> expected = this.customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, expected.getStatusCode());


    }

    @Test
    void updateCustomer() {
        // given

        CustomerDTO dto = new CustomerDTO();
        Optional<CustomerDTO> expected = Optional.of(dto);
        // Any object is given to saveCustomer method, for primitive types anyInt etc. is used.
        Mockito.when(mockCustomerService.updateCustomer(any(), anyLong())).thenReturn(expected);

        // when
        CustomerDTO actual = this.customerController.updateCustomer(1L,dto).getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.get(), actual)
        );
    }

    @Test
    void shouldReturnEqualsWhenGivenAnyLong() {
        // given
        String expected = "deleted";
        when(mockCustomerService.deleteById(anyLong())).thenReturn(expected);

        // when
        ResponseEntity<String> actual = customerController.delete(anyLong());

        // then
        assertAll(
                () -> assertEquals(HttpStatus.OK, actual.getStatusCode()),
                () -> assertEquals(expected, actual.getBody())
        );

    }

    @Test
    void getAllApplicationsByDate() {
        Page<List<LoanApplicationLogger>> excepted = Page.empty();
        when(mockLoanAppService.getAllTransactionsByDate(
                anyString(),anyInt(),anyInt(),any())).thenReturn(excepted);

        Page<List<LoanApplicationLogger>> actual
                = customerController.getAllApplicationsByDate(
                        anyString(),anyInt(),anyInt(),any()).getBody();

        assertAll(
                () -> assertEquals(excepted, actual),
                () -> assertNotNull(actual)
        );


    }
}