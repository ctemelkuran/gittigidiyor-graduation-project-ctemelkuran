package dev.patika.loanapplicationsystem.exceptions;

public class InvalidIdNumberException extends RuntimeException{
    public InvalidIdNumberException(String message) {
        super(message);
    }
}
