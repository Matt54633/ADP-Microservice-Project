package com.project.api.controllers;

import java.net.URI;
import java.util.Iterator;
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

import com.project.api.models.Customer;
import com.project.api.repositories.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    // CRUD Customer Endpoints

    @GetMapping
    public Iterable<Customer> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable("id") long id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer newCustomer) {
        if (newCustomer.getId() != 0 || newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        newCustomer = repository.save(newCustomer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newCustomer.getId()).toUri();

        ResponseEntity<?> response = ResponseEntity.created(location).build();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCustomer(
            @RequestBody Customer newCustomer,
            @PathVariable("id") long customerId) {

        if (newCustomer.getId() != customerId || newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        newCustomer = repository.save(newCustomer);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
        var customer = repository.findById(id);

        if (customer != null)
            repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Front-end references 'byname' endpoints
    // Required for customer lookup for authentication

    @GetMapping("/byname/{name}")
    public ResponseEntity<?> lookupCustomerByNameGet(@PathVariable("name") String username) {

        Iterator<Customer> customers = repository.findAll().iterator();

        while (customers.hasNext()) {
            Customer customer = customers.next();

            if (customer.getName().equalsIgnoreCase(username)) {
                ResponseEntity<?> response = ResponseEntity.ok(customer);
                return response;
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/byname")
    public ResponseEntity<?> lookupCustomerByNamePost(@RequestBody String username) {

        Iterator<Customer> customers = repository.findAll().iterator();

        while (customers.hasNext()) {
            Customer customer = customers.next();

            if (customer.getName().equals(username)) {
                ResponseEntity<?> response = ResponseEntity.ok(customer);
                return response;
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
