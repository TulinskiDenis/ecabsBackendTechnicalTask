package com.ecabs.backendtechnicaltask.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecabs.backendtechnicaltask.consumer.dao.BookingDao;
import com.ecabs.backendtechnicaltask.consumer.exception.FatalBusinessLogicException;
import com.ecabs.backendtechnicaltask.consumer.model.Booking;

@Service
public class CUDOperationsService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private BookingDao bookingDao;

    public Booking add(Booking booking) {
        LOG.debug("Saving record: {}", booking);
        return bookingDao.save(booking);
    }

    public void edit(Booking booking) {
        if (booking.getId() == null) {
            throw new FatalBusinessLogicException(
                    "Entity id is null. Update operation failed. Should not be re-queued in DLQ test");
        }
        LOG.debug("Editing record: {}", booking);
        bookingDao.save(booking);
    }

    public void delete(Long id) {
        LOG.debug("Deleteing record: id={}", id);
        bookingDao.deleteById(id);
    }
}
