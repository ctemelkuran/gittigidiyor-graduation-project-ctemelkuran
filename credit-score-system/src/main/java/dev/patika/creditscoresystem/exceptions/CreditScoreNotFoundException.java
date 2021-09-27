package dev.patika.creditscoresystem.exceptions;

public class CreditScoreNotFoundException extends RuntimeException{
    public CreditScoreNotFoundException() {
    }

    public CreditScoreNotFoundException(String message) {
        super(message);
    }
}
