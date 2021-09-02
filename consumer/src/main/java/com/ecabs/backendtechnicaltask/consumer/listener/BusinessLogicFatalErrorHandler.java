package com.ecabs.backendtechnicaltask.consumer.listener;

import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;

import com.ecabs.backendtechnicaltask.consumer.exception.FatalBusinessLogicException;

public class BusinessLogicFatalErrorHandler extends ConditionalRejectingErrorHandler {

    /**
     * Checks if the error was caused by an instance of FatalBusinessLogicException
     * and skips sending the message to the dead-letter-queue. Otherwise performs
     * default error processing logic.
     *
     * @param wrapper for listener exceptions
     */
    @Override
    public void handleError(Throwable t) {
        if ((t.getCause() instanceof FatalBusinessLogicException)) {
            throw new ImmediateAcknowledgeAmqpException("Fatal Business Logic Exception: not sending to DLQ");
        }
        super.handleError(t);
    }

}
