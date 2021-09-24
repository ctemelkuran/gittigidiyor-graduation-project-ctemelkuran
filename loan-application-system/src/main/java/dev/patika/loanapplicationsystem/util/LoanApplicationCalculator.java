package dev.patika.loanapplicationsystem.util;

import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import dev.patika.loanapplicationsystem.entity.enums.LoanResultMessage;

public class LoanApplicationCalculator {

    public static final double CREDIT_LIMIT_FACTOR = 4;



    public static LoanResultMessage decideLoanResultMessage(int creditScore) {

        if (creditScore<500)
            return LoanResultMessage.DENIED;
        else
            return LoanResultMessage.APPROVED;
   /*     int creditScore = customer.getCustomerCreditScore();
        LoanApplicationResult result = null;

        if (creditScore < 500){
            result.setCustomerIdNumber(customer.getIdNumber());
            result.setResultMessage(LoanResultMessage.DENIED);
            result.setLoanAmount(0);
        }
        else {
            result.setResultMessage(LoanResultMessage.APPROVED);
            result.setLoanAmount(1000);
        }
        return result;*/

    }

    public static double calculateLoanAmount(int creditScore, double salary) {
        if (creditScore < 500)
            return 0;
        else if (creditScore >= 500 && creditScore < 1000)
            if (salary < 5000)
                return 10000;
            else
                return 20000;
        else
            return salary*CREDIT_LIMIT_FACTOR;
    }
}
