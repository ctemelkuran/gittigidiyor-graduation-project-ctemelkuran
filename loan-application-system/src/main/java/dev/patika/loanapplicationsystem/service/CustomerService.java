package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.CreditScore;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.entity.LoanApplicationLogger;
import dev.patika.loanapplicationsystem.exceptions.CustomerAlreadyExistsException;
import dev.patika.loanapplicationsystem.exceptions.CustomerNotFoundException;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
import dev.patika.loanapplicationsystem.repository.CreditScoreRepository;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import dev.patika.loanapplicationsystem.repository.LoanApplicationLoggerRepository;
import dev.patika.loanapplicationsystem.util.CustomerValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static dev.patika.loanapplicationsystem.util.ErrorMessageConstants.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    private final CreditScoreRepository creditScoreRepository;

    /**
     * Creates a new customer and saves to database
     *
     * @param customerDTO the input DTO object to save a new customer
     * @return the saves customer object {@link Optional<CustomerDTO>}
     */
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

    /**
     * Determines the credit score of the customer
     * according to last digit of the National Id Number.
     *
     * @param idNumber the unique National ID number of customer is required
     * @return credit score of the customer int integer type
     */
    public int creditScoreAccordingToIdNumber(long idNumber) {
        long lastDigit = Math.abs(idNumber % 10);

        CreditScore creditScore = creditScoreRepository
                .findCreditScoreByLastDigitOfIdNumberEquals(Math.toIntExact(lastDigit));
        return creditScore.getCreditScore();
    }

    /**
     * Find all customer that are saved in the database.
     *
     * @return {@link Set<CustomerDTO>} set of customers
     */
    @Transactional(readOnly = true)
    public Set<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::mapFromCustomerToCustomerDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Updates the already existed customer
     *
     * @param customerDTO object is taken as input to update {@link Customer} data.
     * @param id is autogenerated Id of Customer object
     * @return
     */
    @Transactional
    public Optional<CustomerDTO> updateCustomer(CustomerDTO customerDTO, long id) {
        customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with id: "+ id));

        this.validateRequest(customerDTO);

        boolean isExists = customerRepository.existsByIdNumber(customerDTO.getIdNumber());

        if (!isExists) {
            throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND + customerDTO.getIdNumber());
        }

        Customer customer = customerMapper.mapFromCustomerDTOtoCustomer(customerDTO);
        customer.setId(id);
        customer.setCustomerCreditScore(creditScoreAccordingToIdNumber(customer.getIdNumber()));
        CustomerDTO updatedCustomerDTO =
                customerMapper.mapFromCustomerToCustomerDTO(customerRepository.save(customer));
        return Optional.of(updatedCustomerDTO);
    }


    /**
     * Customer national ID number requires valdiation
     * @param customerDTO object is taken to validate ID Number of customer
     */
    private void validateRequest(CustomerDTO customerDTO) {
        CustomerValidatorUtil.validateNationalId(customerDTO.getIdNumber());
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(long id){
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND, id)));

        return customerMapper.mapFromCustomerToCustomerDTO(customer);
    }


    /**
     * Deletes a specific course according to {@literal id} number.
     *
     * @param id primary key of the customer entity
     * @return success message in String type with {@literal id} included
     */
    @Transactional
    public String deleteById(long id) {

        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        customerRepository.delete(foundCustomer);
        return "Customer deleted with id: " + id;
    }


}

