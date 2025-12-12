package com.EventHub.eventHub.domain.ports.out;

import com.EventHub.eventHub.domain.model.Event;
import com.EventHub.eventHub.domain.model.PageResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(Long id);
    PageResponse<Event> findAll(Pageable pageable, String city, String category, LocalDate startDate, LocalDate endDate, String status);
    void deleteById(Long id);
    Optional<Event> findByName(String name);
}
