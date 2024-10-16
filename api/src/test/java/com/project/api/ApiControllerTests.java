package com.project.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.project.api.controllers.ApiController;

@SpringBootTest
public class ApiControllerTests {

    @Autowired
    ApiController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void healthCheckReturnsDefaultMessage() throws Exception {
        String result = controller.healthCheck();
        assertNotNull(result);
        assertTrue(result.contains("API Service is running!"));
    }

}
