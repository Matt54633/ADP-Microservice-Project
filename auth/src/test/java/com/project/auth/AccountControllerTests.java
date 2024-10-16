package com.project.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.auth.controllers.AccountController;
import com.project.auth.models.Customer;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AccountController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    
    @Test
    void testGetTokenUnauthorized() throws Exception {
        Customer customer = new Customer();
        customer.setName("invaliduser");
        customer.setPassword("wrongpassword");

        mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void testRegisterUser() throws Exception {
        Customer customer = new Customer();
        customer.setName("newuser");
        customer.setEmail("newuser@example.com");
        customer.setPassword("password");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
               .andExpect(status().isCreated());
                }

    @Test
    void testRegisterUserBadRequest() throws Exception {
        Customer customer = new Customer();
        customer.setId(1); // ID should be zero for new registrations
        customer.setName("newuser");
        customer.setEmail("newuser@example.com");
        customer.setPassword("password");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
               .andExpect(status().isBadRequest());
    }
}