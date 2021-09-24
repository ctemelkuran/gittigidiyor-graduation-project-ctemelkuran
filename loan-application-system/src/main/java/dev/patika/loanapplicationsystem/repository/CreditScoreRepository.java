package dev.patika.loanapplicationsystem.repository;

import dev.patika.loanapplicationsystem.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {

    int findCreditScoreByLastDigitOfIdNumber(long lastDigit);
}
