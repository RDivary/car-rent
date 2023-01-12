package com.divary.carsearchservice.preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQPreference {
    @Value("${spring.rabbitmq.host}")
    public String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    public int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    public String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    public String rabbitmqPassword;
}
