package dev.patika.creditscoresystem.repository;

import dev.patika.creditscoresystem.entity.CreditScore;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CreditScoreRepository extends MongoRepository<CreditScore, String> {

    CreditScore findCreditScoreByLastDigitOfIdNumberEquals(int lastDigit);


}
