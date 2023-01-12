package com.divary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private T data;
    private int code;
    private String status;
    private String message;
    private final LocalDateTime time = LocalDateTime.now();

}
