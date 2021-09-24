package dev.patika.loanapplicationsystem.controller;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.service.CustomerService;
import dev.patika.loanapplicationsystem.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/loan-application")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplService;
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<String> applyToLoan (@RequestBody @Valid CustomerDTO customerDTO) {

        return null;
    }

}
