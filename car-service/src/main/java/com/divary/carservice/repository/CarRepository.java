package com.divary.carservice.repository;

import com.divary.carservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, String> {
    List<Car> findByBrandId(String idBrand);
}
