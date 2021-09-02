package com.ecabs.backendtechnicaltask.producer.sender;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecabs.backendtechnicaltask.producer.mq.RabbitMqClient;
import com.ecabs.backendtechnicaltask.producer.sender.internalmessage.OperationType;
import com.ecabs.backendtechnicaltask.producer.sender.internalmessage.OutboundMessage;
import com.ecabs.backendtechnicaltask.producer.sender.internalmessage.OutboundMessageBuilder;

@Component
public class RabbitMqSender {

    @Autowired
    private Map<Enum, OutboundMessageBuilder> messageConstructors;

    @Autowired
    private RabbitMqClient rabbitMqClient;

    public boolean send(OperationType operationType, Object body) {
        OutboundMessage message = messageConstructors.get(operationType).prepare(body);
        return rabbitMqClient.sendMessageWithConfirmation(message);
    }
}
