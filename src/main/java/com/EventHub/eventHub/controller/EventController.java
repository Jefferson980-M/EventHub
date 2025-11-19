package com.EventHub.eventHub.controller;

import com.EventHub.eventHub.dto.EventDTO;
import com.EventHub.eventHub.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Create a new event", responses = {
            @ApiResponse(responseCode = "200", description = "Event created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public EventDTO createEvent(@Valid @RequestBody EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    @Operation(summary = "Get all events", responses = {
            @ApiResponse(responseCode = "200", description = "Found all events",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDTO.class)))
    })
    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Operation(summary = "Get an event by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Found the event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an event", responses = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        return eventService.getEventById(id)
                .map(existingEvent -> ResponseEntity.ok(eventService.updateEvent(id, eventDTO)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an event", responses = {
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(existingEvent -> {
                    eventService.deleteEvent(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
