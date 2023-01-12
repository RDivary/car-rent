package com.divary.carservice.config;

import com.divary.carservice.preference.RabbitMQPreference;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    private final RabbitMQPreference rabbitMQPreference;

    public RabbitMqConfig(RabbitMQPreference rabbitMQPreference) {
        this.rabbitMQPreference = rabbitMQPreference;
    }

    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(rabbitMQPreference.rabbitmqHost);
        cachingConnectionFactory.setPort(rabbitMQPreference.rabbitmqPort);
        cachingConnectionFactory.setUsername(rabbitMQPreference.rabbitmqUsername);
        cachingConnectionFactory.setPassword(rabbitMQPreference.rabbitmqPassword);

        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }
}
