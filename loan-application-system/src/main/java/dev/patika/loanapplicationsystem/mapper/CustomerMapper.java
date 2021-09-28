package dev.patika.loanapplicationsystem.mapper;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.service.CustomerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

    @Autowired
    protected CustomerService customerService;

    @Mapping(target = "customerCreditScore", expression = "java(customerService.getCreditScore(dto.getIdNumber()))")
    public abstract Customer mapFromCustomerDTOtoCustomer(CustomerDTO dto);
    public abstract CustomerDTO mapFromCustomerToCustomerDTO(Customer customer);
}
