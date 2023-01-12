package com.divary.carservice.dto;

import com.divary.carservice.enums.Engine;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarDto {
    private String id;
    private String brand;
    private String model;
    private int passenger;
    private Engine engine;
    private boolean isDelete;
}
