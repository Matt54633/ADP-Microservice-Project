package com.project.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.api.controllers.CustomerController;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {
    @Autowired
    CustomerController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

 
}
