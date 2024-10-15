package com.project.api.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping("/{id}")
    public Optional<Event> getEvent(@PathVariable("id") long id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event newEvent) {
        if (newEvent.getId() != 0 || newEvent.getTitle() == null || newEvent.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        newEvent = repository.save(newEvent);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newEvent.getId()).toUri();

        ResponseEntity<?> response = ResponseEntity.created(location).build();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putEvent(
            @RequestBody Event newEvent,
            @PathVariable("id") long eventId) {

        if (newEvent.getId() != eventId || newEvent.getTitle() == null || newEvent.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        newEvent = repository.save(newEvent);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") long id) {
        var event = repository.findById(id);

        if (event != null)
            repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
