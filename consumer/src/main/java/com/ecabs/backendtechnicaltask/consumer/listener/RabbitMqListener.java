package com.ecabs.backendtechnicaltask.consumer.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecabs.backendtechnicaltask.consumer.model.Booking;
import com.ecabs.backendtechnicaltask.consumer.service.AuditService;
import com.ecabs.backendtechnicaltask.consumer.service.CUDOperationsService;
import com.ecabs.backendtechnicaltask.consumer.service.FailedMessagesHandlerService;

@Component
public class RabbitMqListener {

    @Autowired
    private AuditService auditService;

    @Autowired
    private FailedMessagesHandlerService failedMessagesHandler;

    @Autowired
    private CUDOperationsService cudOperationsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${queue.name.audit}", durable = "false", arguments = {
                    @Argument(name = "x-dead-letter-exchange", value = "${exch.name.dlq}"),
                    @Argument(name = "x-dead-letter-routing-key", value = "${queue.name.dlq}")
            }),
            exchange = @Exchange(value = "${exch.name.message}", ignoreDeclarationExceptions = "true",
                       durable = "false", type = "fanout"),
            key = "${routing.dlq}"))
    public void audit(Message msg) {
        auditService.logAuditMessage(msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${queue.name.add}", durable = "false", arguments = {
                    @Argument(name = "x-dead-letter-exchange", value = "${exch.name.dlq}"),
                    @Argument(name = "x-dead-letter-routing-key", value = "${queue.name.dlq}")
            }),
            exchange = @Exchange(value = "${exch.name.booking}", ignoreDeclarationExceptions = "true", durable = "false"),
            key = "${routing.add}"))
    public void add(Booking booking) {
        cudOperationsService.add(booking);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${queue.name.edit}", durable = "false", arguments = {
                    @Argument(name = "x-dead-letter-exchange", value = "${exch.name.dlq}"),
                    @Argument(name = "x-dead-letter-routing-key", value = "${queue.name.dlq}")
            }),
            exchange = @Exchange(value = "${exch.name.booking}", ignoreDeclarationExceptions = "true", durable = "false"),
            key = "${routing.edit}"))
    public void edit(Booking booking) {
        cudOperationsService.edit(booking);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${queue.name.delete}", durable = "false", arguments = {
                    @Argument(name = "x-dead-letter-exchange", value = "${exch.name.dlq}"),
                    @Argument(name = "x-dead-letter-routing-key", value = "${queue.name.dlq}")
            }),
            exchange = @Exchange(value = "${exch.name.booking}", ignoreDeclarationExceptions = "true", durable = "false"),
            key = "${routing.delete}"))
    public void delete(Long id) {
        cudOperationsService.delete(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${queue.name.dlq}", durable = "false"),
            exchange = @Exchange(value = "${exch.name.dlq}", ignoreDeclarationExceptions = "true", durable = "false"),
            key = "${routing.dlq}"))
    public void processFailedMessages(Message message) {
        failedMessagesHandler.processFailedMessage(message);
    }
}
