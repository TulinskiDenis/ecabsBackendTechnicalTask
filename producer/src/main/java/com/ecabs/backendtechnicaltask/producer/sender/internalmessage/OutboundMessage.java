package com.ecabs.backendtechnicaltask.producer.sender.internalmessage;

import org.springframework.amqp.core.Message;

public class OutboundMessage {

    public OutboundMessage(String routingKey, Message message) {
        this.routingKey = routingKey;
        this.message = message;
    }

    private String routingKey;

    private Message message;

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
