package com.EventHub.eventHub.service;

import com.EventHub.eventHub.dto.EventDTO;
import com.EventHub.eventHub.model.Event;
import com.EventHub.eventHub.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event = eventRepository.save(event);
        eventDTO.setId(event.getId());
        return eventDTO;
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(event -> {
            EventDTO dto = new EventDTO();
            dto.setId(event.getId());
            dto.setName(event.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<EventDTO> getEventById(Long id) {
        return eventRepository.findById(id).map(event -> {
            EventDTO dto = new EventDTO();
            dto.setId(event.getId());
dto.setName(event.getName());
            return dto;
        });
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = new Event();
        event.setId(id);
        event.setName(eventDTO.getName());
        event = eventRepository.save(event);
        eventDTO.setId(event.getId());
        return eventDTO;
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
