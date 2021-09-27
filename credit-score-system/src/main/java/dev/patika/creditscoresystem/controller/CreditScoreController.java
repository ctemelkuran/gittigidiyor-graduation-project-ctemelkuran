package dev.patika.creditscoresystem.controller;

import dev.patika.creditscoresystem.entity.CreditScore;
import dev.patika.creditscoresystem.service.CreditScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/credit-scores")
@RequiredArgsConstructor
public class CreditScoreController {

    private final CreditScoreService creditScoreService;

    @GetMapping
    public ResponseEntity<Set<CreditScore>> getAllCreditScores(){
        return new ResponseEntity<>(creditScoreService.gelAllCreditScores(), HttpStatus.OK);
    }

    @GetMapping("/{lastDigit}")
    public ResponseEntity<CreditScore> getCreditScore (@PathVariable int lastDigit){
        return new ResponseEntity<>(creditScoreService.findByLastDigit(lastDigit), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CreditScore> updateCreditScore(@RequestBody CreditScore creditScore){
        CreditScore updatedCreditScore = creditScoreService.updateCreditScore(creditScore);

        return  new ResponseEntity<>(updatedCreditScore, HttpStatus.OK);
    }

}
