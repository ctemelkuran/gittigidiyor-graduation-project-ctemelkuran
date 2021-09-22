package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.CreditScore;
import dev.patika.loanapplicationsystem.repository.CreditScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditScoreService {
    private final CreditScoreRepository creditScoreRepository;

    @Transactional(readOnly = true)
    public Set<CreditScore> gelAllCreditScores() {

        return creditScoreRepository.findAll().stream().collect(Collectors.toSet());

    }

}
