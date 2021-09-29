package dev.patika.loanapplicationsystem.repository;

import dev.patika.loanapplicationsystem.entity.LoanApplicationLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanApplicationLoggerRepository extends PagingAndSortingRepository<LoanApplicationLogger, Long> {
    @Query("SELECT a FROM LoanApplicationLogger a WHERE a.loanApplicationDate= ?1")
    Page<List<LoanApplicationLogger>> findAllApplicationsByDate(LocalDate applicationDate, Pageable pageable);

}
