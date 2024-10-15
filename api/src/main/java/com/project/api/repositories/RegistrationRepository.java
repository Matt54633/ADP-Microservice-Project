package com.project.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.api.models.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, Long> {
}