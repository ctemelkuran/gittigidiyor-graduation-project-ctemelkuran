package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.CreditScore;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.exceptions.CustomerAlreadyExistsException;
import dev.patika.loanapplicationsystem.exceptions.CustomerNotFoundException;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
import dev.patika.loanapplicationsystem.repository.CreditScoreRepository;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import dev.patika.loanapplicationsystem.util.CustomerValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static dev.patika.loanapplicationsystem.util.ErrorMessageConstants.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CreditScoreRepository creditScoreRepository;

    @Transactional
    public Optional<CustomerDTO> saveCustomer(CustomerDTO customerDTO) {

        // Validate input, especially National ID
        this.validateRequest(customerDTO);

        boolean isExists = customerRepository.existsByIdNumber(customerDTO.getIdNumber());

        if (isExists) {
            throw new CustomerAlreadyExistsException("Customer with National Id: "
                    + customerDTO.getIdNumber() + " is already exists!");
        }

        Customer customer = customerMapper.mapFromCustomerDTOtoCustomer(customerDTO);

        // set credit score while saving the customer
        customer.setCustomerCreditScore(creditScoreAccordingToIdNumber(customer.getIdNumber()));

        customerRepository.save(customer);
        return Optional.of(customerMapper.mapFromCustomerToCustomerDTO(customer));
    }

    private int creditScoreAccordingToIdNumber(long idNumber) {
        long lastDigit = Math.abs(idNumber % 10);

        CreditScore creditScore = creditScoreRepository
                .findCreditScoreByLastDigitOfIdNumberEquals(Math.toIntExact(lastDigit));
        return creditScore.getCreditScore();
    }

    @Transactional(readOnly = true)
    public Set<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapFromCustomerToCustomerDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Optional<CustomerDTO> updateCustomer(CustomerDTO customerDTO, long id) {
        customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with id: "+ id));

        this.validateRequest(customerDTO);

        boolean isExists = customerRepository.existsByIdNumber(customerDTO.getIdNumber());

        if (isExists) {
            throw new CustomerAlreadyExistsException("Customer with National Id: "
                    + customerDTO.getIdNumber() + " is already exists!");
        }

        Customer customer = customerMapper.mapFromCustomerDTOtoCustomer(customerDTO);
        customer.setId(id);
        customer.setCustomerCreditScore(creditScoreAccordingToIdNumber(customer.getIdNumber()));
        CustomerDTO updatedCustomerDTO =
                customerMapper.mapFromCustomerToCustomerDTO(customerRepository.save(customer));
        return Optional.of(updatedCustomerDTO);
    }


    private void validateRequest(CustomerDTO customerDTO) {
        CustomerValidatorUtil.validateNationalId(customerDTO.getIdNumber());
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(long id){
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND, id)));

        return customerMapper.mapFromCustomerToCustomerDTO(customer);
    }


    @Transactional
    public String deleteById(long id) {

        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        customerRepository.delete(foundCustomer);
        return "Customer deleted with id: " + id;
    }
}

