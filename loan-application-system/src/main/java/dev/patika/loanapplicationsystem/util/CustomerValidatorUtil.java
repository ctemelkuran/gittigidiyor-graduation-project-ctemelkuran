package dev.patika.loanapplicationsystem.util;

import dev.patika.loanapplicationsystem.exceptions.InvalidIdNumberException;

import static dev.patika.loanapplicationsystem.util.ErrorMessageConstants.INVALID_ID_NUMBER;

public class CustomerValidatorUtil {

    public static void validateNationalId(long idNumber){

        int sumOfNumber=0;
        for (int i = 0; i < 10; i++){
            sumOfNumber += Character.getNumericValue(Long.toString(idNumber).charAt(i));
        }
        if (Character.getNumericValue(Long.toString(idNumber).charAt(10)) != sumOfNumber % 10){
            throw new InvalidIdNumberException(INVALID_ID_NUMBER);
        }
    }

}
