package com.ecabs.backendtechnicaltask.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecabs.booking.backendtechnicaltask.dto.Response;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String SYSTEM_ERROR_CODE = "system.error";

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleSystemError(Exception exception) {
        logger.debug("SYSTEM ERROR: ", exception);
        return ResponseEntity.internalServerError()
                .body(Response.of(10, messageSource.getMessage(SYSTEM_ERROR_CODE, null, null)));
    }
}
