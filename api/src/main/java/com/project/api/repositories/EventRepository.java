package com.project.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.api.models.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
}
