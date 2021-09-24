package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.exceptions.CustomerAlreadyExistsException;
import dev.patika.loanapplicationsystem.exceptions.CustomerNotFoundException;
import dev.patika.loanapplicationsystem.exceptions.InvalidIdNumberException;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
import dev.patika.loanapplicationsystem.repository.CreditScoreRepository;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import dev.patika.loanapplicationsystem.util.CustomerValidatorUtil;
import dev.patika.loanapplicationsystem.util.ErrorMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
        customer.setCreditScore(creditScoreAccordingToIdNumber(customer.getIdNumber()));

        customerRepository.save(customer);
        return Optional.of(customerMapper.mapFromCustomerToCustomerDTO(customer));
    }

    private int creditScoreAccordingToIdNumber(long idNumber) {
        long lastDigit = idNumber % 10;
        return creditScoreRepository.findCreditScoreByLastDigitOfIdNumber(lastDigit);
    }

    @Transactional(readOnly = true)
    public Set<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customer -> customerMapper.mapFromCustomerToCustomerDTO(customer))
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
        customer.setCreditScore(creditScoreAccordingToIdNumber(customer.getIdNumber()));
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


    @Transactional(readOnly = true)
    public String deleteById(long id) {

        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        customerRepository.delete(foundCustomer);
        return "Customer deleted with id: " + id;
    }
}

