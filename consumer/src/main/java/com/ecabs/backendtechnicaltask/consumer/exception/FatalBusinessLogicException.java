package com.ecabs.backendtechnicaltask.consumer.exception;

public class FatalBusinessLogicException extends RuntimeException {
    private static final long serialVersionUID = 3876200138884289087L;

    public FatalBusinessLogicException(String message) {
        super(message);
    }
}
