package com.divary.carservice.preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQCarPreference {
    @Value("${rabbit.exchange.create-car}")
    public String createCarExchange;

    @Value("${rabbit.exchange.update-car}")
    public String updateCarExchange;

    @Value("${rabbit.exchange.delete-car}")
    public String deleteCarExchange;
}
