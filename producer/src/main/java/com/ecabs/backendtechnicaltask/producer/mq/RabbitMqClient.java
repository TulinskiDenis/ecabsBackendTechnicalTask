package com.ecabs.backendtechnicaltask.producer.mq;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecabs.backendtechnicaltask.producer.sender.internalmessage.OutboundMessage;

@Component
public class RabbitMqClient {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public boolean sendMessageWithConfirmation(OutboundMessage outbound) {
        LOG.debug("PUBLISHING MESSAGE : \nroutingKey=" + outbound.getRoutingKey() + "\nmessage=" + outbound.getMessage());

        CorrelationData correlationData = new CorrelationData();
        boolean result = false;
        rabbitTemplate.convertAndSend(outbound.getRoutingKey(), outbound.getMessage(), correlationData);
        try {
            result = correlationData.getFuture().get(10, TimeUnit.SECONDS).isAck();
            LOG.debug("CONFIRMATION ACK: " + result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOG.error("ERROR: ", e);
        }
        return result;
    }

}
