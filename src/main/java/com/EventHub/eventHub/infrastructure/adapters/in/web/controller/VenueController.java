package com.EventHub.eventHub.infrastructure.adapters.in.web.controller;

import com.EventHub.eventHub.domain.model.PageResponse;
import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.domain.ports.in.VenueUseCase;
import com.EventHub.eventHub.domain.validation.groups.Create;
import com.EventHub.eventHub.domain.validation.groups.Update;
import com.EventHub.eventHub.infrastructure.adapters.in.web.dto.VenueDTO;
import com.EventHub.eventHub.infrastructure.adapters.in.web.mapper.VenueMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/venues")
@SecurityRequirement(name = "bearerAuth")
public class VenueController {

    private final VenueUseCase venueUseCase;
    private final VenueMapper venueMapper;

    public VenueController(VenueUseCase venueUseCase, VenueMapper venueMapper) {
        this.venueUseCase = venueUseCase;
        this.venueMapper = venueMapper;
    }

    @Operation(summary = "Get all venues with pagination and optional filters")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PageResponse<VenueDTO>> getAllVenues(
            Pageable pageable,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Integer capacity) {
        PageResponse<Venue> venues = venueUseCase.getAllVenues(pageable, city, state, capacity);
        return ResponseEntity.ok(new PageResponse<>(
                venues.getContent().stream().map(venueMapper::toDto).collect(Collectors.toList()),
                venues.getPageNumber(),
                venues.getPageSize(),
                venues.getTotalElements(),
                venues.getTotalPages()
        ));
    }

    @Operation(summary = "Get a venue by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        return venueUseCase.getVenueById(id)
                .map(venue -> ResponseEntity.ok(venueMapper.toDto(venue)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new venue")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VenueDTO> createVenue(@Validated(Create.class) @RequestBody VenueDTO venueDTO) {
        Venue venue = venueMapper.toDomain(venueDTO);
        Venue createdVenue = venueUseCase.createVenue(venue);
        return new ResponseEntity<>(venueMapper.toDto(createdVenue), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a venue")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @Validated(Update.class) @RequestBody VenueDTO venueDTO) {
        Venue venue = venueMapper.toDomain(venueDTO);
        return venueUseCase.updateVenue(id, venue)
                .map(updatedVenue -> ResponseEntity.ok(venueMapper.toDto(updatedVenue)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a venue")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        if (venueUseCase.deleteVenue(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
