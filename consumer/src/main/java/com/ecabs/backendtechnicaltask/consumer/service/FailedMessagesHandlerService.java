package com.ecabs.backendtechnicaltask.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

@Service
public class FailedMessagesHandlerService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void processFailedMessage(Message message) {
        LOG.debug("FAILED MESSAGE DELIVERED TO DLQ FOR FURTHER PROCESSING: " + message.toString());
    }
}
