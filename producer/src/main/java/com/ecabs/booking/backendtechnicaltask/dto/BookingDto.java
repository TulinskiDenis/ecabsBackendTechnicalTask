package com.ecabs.booking.backendtechnicaltask.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BookingDto {

    private Long id;
    private String passengerName;
    private String passengerContactNumber;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pickupTime;
    private boolean asap;
    private int waitingTime;
    private int numberOfPassengers;
    private double price;
    private int rating;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedOn;
    private List<TripWaypointDto> tripWaypoints = new ArrayList<>();

}
