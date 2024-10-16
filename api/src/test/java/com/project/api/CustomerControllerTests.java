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
import com.project.api.controllers.CustomerController;
import com.project.api.models.Customer;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CustomerController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void testGetCustomer() throws Exception {
        mockMvc.perform(get("/customers/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.email").value("tom@example.com"))
                .andExpect(jsonPath("$.password").value("pAssWorD"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setName("John Doe");
        newCustomer.setEmail("john.doe@example.com");
        newCustomer.setPassword("password123");

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1);
        updatedCustomer.setName("Updated Name");
        updatedCustomer.setEmail("updated.email@example.com");
        updatedCustomer.setPassword("newpassword");

        mockMvc.perform(put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testLookupCustomerByNameGet() throws Exception {
        mockMvc.perform(get("/customers/byname/Ella"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ella"));
    }

    @Test
    void testLookupCustomerByNamePost() throws Exception {
        mockMvc.perform(post("/customers/byname")
                .contentType(MediaType.TEXT_PLAIN)
                .content("Matt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Matt"));
    }
}