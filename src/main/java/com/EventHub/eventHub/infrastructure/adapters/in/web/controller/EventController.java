package com.EventHub.eventHub.infrastructure.adapters.in.web.controller;

import com.EventHub.eventHub.domain.exception.ResourceNotFoundException;
import com.EventHub.eventHub.domain.model.Event;
import com.EventHub.eventHub.domain.model.PageResponse;
import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.domain.ports.in.EventUseCase;
import com.EventHub.eventHub.domain.ports.in.VenueUseCase;
import com.EventHub.eventHub.domain.validation.groups.Create;
import com.EventHub.eventHub.domain.validation.groups.Update;
import com.EventHub.eventHub.infrastructure.adapters.in.web.dto.EventDTO;
import com.EventHub.eventHub.infrastructure.adapters.in.web.mapper.EventMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@SecurityRequirement(name = "bearerAuth")
public class EventController {

    private final EventUseCase eventUseCase;
    private final VenueUseCase venueUseCase;
    private final EventMapper eventMapper;

    public EventController(EventUseCase eventUseCase, VenueUseCase venueUseCase, EventMapper eventMapper) {
        this.eventUseCase = eventUseCase;
        this.venueUseCase = venueUseCase;
        this.eventMapper = eventMapper;
    }

    @Operation(summary = "Create a new event")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDTO> createEvent(@Validated(Create.class) @RequestBody EventDTO eventDTO) {
        // 1. Mapear el DTO a un objeto de dominio Event (el venue será nulo)
        Event event = eventMapper.toDomain(eventDTO);

        // 2. Buscar el Venue usando el venueId del DTO
        Venue venue = venueUseCase.getVenueById(eventDTO.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + eventDTO.getVenueId()));

        // 3. Asignar el Venue encontrado al Event
        event.setVenue(venue);

        // 4. Llamar al caso de uso para crear el evento
        Event createdEvent = eventUseCase.createEvent(event);

        // 5. Devolver el DTO completo
        return new ResponseEntity<>(eventMapper.toDto(createdEvent), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all events with pagination and optional filters")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PageResponse<EventDTO>> getAllEvents(
            Pageable pageable,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String status) {
        PageResponse<Event> events = eventUseCase.getAllEvents(pageable, city, category, startDate, endDate, status);
        return ResponseEntity.ok(new PageResponse<>(
                events.getContent().stream().map(eventMapper::toDto).collect(Collectors.toList()),
                events.getPageNumber(),
                events.getPageSize(),
                events.getTotalElements(),
                events.getTotalPages()
        ));
    }

    @Operation(summary = "Get an event by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return eventUseCase.getEventById(id)
                .map(event -> ResponseEntity.ok(eventMapper.toDto(event)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an event")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Validated(Update.class) @RequestBody EventDTO eventDTO) {
        Event event = eventMapper.toDomain(eventDTO);
        
        Venue venue = venueUseCase.getVenueById(eventDTO.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with id: " + eventDTO.getVenueId()));
        event.setVenue(venue);

        return eventUseCase.updateEvent(id, event)
                .map(updatedEvent -> ResponseEntity.ok(eventMapper.toDto(updatedEvent)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an event")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (eventUseCase.deleteEvent(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
