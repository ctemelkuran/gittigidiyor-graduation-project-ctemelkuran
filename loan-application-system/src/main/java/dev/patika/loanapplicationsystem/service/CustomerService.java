package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.exceptions.CustomerAlreadyExistsException;
import dev.patika.loanapplicationsystem.exceptions.InvalidIdNumberException;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
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

    @Transactional
    public Optional<CustomerDTO> saveCustomer(CustomerDTO customerDTO){

        this.validateRequest(customerDTO);
        boolean isExists = customerRepository.existsByIdNumber(customerDTO.getIdNumber());

        if(isExists){
            throw new CustomerAlreadyExistsException("Customer with National Id: "
                    + customerDTO.getIdNumber() + " is already exists!");
        }
  /*      else if (isOdd(customerDTO.getIdNumber())){
            throw new InvalidIdNumberException(ErrorMessageConstants.ID_NUMBER_IS_ODD);
        }*/

        Customer customer = customerMapper.mapFromCustomerDTOtoCustomer(customerDTO);
        customerRepository.save(customer);
        return Optional.of(customerMapper.mapFromCustomerToCustomerDTO(customer));
    }

    public Set<CustomerDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customer -> customerMapper.mapFromCustomerToCustomerDTO(customer))
                .collect(Collectors.toSet());
    }
/*
    @Transactional
    public Optional<CustomerDTO> updateCustomer(CustomerDTO customerDTO) {
        Customer selectedCustomer = customerRepository.findById(customerDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CUSTOMER_NOT_FOUND, customerDTO.getIdNumber())));

        boolean customerExist = customerRepository.findByCode(request.getCode()).
                isPresent();

        if(customerExist){
            throw new CustomerAlreadyExistsException(
                    String.format(COURSE_FOUND, request.getCode()));
        }

        Customer updatedCustomer = customerRepository.save(selectedCustomer);

        return Optional.of(updatedCustomer);
    }*/

    public boolean isOdd(long i) {
        return (i & 1) != 0;
    }

    private void validateRequest(CustomerDTO customerDTO) {
        CustomerValidatorUtil.validateNationalId(customerDTO.getIdNumber());
    }
}

