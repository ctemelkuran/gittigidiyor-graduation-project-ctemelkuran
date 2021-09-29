package dev.patika.loanapplicationsystem.exceptions;

import dev.patika.loanapplicationsystem.entity.ExceptionLogger;
import dev.patika.loanapplicationsystem.service.ExceptionLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ExceptionLoggerService loggerService;

    @Autowired
    public GlobalExceptionHandler(ExceptionLoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @ExceptionHandler({LoanApplicationDateParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(LoanApplicationDateParseException exc){
        ErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidIdNumberException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(InvalidIdNumberException exc){

        ErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        loggerService.saveException(response, exc.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomerAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(CustomerAlreadyExistsException exc){

        ErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        loggerService.saveException(response, exc.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(CustomerNotFoundException exc){
        ErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        loggerService.saveException(response, exc.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({LoanApplicationNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(LoanApplicationNotFoundException exc){
        ErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        loggerService.saveException(response, exc.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse prepareErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(System.currentTimeMillis());
        return errorResponse;
    }
}
