package com.javaguru.onlineshop.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

public class CustomErrorDetails {

    private Date timestamp;
    private Map<String, String> message;
    private HttpStatus httpStatus;
    private String stringMessage;

    public CustomErrorDetails(Date timestamp, Map<String, String> message, HttpStatus httpStatus) {
        this.timestamp = timestamp;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public CustomErrorDetails(Date timestamp, String stringMessage, HttpStatus httpStatus) {
        this.timestamp = timestamp;
        this.stringMessage = stringMessage;
        this.httpStatus = httpStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
