package com.ecabs.backendtechnicaltask.producer.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecabs.backendtechnicaltask.producer.sender.internalmessage.OutboundMessageBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class Config {

    @Value("${exch.name.message}")
    private String messageExchange;

    @Bean
    public Map<Enum, OutboundMessageBuilder> messageConstructors(List<OutboundMessageBuilder> senders) {
        return senders.stream()
                .collect(HashMap::new,
                        (map, sender) -> map.put(sender.getOperationType(), sender),
                        (map1, map2) -> map1.putAll(map2));
    }

    @Bean
    public ConnectionFactory createConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setPublisherConfirmType(ConfirmType.CORRELATED);
        connectionFactory.setChannelCacheSize(3);
        connectionFactory.setChannelCheckoutTimeout(1000L);
        return connectionFactory;
    }

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(messageExchange);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper()));
        return rabbitTemplate;
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
