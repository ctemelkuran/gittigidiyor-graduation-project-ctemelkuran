package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import dev.patika.loanapplicationsystem.exceptions.LoanApplicationNotFoundException;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import dev.patika.loanapplicationsystem.repository.LoanApplicationResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static dev.patika.loanapplicationsystem.util.ErrorMessageConstants.LOAN_APPLICATION_NOT_FOUND;
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

    @Transactional
    public LoanApplicationResult applyToLoan(CustomerDTO customerDTO) {
        // check if customer exists
        boolean isExists = customerRepository.existsByIdNumber(customerDTO.getIdNumber());


        // if customer not exist with given Id Number save the customer
        if (!isExists){
            customerService.saveCustomer(customerDTO);

            return resultRepository.save(loanApplicationResult(
                    customerRepository.findCustomerByIdNumber(customerDTO.getIdNumber())));

        }
        else{

            return resultRepository.save(loanApplicationResult(
                    customerRepository.findCustomerByIdNumber(customerDTO.getIdNumber())));
        }
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

}
