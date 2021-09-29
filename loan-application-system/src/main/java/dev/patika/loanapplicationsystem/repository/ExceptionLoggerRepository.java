package dev.patika.loanapplicationsystem.repository;

import dev.patika.loanapplicationsystem.entity.ExceptionLogger;
import dev.patika.loanapplicationsystem.entity.LoanApplicationLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface  ExceptionLoggerRepository extends PagingAndSortingRepository<ExceptionLogger, Long> {
    @Query("SELECT e FROM ExceptionLogger e WHERE e.date= ?1")
    Page<List<ExceptionLogger>> findAllExceptionsByDate(LocalDate exceptionDate, Pageable pageable);

    Page<List<ExceptionLogger>> findAllByExceptionTypeContains(String exceptionType, Pageable pageable);
}
