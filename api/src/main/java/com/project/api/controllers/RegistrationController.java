package com.project.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.models.Registration;
import com.project.api.repositories.RegistrationRepository;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    
    @Autowired RegistrationRepository repository;

    // CRUD Event Endpoints

    @GetMapping
    public Iterable<Registration> getAll() {
        return repository.findAll();
    }
}
