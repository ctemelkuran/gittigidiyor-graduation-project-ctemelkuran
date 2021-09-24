package dev.patika.loanapplicationsystem.repository;

import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationResultRepository extends JpaRepository<LoanApplicationResult, Long> {

}
