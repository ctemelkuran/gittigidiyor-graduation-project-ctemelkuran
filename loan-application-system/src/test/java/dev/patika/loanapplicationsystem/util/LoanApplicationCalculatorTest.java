package dev.patika.loanapplicationsystem.util;

import dev.patika.loanapplicationsystem.entity.enums.LoanResultMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static dev.patika.loanapplicationsystem.util.LoanApplicationCalculator.calculateLoanAmount;
import static dev.patika.loanapplicationsystem.util.LoanApplicationCalculator.decideLoanResultMessage;
import static org.junit.jupiter.api.Assertions.*;


class LoanApplicationCalculatorTest {

    @ParameterizedTest
    @ValueSource(ints = {100, 200, 400, 450})
    void decideLoanResultMessageTest(int givenCreditScore) {
        // given
        int creditScore = givenCreditScore;
        LoanResultMessage expected = LoanResultMessage.DENIED;

        // when
        LoanResultMessage actual = decideLoanResultMessage(creditScore);

        // then
        assertEquals(expected, actual);

    }

    @ParameterizedTest(name = "1st={0}, 2th={1}")
    @CsvSource(value = {"600, 2000", "750, 6000", "1500, 3000"})
    void calculateLoanAmountTest(int givenCreditScore, double givenSalary) {
        // when
        double actual = calculateLoanAmount(givenCreditScore, givenSalary);

        //then
        assertTrue(actual> 0);
    }
}