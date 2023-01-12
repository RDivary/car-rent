package com.divary.carsearchservice.service;

import com.divary.carsearchservice.dto.CarDto;
import com.divary.carsearchservice.dto.request.CarSearchRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarService {
    void create(CarDto form);
    List<CarDto> search(CarSearchRequest form, Pageable pageable);
    CarDto findById(String id);
    void update(String id, CarDto form);
    void delete(String id);
}
