package com.ecabs.backendtechnicaltask.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ecabs.backendtechnicaltask.producer.ProducerApplication;
import com.ecabs.backendtechnicaltask.producer.mq.RabbitMqClient;
import com.ecabs.booking.backendtechnicaltask.dto.BookingDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = ProducerApplication.class)
@AutoConfigureMockMvc
public class AppIntegrationTest {

    @MockBean
    private RabbitMqClient rabbitMqClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddOperationSuccess() throws Exception, JsonProcessingException, UnsupportedEncodingException {
        when(rabbitMqClient.sendMessageWithConfirmation(ArgumentMatchers.any())).thenReturn(true);

        BookingDto booking = new BookingDto();
        booking.setPassengerName("Bob");
        booking.setPassengerContactNumber("+3751231231223");
        booking.setCreatedOn(LocalDateTime.now());
        booking.setLastModifiedOn(LocalDateTime.now());
        booking.setNumberOfPassengers(0);
        byte[] bytesBody = objectMapper.writeValueAsString(booking).getBytes();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/booking/add")
                .header("API-VERSION", 1)
                .content(bytesBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andReturn();

        Map<String, Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        assertEquals(response.get("code"), 0);
    }

    @Test
    public void testAddOperationFail() throws Exception, JsonProcessingException, UnsupportedEncodingException {
        when(rabbitMqClient.sendMessageWithConfirmation(ArgumentMatchers.any())).thenReturn(false);

        BookingDto booking = new BookingDto();
        booking.setPassengerName("Bob");
        booking.setPassengerContactNumber("+3751231231223");
        booking.setCreatedOn(LocalDateTime.now());
        booking.setLastModifiedOn(LocalDateTime.now());
        booking.setNumberOfPassengers(0);
        byte[] bytesBody = objectMapper.writeValueAsString(booking).getBytes();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/booking/add")
                .header("API-VERSION", 1)
                .content(bytesBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Map<String, Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        assertEquals(response.get("code"), 1);
    }
}