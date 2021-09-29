package dev.patika.loanapplicationsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.CreditScore;
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
    RestTemplate restTemplate;

    static final String CREDIT_SCORE_ENDPOINT = "http://localhost:8090/api/credit-scores/";


    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationResult> applyToLoan (@RequestBody @Valid CustomerDTO customerDTO) {

        LoanApplicationResult applicationResult = loanApplService.applyToLoan(customerDTO);

        return new ResponseEntity<>(applicationResult, HttpStatus.OK);
    }


    @GetMapping("/result")
    public ResponseEntity<Set<LoanApplicationResult>> getLoanApplicationResult(@RequestParam long idNumber){
        return new ResponseEntity<>(loanApplService.getApplicationResult(idNumber), HttpStatus.OK);
    }

    @GetMapping("/get-credit-score/{lastDigit}")
    public ResponseEntity<CreditScore> getCreditScore (@PathVariable int lastDigit){
        CreditScore creditScore = restTemplate.getForObject(CREDIT_SCORE_ENDPOINT+lastDigit, CreditScore.class);
        return new ResponseEntity<>(creditScore, HttpStatus.OK);
    }

}
