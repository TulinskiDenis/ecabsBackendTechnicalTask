package com.ecabs.backendtechnicaltask.producer.sender.internalmessage;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DeleteMessageBuilder implements OutboundMessageBuilder<Long> {

    @Value("${routing.delete}")
    private String deleteRoute;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public OutboundMessage prepare(Long msgBody) {
        try {
            byte[] bytesBody = objectMapper.writeValueAsString(msgBody).getBytes();
            Message message = MessageBuilder.withBody(bytesBody)
                    .setContentType(MessageProperties.CONTENT_TYPE_BYTES)
                    .setMessageId(UUID.randomUUID().toString())
                    .setHeader("operation-type", "delete")
                    .build();

            return new OutboundMessage(deleteRoute, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.DELETE;
    }
}