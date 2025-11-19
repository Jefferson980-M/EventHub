package com.EventHub.eventHub.repository.Impl;

import com.EventHub.eventHub.model.Event;
import com.EventHub.eventHub.repository.EventRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepositoryImpl implements EventRepository {
    private final List<Event> events = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Event save(Event event) {
        if (event.getId() == null) {
            event.setId(counter.incrementAndGet());
            events.add(event);
        } else {
            Optional<Event> existingEvent = findById(event.getId());
            existingEvent.ifPresent(value -> {
                value.setName(event.getName());
            });
        }
        return event;
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(events);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return events.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        events.removeIf(e -> e.getId().equals(id));
    }
}
