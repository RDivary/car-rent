package com.divary.carservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDto {
    private String fieldName;
    private String errorMessage;
}
