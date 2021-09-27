package dev.patika.creditscoresystem;

import dev.patika.creditscoresystem.entity.CreditScore;
import dev.patika.creditscoresystem.repository.CreditScoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class CreditScoreSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditScoreSystemApplication.class, args);
    }

    /**
     * Initiliaze an example data set in gittigidiyor-mongodb
     *
     * @param repository to access the database with MongoRepository
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(CreditScoreRepository repository){
        return args -> {
            repository.deleteAll();

            CreditScore creditScore0 = new CreditScore(0,2000);
            CreditScore creditScore2 = new CreditScore(2,550);
            CreditScore creditScore4 = new CreditScore(4,1000);
            CreditScore creditScore6 = new CreditScore(6,400);
            CreditScore creditScore8 = new CreditScore(8,900);

            repository.save(creditScore0);
            repository.save(creditScore2);
            repository.save(creditScore4);
            repository.save(creditScore6);
            repository.save(creditScore8);

        };

    }
}
