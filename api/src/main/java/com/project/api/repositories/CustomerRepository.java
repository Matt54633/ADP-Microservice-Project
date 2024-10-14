package com.project.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.api.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> { }