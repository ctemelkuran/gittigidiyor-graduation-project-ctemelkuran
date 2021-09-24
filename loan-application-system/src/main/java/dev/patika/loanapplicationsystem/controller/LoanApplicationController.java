package dev.patika.loanapplicationsystem.controller;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import dev.patika.loanapplicationsystem.service.CustomerService;
import dev.patika.loanapplicationsystem.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/loan-application")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResult> applyToLoan (@RequestBody @Valid CustomerDTO customerDTO) {


        return new ResponseEntity<>(loanApplService.applyToLoan(customerDTO), HttpStatus.OK);
    }

    @GetMapping("/result")
    public ResponseEntity<Set<LoanApplicationResult>> loanApplicationResult(@RequestParam long idNumber){
        return new ResponseEntity<>(loanApplService.getApplicationResult(idNumber), HttpStatus.OK);
    }

}
