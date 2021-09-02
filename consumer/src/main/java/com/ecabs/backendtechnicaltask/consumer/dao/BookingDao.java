package com.ecabs.backendtechnicaltask.consumer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecabs.backendtechnicaltask.consumer.model.Booking;

public interface BookingDao extends JpaRepository<Booking, Long> {

}
