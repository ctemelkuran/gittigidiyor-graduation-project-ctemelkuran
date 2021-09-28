package dev.patika.loanapplicationsystem.controller;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.CreditScore;
import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import dev.patika.loanapplicationsystem.service.LoanApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class LoanApplicationControllerTest {

    @Mock
    LoanApplicationService mockLoanService;

    @InjectMocks
    LoanApplicationController loanApplicationController;


    @Test
    void applyToLoan() {
        LoanApplicationResult result = new LoanApplicationResult();
        CustomerDTO customerDTO = new CustomerDTO();
        when(mockLoanService.applyToLoan(customerDTO)).thenReturn(result);

        ResponseEntity<LoanApplicationResult> actual = loanApplicationController.applyToLoan(customerDTO);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(HttpStatus.OK, actual.getStatusCode())
        );
    }

    @Test
    void loanApplicationResult() {
        Set<LoanApplicationResult> expected = new HashSet<>();
        when(mockLoanService.getApplicationResult(anyLong())).thenReturn(expected);

        ResponseEntity<Set<LoanApplicationResult>> actual = loanApplicationController.loanApplicationResult(anyLong());

        assertAll(
                () -> assertEquals(HttpStatus.OK, actual.getStatusCode()),
                () -> assertEquals(expected.size(), actual.getBody().size())
        );
    }


}