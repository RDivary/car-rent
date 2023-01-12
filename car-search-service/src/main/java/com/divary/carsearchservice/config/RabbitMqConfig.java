package com.divary.carsearchservice.config;

import com.divary.carsearchservice.preference.RabbitMQCreateCarPreference;
import com.divary.carsearchservice.preference.RabbitMQDeleteCarPreference;
import com.divary.carsearchservice.preference.RabbitMQPreference;
import com.divary.carsearchservice.preference.RabbitMQUpdateCarPreference;
import org.springframework.amqp.core.*;
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
    private final RabbitMQCreateCarPreference createCarPreference;
    private final RabbitMQUpdateCarPreference updateCarPreference;
    private final RabbitMQDeleteCarPreference deleteCarPreference;

    private static final String DEATH_LATTER_EXCHANGE = "x-dead-letter-exchange";
    private static final String DEATH_LATTER_ROUTING_KEY = "x-dead-letter-routing-key";
    private static final String MESSAGE_TTL = "x-message-ttl";

    public RabbitMqConfig(RabbitMQPreference rabbitMQPreference, RabbitMQCreateCarPreference createCarPreference, RabbitMQUpdateCarPreference updateCarPreference, RabbitMQDeleteCarPreference deleteCarPreference) {
        this.rabbitMQPreference = rabbitMQPreference;
        this.createCarPreference = createCarPreference;
        this.updateCarPreference = updateCarPreference;
        this.deleteCarPreference = deleteCarPreference;
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
    
    /*
    Create Car
     */
    @Bean
    FanoutExchange createCarOriginalExchange(){
        return new FanoutExchange(createCarPreference.createCarOriginalExchange);
    }

    @Bean
    DirectExchange createCarRetryExchange(){
        return new DirectExchange(createCarPreference.createCarRetryExchange);
    }

    @Bean
    DirectExchange createCarFinalExchange(){
        return new DirectExchange(createCarPreference.createCarFinalExchange);
    }

    @Bean
    Queue createCarOriginalQueue(){
        return QueueBuilder.durable(createCarPreference.createCarOriginalQueue)
                .withArgument(DEATH_LATTER_EXCHANGE, createCarPreference.createCarRetryExchange)
                .withArgument(DEATH_LATTER_ROUTING_KEY, createCarPreference.createCarRetryRoutingKey)
                .withArgument(MESSAGE_TTL, createCarPreference.createCarTtlOriginal).build();
    }

    @Bean
    Queue createCarRetryQueue(){
        return QueueBuilder.durable(createCarPreference.createCarRetryQueue)
                .withArgument(DEATH_LATTER_EXCHANGE, createCarPreference.createCarRetryExchange)
                .withArgument(DEATH_LATTER_ROUTING_KEY, createCarPreference.createCarOriginalRoutingKey)
                .withArgument(MESSAGE_TTL, createCarPreference.createCarTtlRetry).build();
    }

    @Bean
    Queue createCarFinalQueue(){
        return QueueBuilder.durable(createCarPreference.createCarFinalQueue).build();
    }

    @Bean
    Binding createCarOriginalToFanoutBinding(){
        return BindingBuilder.bind(createCarOriginalQueue()).to(createCarOriginalExchange());
    }

    @Bean
    Binding createCarOriginalBinding(){
        return BindingBuilder.bind(createCarOriginalQueue()).to(createCarRetryExchange()).with(createCarPreference.createCarOriginalRoutingKey);
    }

    @Bean
    Binding createCarRetryBinding(){
        return BindingBuilder.bind(createCarRetryQueue()).to(createCarRetryExchange()).with(createCarPreference.createCarRetryRoutingKey);
    }

    @Bean
    Binding createCarFinalBinding(){
        return BindingBuilder.bind(createCarFinalQueue()).to(createCarFinalExchange()).with(createCarPreference.createCarFinalRoutingKey);
    }

    /*
    Update Car
     */
    @Bean
    FanoutExchange updateCarOriginalExchange(){
        return new FanoutExchange(updateCarPreference.updateCarOriginalExchange);
    }

    @Bean
    DirectExchange updateCarRetryExchange(){
        return new DirectExchange(updateCarPreference.updateCarRetryExchange);
    }

    @Bean
    DirectExchange updateCarFinalExchange(){
        return new DirectExchange(updateCarPreference.updateCarFinalExchange);
    }

    @Bean
    Queue updateCarOriginalQueue(){
        return QueueBuilder.durable(updateCarPreference.updateCarOriginalQueue)
                .withArgument(DEATH_LATTER_EXCHANGE, updateCarPreference.updateCarRetryExchange)
                .withArgument(DEATH_LATTER_ROUTING_KEY, updateCarPreference.updateCarRetryRoutingKey)
                .withArgument(MESSAGE_TTL, updateCarPreference.updateCarTtlOriginal).build();
    }

    @Bean
    Queue updateCarRetryQueue(){
        return QueueBuilder.durable(updateCarPreference.updateCarRetryQueue)
                .withArgument(DEATH_LATTER_EXCHANGE, updateCarPreference.updateCarRetryExchange)
                .withArgument(DEATH_LATTER_ROUTING_KEY, updateCarPreference.updateCarOriginalRoutingKey)
                .withArgument(MESSAGE_TTL, updateCarPreference.updateCarTtlRetry).build();
    }

    @Bean
    Queue updateCarFinalQueue(){
        return QueueBuilder.durable(updateCarPreference.updateCarFinalQueue).build();
    }

    @Bean
    Binding updateCarOriginalToFanoutBinding(){
        return BindingBuilder.bind(updateCarOriginalQueue()).to(updateCarOriginalExchange());
    }

    @Bean
    Binding updateCarOriginalBinding(){
        return BindingBuilder.bind(updateCarOriginalQueue()).to(updateCarRetryExchange()).with(updateCarPreference.updateCarOriginalRoutingKey);
    }

    @Bean
    Binding updateCarRetryBinding(){
        return BindingBuilder.bind(updateCarRetryQueue()).to(updateCarRetryExchange()).with(updateCarPreference.updateCarRetryRoutingKey);
    }

    @Bean
    Binding updateCarFinalBinding(){
        return BindingBuilder.bind(updateCarFinalQueue()).to(updateCarFinalExchange()).with(updateCarPreference.updateCarFinalRoutingKey);
    }

    /*
    Delete Car
     */
    @Bean
    FanoutExchange deleteCarOriginalExchange(){
        return new FanoutExchange(deleteCarPreference.deleteCarOriginalExchange);
    }

    @Bean
    DirectExchange deleteCarRetryExchange(){
        return new DirectExchange(deleteCarPreference.deleteCarRetryExchange);
    }

    @Bean
    DirectExchange deleteCarFinalExchange(){
        return new DirectExchange(deleteCarPreference.deleteCarFinalExchange);
    }

    @Bean
    Queue deleteCarOriginalQueue(){
        return QueueBuilder.durable(deleteCarPreference.deleteCarOriginalQueue)
                .withArgument(DEATH_LATTER_EXCHANGE, deleteCarPreference.deleteCarRetryExchange)
                .withArgument(DEATH_LATTER_ROUTING_KEY, deleteCarPreference.deleteCarRetryRoutingKey)
                .withArgument(MESSAGE_TTL, deleteCarPreference.deleteCarTtlOriginal).build();
    }

    @Bean
    Queue deleteCarRetryQueue(){
        return QueueBuilder.durable(deleteCarPreference.deleteCarRetryQueue)
                .withArgument(DEATH_LATTER_EXCHANGE, deleteCarPreference.deleteCarRetryExchange)
                .withArgument(DEATH_LATTER_ROUTING_KEY, deleteCarPreference.deleteCarOriginalRoutingKey)
                .withArgument(MESSAGE_TTL, deleteCarPreference.deleteCarTtlRetry).build();
    }

    @Bean
    Queue deleteCarFinalQueue(){
        return QueueBuilder.durable(deleteCarPreference.deleteCarFinalQueue).build();
    }

    @Bean
    Binding deleteCarOriginalToFanoutBinding(){
        return BindingBuilder.bind(deleteCarOriginalQueue()).to(deleteCarOriginalExchange());
    }

    @Bean
    Binding deleteCarOriginalBinding(){
        return BindingBuilder.bind(deleteCarOriginalQueue()).to(deleteCarRetryExchange()).with(deleteCarPreference.deleteCarOriginalRoutingKey);
    }

    @Bean
    Binding deleteCarRetryBinding(){
        return BindingBuilder.bind(deleteCarRetryQueue()).to(deleteCarRetryExchange()).with(deleteCarPreference.deleteCarRetryRoutingKey);
    }

    @Bean
    Binding deleteCarFinalBinding(){
        return BindingBuilder.bind(deleteCarFinalQueue()).to(deleteCarFinalExchange()).with(deleteCarPreference.deleteCarFinalRoutingKey);
    }
}
