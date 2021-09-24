package dev.patika.loanapplicationsystem.exceptions;

public class LoanApplicationNotFoundException extends RuntimeException{
    public LoanApplicationNotFoundException(String message) {
        super(message);
    }
}
