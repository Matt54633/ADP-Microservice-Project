package com.project.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.models.Event;
import com.project.api.repositories.EventRepository;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventRepository repository;

    // CRUD Event Endpoints

    @GetMapping
    public Iterable<Event> getAll() {
        return repository.findAll();
    }
}
