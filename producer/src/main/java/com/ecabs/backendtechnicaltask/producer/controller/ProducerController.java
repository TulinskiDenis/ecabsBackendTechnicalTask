package com.ecabs.backendtechnicaltask.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecabs.backendtechnicaltask.producer.sender.RabbitMqSender;
import com.ecabs.backendtechnicaltask.producer.sender.internalmessage.OperationType;
import com.ecabs.booking.backendtechnicaltask.dto.BookingDto;
import com.ecabs.booking.backendtechnicaltask.dto.Response;

@RestController
@RequestMapping("api/booking")
public class ProducerController {
    private static final String RESPONSE_MESSAGE_PREFIC = "api.response.success.";

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Autowired
    MessageSource messageSource;

    @PostMapping(value = "/add", headers = "API-VERSION=1")
    public Response add(@RequestBody BookingDto bookingDto) {
        boolean status = rabbitMqSender.send(OperationType.ADD, bookingDto);

        return getResponse(status);
    }

    @PutMapping(value = "/edit", headers = "API-VERSION=1")
    public Response edit(@RequestBody BookingDto bookingDto) {
        boolean status = rabbitMqSender.send(OperationType.EDIT, bookingDto);
        return getResponse(status);
    }

    @DeleteMapping(value = "/delete/{id}", headers = "API-VERSION=1")
    public Response delete(@PathVariable Long id) {
        boolean status = rabbitMqSender.send(OperationType.DELETE, id);
        return getResponse(status);
    }

    private Response getResponse(boolean status) {
        return Response.of(status ? 0 : 1,
                messageSource.getMessage(RESPONSE_MESSAGE_PREFIC + status, null, null));
    }
}
