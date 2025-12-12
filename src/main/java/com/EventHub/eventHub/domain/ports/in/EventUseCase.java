package com.EventHub.eventHub.domain.ports.in;

import com.EventHub.eventHub.domain.model.Event;
import com.EventHub.eventHub.domain.model.PageResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface EventUseCase {
    Event createEvent(Event event);
    Optional<Event> getEventById(Long id);
    PageResponse<Event> getAllEvents(Pageable pageable, String city, String category, LocalDate startDate, LocalDate endDate, String status);
    Optional<Event> updateEvent(Long id, Event event);
    boolean deleteEvent(Long id);
}
