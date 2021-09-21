package dev.patika.loanapplicationsystem.repository;

import dev.patika.loanapplicationsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByIdNumber(long idNumber);
}

