package com.ecabs.backendtechnicaltask.consumer.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String passengerName;
    private String passengerContactNumber;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp pickupTime;
    private boolean asap;
    private int waitingTime;
    private int numberOfPassengers;
    private double price;
    private int rating;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createdOn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastModifiedOn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "booking_id")
    private List<TripWaypoint> tripWaypoints = new ArrayList<>();

}
