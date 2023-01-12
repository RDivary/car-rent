package com.divary.carservice.config;

import com.divary.carservice.dto.ErrorDto;
import com.divary.dto.BaseResponse;
import com.divary.exception.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerConfig extends ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<BaseResponse<List<ErrorDto>>> exceptionHandler(MethodArgumentNotValidException e) {
        List<ErrorDto> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            ErrorDto error = ErrorDto.builder()
                    .fieldName(fieldName)
                    .errorMessage(errorMessage)
                    .build();
            errors.add(error);
        });
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(this.getResponseBody(httpStatus, "Validation error", errors));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public <T>ResponseEntity<BaseResponse<T>> exceptionHandler(SQLIntegrityConstraintViolationException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(getResponseBody(httpStatus, e.getMessage()));
    }
}
