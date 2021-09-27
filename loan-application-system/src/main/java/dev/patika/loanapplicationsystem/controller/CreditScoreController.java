package dev.patika.loanapplicationsystem.controller;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.CreditScore;
import dev.patika.loanapplicationsystem.service.CreditScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/credit-scores")
@RequiredArgsConstructor
public class CreditScoreController {

    private final CreditScoreService creditScoreService;

    @GetMapping
    public ResponseEntity<Set<CreditScore>> getAllCreditScores(){
        return new ResponseEntity<>(creditScoreService.gelAllCreditScores(), HttpStatus.OK);
    }


}
