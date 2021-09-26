package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.entity.LoanApplicationLogger;
import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import dev.patika.loanapplicationsystem.repository.LoanApplicationLoggerRepository;
import dev.patika.loanapplicationsystem.repository.LoanApplicationResultRepository;
import dev.patika.loanapplicationsystem.util.ClientRequestInfo;
import dev.patika.loanapplicationsystem.service.validators.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static dev.patika.loanapplicationsystem.util.LoanApplicationCalculator.calculateLoanAmount;
import static dev.patika.loanapplicationsystem.util.LoanApplicationCalculator.decideLoanResultMessage;


@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    @Autowired
    private CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final LoanApplicationResultRepository resultRepository;
    @Autowired
    private CustomerMapper customerMapper;
    private final LoanApplicationLoggerRepository loggerRepository;
    private final ClientRequestInfo clientRequestInfo;



    @Transactional
    public LoanApplicationResult applyToLoan(CustomerDTO customerDTO) {
        // check if customer exists
        boolean isExists = customerRepository.existsByIdNumber(customerDTO.getIdNumber());

        LoanApplicationResult loanApplicationResult = new LoanApplicationResult();
        // if customer not exist with given Id Number save the customer
        if (!isExists){
            customerService.saveCustomer(customerDTO);
            loanApplicationResult = resultRepository.save(loanApplicationResult(
                    customerRepository.findCustomerByIdNumber(customerDTO.getIdNumber())));

        }
        else{

            loanApplicationResult = resultRepository.save(loanApplicationResult(
                    customerRepository.findCustomerByIdNumber(customerDTO.getIdNumber())));
        }
        this.saveLoanApplicationToDatabase(loanApplicationResult);
        return loanApplicationResult;

    }

    public LoanApplicationResult loanApplicationResult(Customer customer) {
        LoanApplicationResult result = new LoanApplicationResult();

        int creditScore = customer.getCustomerCreditScore();

        result.setResultMessage(decideLoanResultMessage(creditScore));
        result.setLoanAmount(calculateLoanAmount(creditScore, customer.getSalary()));
        result.setCustomerIdNumber(customer.getIdNumber());

        return result;
    }

    public Set<LoanApplicationResult> getApplicationResult(long idNumber){

        //LoanApplicationResult foundResult =
                //.orElseThrow(() -> new LoanApplicationNotFoundException(String.format(LOAN_APPLICATION_NOT_FOUND, idNumber)));
        return resultRepository.findByCustomerIdNumber(idNumber);
    }

    private void saveLoanApplicationToDatabase(LoanApplicationResult result) {
        LoanApplicationLogger logger = new LoanApplicationLogger();
        logger.setCustomerIdNumber(result.getCustomerIdNumber());
        logger.setLoanAmount(result.getLoanAmount());
        logger.setLoanResultMessage(result.getResultMessage());
        logger.setLoanApplicationDate(LocalDate.now());
        logger.setClientUrl(clientRequestInfo.getClientUrl());
        logger.setClientIpAddress(clientRequestInfo.getClientIpAddress());
        logger.setSessionActivityId(clientRequestInfo.getSessionActivityId());
        this.loggerRepository.save(logger);

    }

    public Page<List<LoanApplicationLogger>> getAllTransactionsByDate(String transactionDate, Integer pageNumber, Integer pageSize, Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        CustomerValidator.validateDate(transactionDate, formatter);
        LocalDate transactionDateResult = LocalDate.parse(transactionDate, formatter);
        if(pageNumber != null && pageSize != null){
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return this.loggerRepository.findAllTransactionByTransactionDate(transactionDateResult, pageable);
    }
}
