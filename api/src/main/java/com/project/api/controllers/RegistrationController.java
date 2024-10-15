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

import com.project.api.models.Registration;
import com.project.api.repositories.RegistrationRepository;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    RegistrationRepository repository;

    // CRUD Event Endpoints

    @GetMapping
    public Iterable<Registration> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Registration> getRegistration(@PathVariable("id") long id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> createRegistration(@RequestBody Registration newRegistration) {
        if (newRegistration.getId() != 0 || newRegistration.getCustomerId() != 0 || newRegistration.getEventId() != 0) {
            return ResponseEntity.badRequest().build();
        }

        newRegistration = repository.save(newRegistration);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRegistration.getId()).toUri();

        ResponseEntity<?> response = ResponseEntity.created(location).build();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putRegistratioEntity(
            @RequestBody Registration newRegistration,
            @PathVariable("id") long registrationId) {

        if (newRegistration.getId() != registrationId) {
            return ResponseEntity.badRequest().build();
        }

        newRegistration = repository.save(newRegistration);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable("id") long id) {
        var registration = repository.findById(id);

        if (registration != null)
            repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
