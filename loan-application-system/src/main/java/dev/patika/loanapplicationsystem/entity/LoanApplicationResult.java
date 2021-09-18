package dev.patika.loanapplicationsystem.entity;

import dev.patika.loanapplicationsystem.entity.enums.LoanResultMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationResult extends AbstractBaseEntity{
    private LoanResultMessage resultMessage;
    private double loanAmount;
}
