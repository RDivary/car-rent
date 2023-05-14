package com.divary.carservice.dto;

import com.divary.carservice.enums.Engine;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarDto {
    private String id;
    private String brand;
    private String model;
    private int passenger;
    private Engine engine;
    private boolean isDelete;
}
