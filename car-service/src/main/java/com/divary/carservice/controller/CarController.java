package com.divary.carservice.controller;

import com.divary.carservice.dto.CarDto;
import com.divary.carservice.dto.request.car.CreateOrUpdateCarRequest;
import com.divary.carservice.service.CarService;
import com.divary.controller.BaseController;
import com.divary.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/car-service/api/v1/car")
public class CarController extends BaseController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Object>> create(@RequestBody @Valid CreateOrUpdateCarRequest form){
        carService.create(form);
        return getResponseCreated(null, "Car created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CarDto>> findById(@PathVariable String id){
        return getResponseOk(carService.findById(id), "Car found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> update(@PathVariable String id, @RequestBody CreateOrUpdateCarRequest form){
        carService.update(id, form);
        return getResponseOk(null, "Car updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> delete(@PathVariable String id){
        carService.delete(id);
        return getResponseOk(null, "Car deleted");
    }

}
