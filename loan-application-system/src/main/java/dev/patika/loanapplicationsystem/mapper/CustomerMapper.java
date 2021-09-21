package dev.patika.loanapplicationsystem.mapper;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer mapFromCustomerDTOtoCustomer(CustomerDTO dto);
    CustomerDTO mapFromCustomerToCustomerDTO(Customer customer);
}
