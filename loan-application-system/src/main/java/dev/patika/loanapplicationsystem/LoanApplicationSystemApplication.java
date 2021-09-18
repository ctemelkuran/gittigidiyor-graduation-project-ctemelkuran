package dev.patika.loanapplicationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class LoanApplicationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanApplicationSystemApplication.class, args);
    }

}
