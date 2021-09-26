package dev.patika.loanapplicationsystem.service.validators;

import dev.patika.loanapplicationsystem.exceptions.InvalidIdNumberException;
import dev.patika.loanapplicationsystem.exceptions.LoanApplicationDateParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static dev.patika.loanapplicationsystem.util.ErrorMessageConstants.INVALID_DATE_FORMAT;
import static dev.patika.loanapplicationsystem.util.ErrorMessageConstants.INVALID_ID_NUMBER;

public class CustomerValidator {

    /**
     * Validates the National ID Number
     * Sum of first 10 digits equals to 11th number of ID Number.
     * @param idNumber
     */
    public static void validateNationalId(long idNumber) {

        int sumOfNumber = 0;
        for (int i = 0; i < 10; i++) {
            sumOfNumber += valueAtIndexOfLong(idNumber, i);
        }
        if (valueAtIndexOfLong(idNumber, 10) != sumOfNumber % 10) {
            throw new InvalidIdNumberException(INVALID_ID_NUMBER);
        }
    }

    public static int valueAtIndexOfLong(long number, int index){
        int digitValue = Character.getNumericValue(Long.toString(number).charAt(index));
        return digitValue;
    }

    public static void validateDate(String date, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new LoanApplicationDateParseException(INVALID_DATE_FORMAT + date);
        }

    }
}
