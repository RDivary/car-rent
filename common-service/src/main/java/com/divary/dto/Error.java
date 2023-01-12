package com.divary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Error {
    private String column;
    private String message;
}
