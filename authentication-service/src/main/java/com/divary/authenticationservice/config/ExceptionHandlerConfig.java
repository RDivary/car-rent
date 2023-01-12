package com.divary.authenticationservice.config;

import com.divary.dto.BaseResponse;
import com.divary.exception.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlerConfig extends ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<BaseResponse<Object>> badCredentialsExceptionHandler(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getResponseBody(HttpStatus.UNAUTHORIZED, "Sorry, your password was incorrect."));
    }
}
