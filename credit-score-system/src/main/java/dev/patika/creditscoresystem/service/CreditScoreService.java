package dev.patika.creditscoresystem.service;


import dev.patika.creditscoresystem.entity.CreditScore;
import dev.patika.creditscoresystem.exceptions.CreditScoreNotFoundException;
import dev.patika.creditscoresystem.repository.CreditScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreditScoreService {
    private final CreditScoreRepository creditScoreRepository;

    /**
     * Find all credit scores that are saved in the database.
     *
     * @return {@link Set<CreditScore>} to show all credit scores
     */
    @Transactional(readOnly = true)
    public Set<CreditScore> gelAllCreditScores() {
        return new HashSet<>(creditScoreRepository.findAll());
    }


    /**
     * Update credit scores in the database
     *
     * @param creditScore
     * @return
     */
    @Transactional
    public CreditScore updateCreditScore(CreditScore creditScore) {

        Optional<CreditScore> foundCreditScore = creditScoreRepository.findById(creditScore.getId());

        if (foundCreditScore.isEmpty()){
            throw new CreditScoreNotFoundException("Credit score not found!");
        }

        return creditScoreRepository.save(creditScore);
    }

    @Transactional(readOnly = true)
    public CreditScore findByLastDigit(int lastDigit) {
        return creditScoreRepository.findCreditScoreByLastDigitOfIdNumberEquals(lastDigit);
    }


}
