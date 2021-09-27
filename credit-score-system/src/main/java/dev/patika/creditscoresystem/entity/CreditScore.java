package dev.patika.creditscoresystem.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CreditScore {
    @Id
    private String id;
    private int lastDigitOfIdNumber;
    private int creditScore;

    public CreditScore(int lastDigitOfIdNumber, int creditScore) {
        this.lastDigitOfIdNumber = lastDigitOfIdNumber;
        this.creditScore = creditScore;
    }
}
