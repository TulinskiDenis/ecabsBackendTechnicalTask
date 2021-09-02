package com.ecabs.booking.backendtechnicaltask.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Response {

    private final int code;
    private final String message;

}
