package com.divary.carsearchservice.event.ampq;

import com.divary.carsearchservice.dto.CarDto;
import com.divary.carsearchservice.preference.RabbitMQCreateCarPreference;
import com.divary.carsearchservice.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CarListener extends BaseListener{
    private final CarService carService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;
    private final RabbitMQCreateCarPreference createCarPreference;

    public CarListener(CarService carService, RabbitTemplate rabbitTemplate, ObjectMapper mapper, RabbitMQCreateCarPreference createCarPreference) {
        this.carService = carService;
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
        this.createCarPreference = createCarPreference;
    }

    @RabbitListener(queues = "${rabbit.queue.create-car.original}")
    public void consumeCreateCar(Message message){
        try{
            if (isStillAvailableConsume(message, createCarPreference.createCarRetryExchange, createCarPreference.createCarRetryRoutingKey, createCarPreference.createCarRejectCount, createCarPreference.createCarExpiredCount)){
                CarDto car = mapper.readValue(message.getBody(), CarDto.class);
                carService.create(car);
            } else {
                rabbitTemplate.convertAndSend(createCarPreference.createCarFinalExchange, createCarPreference.createCarFinalRoutingKey, message);
                log.info("send Create Car to parking lot = {}", message);
            }
        } catch (Exception err){
            log.error("Create Car error: {}", ExceptionUtils.getStackTrace(err));
            throw new AmqpRejectAndDontRequeueException("Create Car error");
        }
    }

    @RabbitListener(queues = "${rabbit.queue.update-car.original}")
    public void consumeUpdateCar(Message message){
        try{
            if (isStillAvailableConsume(message, createCarPreference.createCarRetryExchange, createCarPreference.createCarRetryRoutingKey, createCarPreference.createCarRejectCount, createCarPreference.createCarExpiredCount)){
                CarDto car = mapper.readValue(message.getBody(), CarDto.class);
                carService.update(car.getId(), car);
            } else {
                rabbitTemplate.convertAndSend(createCarPreference.createCarFinalExchange, createCarPreference.createCarFinalRoutingKey, message);
                log.info("send Update Car to parking lot = {}", message);
            }
        } catch (Exception err){
            log.error("Update Car error: {}", ExceptionUtils.getStackTrace(err));
            throw new AmqpRejectAndDontRequeueException("Update Car error");
        }
    }

    @RabbitListener(queues = "${rabbit.queue.delete-car.original}")
    public void consumeDeleteCar(Message message){
        try{
            if (isStillAvailableConsume(message, createCarPreference.createCarRetryExchange, createCarPreference.createCarRetryRoutingKey, createCarPreference.createCarRejectCount, createCarPreference.createCarExpiredCount)){
                String id = mapper.readValue(message.getBody(), String.class);
                carService.delete(id);
            } else {
                rabbitTemplate.convertAndSend(createCarPreference.createCarFinalExchange, createCarPreference.createCarFinalRoutingKey, message);
                log.info("send Delete Car to parking lot = {}", message);
            }
        } catch (Exception err){
            log.error("Delete Car error: {}", ExceptionUtils.getStackTrace(err));
            throw new AmqpRejectAndDontRequeueException("Delete Car error");
        }
    }
}
