package com.divary.carsearchservice.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarSearchRequest {
    private String brand;
    private String model;
    private Integer passenger;
    private String engine;
    private boolean isDelete;
}
