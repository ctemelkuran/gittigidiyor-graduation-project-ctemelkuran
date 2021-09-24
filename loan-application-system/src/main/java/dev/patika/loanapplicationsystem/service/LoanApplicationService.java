package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import dev.patika.loanapplicationsystem.repository.LoanApplicationResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dev.patika.loanapplicationsystem.util.LoanApplicationCalculator.calculateLoanAmount;
import static dev.patika.loanapplicationsystem.util.LoanApplicationCalculator.decideLoanResultMessage;


@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final LoanApplicationResultRepository resultRepository;

    @Transactional
    public LoanApplicationResult applyToLoan(CustomerDTO customerDTO) {
        // check if customer exists
        boolean isExists = customerRepository.existsByIdNumber(customerDTO.getIdNumber());


        // if customer not exist with given Id Number save the customer
        if (!isExists){
            Optional<CustomerDTO> Dto = customerService.saveCustomer(customerDTO);
            Customer customer = customerMapper.mapFromCustomerDTOtoCustomer(Dto.get());
            LoanApplicationResult result = resultRepository.save(loanApplicationResult(customer));
            return result;

        }
        else{
            Customer customer = customerMapper.mapFromCustomerDTOtoCustomer(customerDTO);
            LoanApplicationResult result = resultRepository.save(loanApplicationResult(customer));
            return result;
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


}
