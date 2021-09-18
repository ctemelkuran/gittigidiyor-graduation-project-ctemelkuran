package dev.patika.loanapplicationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends AbstractBaseEntity{

    private String fullName;
    private long idNumber;
    private double salary;
    private String phoneNumber;
}
