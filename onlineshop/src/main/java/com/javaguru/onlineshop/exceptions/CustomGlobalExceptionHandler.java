package com.javaguru.onlineshop.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Map<String, String> errors;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        CustomErrorDetails details = new CustomErrorDetails(new Date(), errors, status);
        return new ResponseEntity<>(details, status);
    }

    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<Object> handleExceptions(Exception ex){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomErrorDetails details = new CustomErrorDetails(new Date(), errors, badRequest);
        return new ResponseEntity<>(details, badRequest);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CustomErrorDetails details = new CustomErrorDetails(new Date(), ex.getMessage(), notFound);
        return new ResponseEntity<>(details, notFound);
    }

}
