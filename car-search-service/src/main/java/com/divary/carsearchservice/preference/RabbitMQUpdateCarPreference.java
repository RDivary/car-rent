package com.divary.carsearchservice.preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQUpdateCarPreference {
    //Exchange
    @Value("${rabbit.exchange.update-car.original}")
    public String updateCarOriginalExchange;
    @Value("${rabbit.exchange.update-car.retry}")
    public String updateCarRetryExchange;
    @Value("${rabbit.exchange.update-car.final}")
    public String updateCarFinalExchange;

    //Queue
    @Value("${rabbit.queue.update-car.original}")
    public String updateCarOriginalQueue;
    @Value("${rabbit.queue.update-car.retry}")
    public String updateCarRetryQueue;
    @Value("${rabbit.queue.update-car.final}")
    public String updateCarFinalQueue;

    //Routing Key
    @Value("${rabbit.routing-key.update-car.original}")
    public String updateCarOriginalRoutingKey;
    @Value("${rabbit.routing-key.update-car.retry}")
    public String updateCarRetryRoutingKey;
    @Value("${rabbit.routing-key.update-car.final}")
    public String updateCarFinalRoutingKey;

    //TTL
    @Value("${rabbit.ttl.update-car.original}")
    public int updateCarTtlOriginal;
    @Value("${rabbit.ttl.update-car.retry}")
    public int updateCarTtlRetry;

    //Error Count
    @Value("${rabbit.reject-count.update-car}")
    public int updateCarRejectCount;
    @Value("${rabbit.expired-count.update-car}")
    public int updateCarExpiredCount;
}