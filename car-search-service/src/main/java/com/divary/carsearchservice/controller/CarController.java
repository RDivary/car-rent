package com.divary.carsearchservice.controller;

import com.divary.carsearchservice.dto.CarDto;
import com.divary.carsearchservice.dto.request.CarSearchRequest;
import com.divary.carsearchservice.service.CarService;
import com.divary.controller.BaseController;
import com.divary.dto.BaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/car-search-service/api/v1/car")
public class CarController extends BaseController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<List<CarDto>>> search(
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(value = "model", required = false, defaultValue = "") String model,
            @RequestParam(value = "passenger", required = false) Integer passenger,
            @RequestParam(value = "engine", required = false) String engine,
            @RequestParam(value = "isDelete", required = false, defaultValue = "false") boolean isDelete,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "orderBy", required = false, defaultValue = "ASC") String orderBy,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "100") int size
    ){
        Pageable pageable = getPageable(sortBy, orderBy, page, size);
        CarSearchRequest form = CarSearchRequest.builder()
                .brand(brand)
                .model(model)
                .passenger(passenger)
                .engine(engine)
                .isDelete(isDelete)
                .build();


        return getResponseOk(carService.search(form, pageable), "Car Found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> findById(@PathVariable String id){
        return getResponseOk(carService.findById(id), "Car Found");
    }
}
