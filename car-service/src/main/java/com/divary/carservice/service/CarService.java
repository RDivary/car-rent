package com.divary.carservice.service;

import com.divary.carservice.dto.CarDto;
import com.divary.carservice.dto.request.car.CreateOrUpdateCarRequest;

public interface CarService {
    void create(CreateOrUpdateCarRequest form);
    CarDto findById(String id);
    void update(String id, CreateOrUpdateCarRequest form);
    void delete(String id);
}
