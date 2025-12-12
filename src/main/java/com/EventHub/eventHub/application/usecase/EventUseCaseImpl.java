package com.EventHub.eventHub.application.usecase;

import com.EventHub.eventHub.domain.exception.DuplicateResourceException;
import com.EventHub.eventHub.domain.model.Event;
import com.EventHub.eventHub.domain.model.PageResponse;
import com.EventHub.eventHub.domain.ports.in.EventUseCase;
import com.EventHub.eventHub.domain.ports.out.EventRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EventUseCaseImpl implements EventUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public EventUseCaseImpl(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    @Transactional
    public Event createEvent(Event event) {
        if (eventRepositoryPort.findByName(event.getName()).isPresent()) {
            throw new DuplicateResourceException("Event with name '" + event.getName() + "' already exists.");
        }
        event.setId(null);
        return eventRepositoryPort.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Event> getEventById(Long id) {
        return eventRepositoryPort.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<Event> getAllEvents(Pageable pageable, String city, String category, LocalDate startDate, LocalDate endDate, String status) {
        return eventRepositoryPort.findAll(pageable, city, category, startDate, endDate, status);
    }

    @Override
    @Transactional
    public Optional<Event> updateEvent(Long id, Event event) {
        return eventRepositoryPort.findById(id).map(existingEvent -> {
            if (!existingEvent.getName().equals(event.getName()) && eventRepositoryPort.findByName(event.getName()).isPresent()) {
                throw new DuplicateResourceException("Event with name '" + event.getName() + "' already exists.");
            }
            event.setId(id);
            return eventRepositoryPort.save(event);
        });
    }

    @Override
    @Transactional
    public boolean deleteEvent(Long id) {
        return eventRepositoryPort.findById(id).map(event -> {
            eventRepositoryPort.deleteById(id);
            return true;
        }).orElse(false);
    }
}
