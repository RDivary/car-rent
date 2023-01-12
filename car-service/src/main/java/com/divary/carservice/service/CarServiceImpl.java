package com.divary.carservice.service;

import com.divary.carservice.dto.CarDto;
import com.divary.carservice.dto.request.car.CreateOrUpdateCarRequest;
import com.divary.carservice.model.Car;
import com.divary.carservice.preference.RabbitMQCarPreference;
import com.divary.carservice.repository.CarRepository;
import com.divary.exception.BadRequestException;
import com.divary.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQCarPreference rabbitMQCarPreference;

    public CarServiceImpl(CarRepository carRepository, RabbitTemplate rabbitTemplate, RabbitMQCarPreference rabbitMQCarPreference) {
        this.carRepository = carRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQCarPreference = rabbitMQCarPreference;
    }

    @Override
    public void create(CreateOrUpdateCarRequest form) {
        Car car = Car.builder()
                .brand(form.getBrand())
                .model(form.getModel())
                .engine(form.getEngine())
                .passenger(form.getPassenger())
                .isDelete(false)
                .build();
        carRepository.save(car);
        rabbitTemplate.convertAndSend(rabbitMQCarPreference.createCarExchange, "", carToCarDto(car));
        log.info("Create Car success, " + car);
    }

    @Override
    public CarDto findById(String id) {
        Car car = findById(id, "");
        return carToCarDto(car);
    }

    @Override
    public void update(String id, CreateOrUpdateCarRequest form) {
        Car car = findById(id, "");
        car.setBrand(form.getBrand());
        car.setModel(form.getModel());
        car.setPassenger(form.getPassenger());
        car.setEngine(form.getEngine());

        carRepository.save(car);
        rabbitTemplate.convertAndSend(rabbitMQCarPreference.updateCarExchange, "", carToCarDto(car));
        log.info("Update Car success, " + car);
    }

    @Override
    public void delete(String id) {
        Car car = findById(id, "");
        if (car.isDelete()){
            throw new BadRequestException("Cannot delete car, Because car is deleted");
        }
        car.setDelete(true);

        carRepository.save(car);
        rabbitTemplate.convertAndSend(rabbitMQCarPreference.deleteCarExchange, "", id);
        log.info("Delete Car success, " + id);
    }

    private Car findById(String id, String s) {
        //TODO
        return carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car Not Found"));
    }

    private CarDto carToCarDto(Car car){
        return CarDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .passenger(car.getPassenger())
                .engine(car.getEngine())
                .isDelete(car.isDelete())
                .build();
    }
}
