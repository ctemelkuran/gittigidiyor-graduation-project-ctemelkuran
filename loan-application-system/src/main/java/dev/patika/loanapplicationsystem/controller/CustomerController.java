package dev.patika.loanapplicationsystem.controller;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        Optional<CustomerDTO> optionalCustomerDTO = customerService.saveCustomer(customerDTO);

        if (optionalCustomerDTO.isPresent()) {
            return new ResponseEntity<>(optionalCustomerDTO.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<Set<CustomerDTO>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable long id,
                                                      @RequestBody @Valid CustomerDTO customerDTO){
        Optional<CustomerDTO> optionalCustomerDTO = customerService.updateCustomer(customerDTO, id);

        if (!optionalCustomerDTO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(optionalCustomerDTO.get(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        customerService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
