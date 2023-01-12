package com.divary.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ErrorException{
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
