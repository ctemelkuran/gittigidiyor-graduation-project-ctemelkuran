package dev.patika.loanapplicationsystem.util;

import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializerRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(InitializerRunner.class);

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public void run(String... args) {
        customerRepository.deleteAll();

        customerRepository.save(Customer.builder().fullName("Ali Veli").idNumber(63743325700L).salary(3000).customerCreditScore(2000).phoneNumber("5392582569").build());
        customerRepository.save(Customer.builder().fullName("Quentin Tarantino").idNumber(82661129498L).salary(4000).customerCreditScore(900).phoneNumber("5312582569").build());
        customerRepository.save(Customer.builder().fullName("Arthur Dent").idNumber(53436704844L).salary(4000).customerCreditScore(1000).phoneNumber("5452582569").build());
        customerRepository.save(Customer.builder().fullName("Morty Sanchez").idNumber(12174025338L).salary(5000).customerCreditScore(900).phoneNumber("5869282569").build());

        // customerRepository.findAll().forEach(c -> logger.info("{}", c));

    }
}
