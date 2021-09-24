package dev.patika.loanapplicationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CreditScore extends AbstractBaseEntity{
    private int creditScore;
    private int lastDigitOfIdNumber;
}
