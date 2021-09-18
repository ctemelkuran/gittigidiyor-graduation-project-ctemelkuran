package dev.patika.loanapplicationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditScore extends AbstractBaseEntity{
    private int creditScore;
    @OneToOne
    Customer customer;
}
