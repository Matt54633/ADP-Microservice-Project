package com.project.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApiController {

    // Un-authenticated health check

    @GetMapping
    public String healthCheck() {
        return "API Service is running!";
    }

}