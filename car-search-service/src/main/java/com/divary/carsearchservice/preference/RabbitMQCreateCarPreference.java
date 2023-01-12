package com.divary.carsearchservice.preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQCreateCarPreference {
    //Exchange
    @Value("${rabbit.exchange.create-car.original}")
    public String createCarOriginalExchange;
    @Value("${rabbit.exchange.create-car.retry}")
    public String createCarRetryExchange;
    @Value("${rabbit.exchange.create-car.final}")
    public String createCarFinalExchange;

    //Queue
    @Value("${rabbit.queue.create-car.original}")
    public String createCarOriginalQueue;
    @Value("${rabbit.queue.create-car.retry}")
    public String createCarRetryQueue;
    @Value("${rabbit.queue.create-car.final}")
    public String createCarFinalQueue;

    //Routing Key
    @Value("${rabbit.routing-key.create-car.original}")
    public String createCarOriginalRoutingKey;
    @Value("${rabbit.routing-key.create-car.retry}")
    public String createCarRetryRoutingKey;
    @Value("${rabbit.routing-key.create-car.final}")
    public String createCarFinalRoutingKey;

    //TTL
    @Value("${rabbit.ttl.create-car.original}")
    public int createCarTtlOriginal;
    @Value("${rabbit.ttl.create-car.retry}")
    public int createCarTtlRetry;

    //Error Count
    @Value("${rabbit.reject-count.create-car}")
    public int createCarRejectCount;
    @Value("${rabbit.expired-count.create-car}")
    public int createCarExpiredCount;
}