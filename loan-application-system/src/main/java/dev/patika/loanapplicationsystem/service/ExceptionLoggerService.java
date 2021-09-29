package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.entity.ExceptionLogger;
import dev.patika.loanapplicationsystem.entity.LoanApplicationLogger;
import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import dev.patika.loanapplicationsystem.exceptions.ErrorResponse;
import dev.patika.loanapplicationsystem.repository.ExceptionLoggerRepository;
import dev.patika.loanapplicationsystem.service.validators.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExceptionLoggerService {
    private final ExceptionLoggerRepository exceptionLoggerRepository;

    public void saveException(ErrorResponse response, String exceptionType) {
        ExceptionLogger logger = new ExceptionLogger();
        logger.setExceptionType(exceptionType);
        logger.setDate(LocalDate.now());
        logger.setMessage(response.getMessage());
        this.exceptionLoggerRepository.save(logger);

    }

    public Page<List<ExceptionLogger>> getAllExceptionsByDate(String exceptionDate, Integer pageNumber, Integer pageSize, Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        CustomerValidator.validateDate(exceptionDate, formatter);
        LocalDate exceptionDateResult = LocalDate.parse(exceptionDate, formatter);
        if(pageNumber != null && pageSize != null){
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return this.exceptionLoggerRepository.findAllExceptionsByDate(exceptionDateResult, pageable);
    }

    public Page<List<ExceptionLogger>> getAllExceptionsByType(String exceptionType, Integer pageNumber, Integer pageSize, Pageable pageable) {

        if(pageNumber != null && pageSize != null){
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return this.exceptionLoggerRepository.findAllByExceptionTypeContains(exceptionType, pageable);
    }
}
