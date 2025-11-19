package com.EventHub.eventHub.repository;

import com.EventHub.eventHub.model.Event;
import java.util.List;
import java.util.Optional;

public interface EventRepository {
    Event save(Event event);
    List<Event> findAll();
    Optional<Event> findById(Long id);
    void deleteById(Long id);
}
