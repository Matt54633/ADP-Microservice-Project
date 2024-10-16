package com.project.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.controllers.RegistrationController;
import com.project.api.models.Registration;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RegistrationController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testGetRegistration() throws Exception {
        mockMvc.perform(get("/registrations/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.customerId").value(1))
               .andExpect(jsonPath("$.eventId").value(1))
               .andExpect(jsonPath("$.registrationDate").value("2019-01-15"))
               .andExpect(jsonPath("$.notes").value("please email me the event details"));
    }

    @Test
    void testGetAllRegistrations() throws Exception {
        mockMvc.perform(get("/registrations"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void testCreateRegistration() throws Exception {
        Registration newRegistration = new Registration();
        newRegistration.setCustomerId(0); // Ensure customerId is zero for validation
        newRegistration.setEventId(0); // Ensure eventId is zero for validation
        newRegistration.setRegistrationDate(Date.valueOf("2023-01-01"));
        newRegistration.setNotes("Looking forward to the event");

        mockMvc.perform(post("/registrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRegistration)))
               .andExpect(status().isCreated());
    }

    @Test
    void testUpdateRegistration() throws Exception {
        Registration updatedRegistration = new Registration();
        updatedRegistration.setId(1);
        updatedRegistration.setCustomerId(1);
        updatedRegistration.setEventId(1);
        updatedRegistration.setRegistrationDate(Date.valueOf("2023-01-01"));
        updatedRegistration.setNotes("Updated notes");

        mockMvc.perform(put("/registrations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRegistration)))
               .andExpect(status().isOk());
    }

    @Test
    void testDeleteRegistration() throws Exception {
        mockMvc.perform(delete("/registrations/2"))
               .andExpect(status().isNoContent());
    }
}