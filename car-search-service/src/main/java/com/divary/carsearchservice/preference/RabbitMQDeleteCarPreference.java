package com.divary.carsearchservice.preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQDeleteCarPreference {
    //Exchange
    @Value("${rabbit.exchange.delete-car.original}")
    public String deleteCarOriginalExchange;
    @Value("${rabbit.exchange.delete-car.retry}")
    public String deleteCarRetryExchange;
    @Value("${rabbit.exchange.delete-car.final}")
    public String deleteCarFinalExchange;

    //Queue
    @Value("${rabbit.queue.delete-car.original}")
    public String deleteCarOriginalQueue;
    @Value("${rabbit.queue.delete-car.retry}")
    public String deleteCarRetryQueue;
    @Value("${rabbit.queue.delete-car.final}")
    public String deleteCarFinalQueue;

    //Routing Key
    @Value("${rabbit.routing-key.delete-car.original}")
    public String deleteCarOriginalRoutingKey;
    @Value("${rabbit.routing-key.delete-car.retry}")
    public String deleteCarRetryRoutingKey;
    @Value("${rabbit.routing-key.delete-car.final}")
    public String deleteCarFinalRoutingKey;

    //TTL
    @Value("${rabbit.ttl.delete-car.original}")
    public int deleteCarTtlOriginal;
    @Value("${rabbit.ttl.delete-car.retry}")
    public int deleteCarTtlRetry;

    //Error Count
    @Value("${rabbit.reject-count.delete-car}")
    public int deleteCarRejectCount;
    @Value("${rabbit.expired-count.delete-car}")
    public int deleteCarExpiredCount;
}