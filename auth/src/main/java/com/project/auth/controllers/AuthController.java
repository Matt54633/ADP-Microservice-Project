package com.project.auth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
    
    // Un-authenticated health check

    @GetMapping
    public String healthCheck() {
        return "Auth Service is running!";
    }
}
