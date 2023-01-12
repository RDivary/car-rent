package com.divary.carsearchservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CarDto {
    private String id;
    private String brand;
    private String model;
    private int passenger;
    private String engine;
    private boolean isDelete;
}
