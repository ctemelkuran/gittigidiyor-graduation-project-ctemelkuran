package dev.patika.loanapplicationsystem.entity;

import dev.patika.loanapplicationsystem.entity.enums.LoanResultMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoanApplicationLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerIdNumber;
    @Enumerated(EnumType.STRING)
    private LoanResultMessage loanResultMessage;
    private double loanAmount;
    private LocalDate loanApplicationDate;
    private String clientIpAddress;
    private String clientUrl;
    private String sessionActivityId;


}
