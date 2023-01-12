package com.divary.exception;

import com.divary.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ErrorException.class})
    public <T>ResponseEntity<BaseResponse<T>> exceptionHandler(ErrorException e){
        return ResponseEntity.status(e.getHttpStatus()).body(getResponseBody(e.getHttpStatus(), e.getMessage()));
    }

    protected <T>BaseResponse<T> getResponseBody(HttpStatus httpStatus, String message) {
        return getResponseBody(httpStatus, message, null);
    }

    protected <T>BaseResponse<T> getResponseBody(HttpStatus httpStatus, String message, T data) {
        return BaseResponse.<T>builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .data(data)
                .build();
    }
}
