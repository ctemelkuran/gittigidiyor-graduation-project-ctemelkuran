package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.CreditScore;
import dev.patika.loanapplicationsystem.repository.CreditScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (!creditScoreRepository.existsById(creditScore.getId())){
            throw new EntityNotFoundException("Credit score not found!");
        }

        return creditScoreRepository.
                findCreditScoreByLastDigitOfIdNumberEquals(creditScore.getLastDigitOfIdNumber());
    }
}
