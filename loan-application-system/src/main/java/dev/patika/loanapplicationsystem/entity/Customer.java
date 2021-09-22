package dev.patika.loanapplicationsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Customer extends AbstractBaseEntity{

    private String fullName;
    private long idNumber;
    private double salary;
    private String phoneNumber;
/*    @JsonIgnore
    @OneToOne(mappedBy = "customer")*/
    private int creditScore;
}
