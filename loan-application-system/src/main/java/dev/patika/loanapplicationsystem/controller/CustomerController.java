package dev.patika.loanapplicationsystem.controller;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.LoanApplicationLogger;
import dev.patika.loanapplicationsystem.service.CustomerService;
import dev.patika.loanapplicationsystem.service.LoanApplicationService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final LoanApplicationService loanApplicationService;

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        Optional<CustomerDTO> optionalCustomerDTO = customerService.saveCustomer(customerDTO);

        if (optionalCustomerDTO.isPresent()) {
            return new ResponseEntity<>(optionalCustomerDTO.get(), HttpStatus.CREATED);
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

        if (optionalCustomerDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(optionalCustomerDTO.get(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        String customerDeleted = customerService.deleteById(id);

        return new ResponseEntity<>(customerDeleted, HttpStatus.OK);
    }

    @GetMapping("/get-applications-by-date")
    public ResponseEntity<Page<List<LoanApplicationLogger>>> getAllApplicationsByDate(
            @ApiParam(value = "Loan application query", example = "24/09/2021", required = true)
            @RequestParam String applicationDate,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @PageableDefault(page = 0, size = 10) Pageable pageable){
        return new ResponseEntity<>(this.loanApplicationService.getAllApplicationsByDate(applicationDate, pageNumber, pageSize, pageable), HttpStatus.OK);
    }

}
