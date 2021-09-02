package com.ecabs.backendtechnicaltask.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecabs.backendtechnicaltask.consumer.listener.BusinessLogicFatalErrorHandler;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    @Bean
    DirectExchange bookingExchange() {
        return ExchangeBuilder.directExchange("booking.exch").durable(false).build();
    }

    @Bean
    FanoutExchange messageExchange() {
        return ExchangeBuilder.fanoutExchange("message.exch").durable(false).build();
    }

    @Bean
    Binding exchangesBinding(FanoutExchange messageExchange) {
        return BindingBuilder.bind(bookingExchange()).to(messageExchange);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(new BusinessLogicFatalErrorHandler());
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
