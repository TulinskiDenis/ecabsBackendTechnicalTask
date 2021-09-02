package com.ecabs.booking.backendtechnicaltask.dto;

import lombok.Data;

@Data
public class TripWaypointDto {

    private Long id;
    private String locality;
    private Double latitude;
    private Double longitude;

}
