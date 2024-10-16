package com.project.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.controllers.EventController;
import com.project.api.models.Event;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EventController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testGetEvent() throws Exception {
        mockMvc.perform(get("/events/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("All-Java Conference"))
               .andExpect(jsonPath("$.description").value("Lectures and exhibits covering all Java topics"));
    }

    @Test
    void testGetAllEvents() throws Exception {
        mockMvc.perform(get("/events"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void testCreateEvent() throws Exception {
        Event newEvent = new Event();
        newEvent.setTitle("New Event");
        newEvent.setDescription("Description of the new event");

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEvent)))
               .andExpect(status().isCreated());
                }

    @Test
    void testUpdateEvent() throws Exception {
        Event updatedEvent = new Event();
        updatedEvent.setId(1);
        updatedEvent.setTitle("Updated Event");
        updatedEvent.setDescription("Updated description");

        mockMvc.perform(put("/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEvent)))
               .andExpect(status().isOk());
    }

    @Test
    void testDeleteEvent() throws Exception {
        mockMvc.perform(delete("/events/2"))
               .andExpect(status().isNoContent());
    }
}