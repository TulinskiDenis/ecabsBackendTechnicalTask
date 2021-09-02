package com.ecabs.backendtechnicaltask.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public void logAuditMessage(Message message) {
        LOG.debug("AUDIT MESSAGE: " + message.toString());
    }
}
