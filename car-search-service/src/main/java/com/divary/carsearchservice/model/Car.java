package com.divary.carsearchservice.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "car_rent.car_search_service.car")
public class Car {
    @Id
    private String id;
    private String brand;
    private String model;
    private int passenger;
    private String engine;
    private boolean isDelete;
}
