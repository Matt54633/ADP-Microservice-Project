package com.project.auth;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.auth.controllers.AuthController;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthControllerTests {
    @Autowired
    AuthController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void healthCheckReturnsDefaultMessage() throws Exception {
        String result = controller.healthCheck();
        assertNotNull(result);
        assertTrue(result.contains("Auth Service is running!"));
    }

}
