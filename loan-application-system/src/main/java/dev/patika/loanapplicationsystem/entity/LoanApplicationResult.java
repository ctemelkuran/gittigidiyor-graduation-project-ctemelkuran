package dev.patika.loanapplicationsystem.entity;

import dev.patika.loanapplicationsystem.entity.enums.LoanResultMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationResult extends AbstractBaseEntity{
    private long customerIdNumber;
    @Enumerated(EnumType.STRING)
    private LoanResultMessage resultMessage;
    private double loanAmount;
}
