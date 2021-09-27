package dev.patika.loanapplicationsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CreditScore{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private int creditScore;
    private int lastDigitOfIdNumber;
}
