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
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    RestTemplate restTemplate;

    static final String SMS_SENDER_API = "http://sms-sender-service/api";

    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResult> applyToLoan (@RequestBody @Valid CustomerDTO customerDTO) {

        String phoneNumber = customerDTO.getPhoneNumber();

        LoanApplicationResult applicationResult = loanApplService.applyToLoan(customerDTO);

        //restTemplate.getForEntity(SMS_SENDER_API + "/"+ phoneNumber + "/"+applicationResult, String.class);

        return new ResponseEntity<>(applicationResult, HttpStatus.OK);
    }


    @GetMapping("/result")
    public ResponseEntity<Set<LoanApplicationResult>> loanApplicationResult(@RequestParam long idNumber){
        return new ResponseEntity<>(loanApplService.getApplicationResult(idNumber), HttpStatus.OK);
    }
}
