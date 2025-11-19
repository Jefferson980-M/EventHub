package com.EventHub.eventHub.controller;

import com.EventHub.eventHub.dto.VenueDTO;
import com.EventHub.eventHub.service.VenueService;
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
@RequestMapping("/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @Operation(summary = "Create a new venue", responses = {
            @ApiResponse(responseCode = "200", description = "Venue created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VenueDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public VenueDTO createVenue(@Valid @RequestBody VenueDTO venueDTO) {
        return venueService.createVenue(venueDTO);
    }

    @Operation(summary = "Get all venues", responses = {
            @ApiResponse(responseCode = "200", description = "Found all venues",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VenueDTO.class)))
    })
    @GetMapping
    public List<VenueDTO> getAllVenues() {
        return venueService.getAllVenues();
    }

    @Operation(summary = "Get a venue by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Found the venue",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VenueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a venue", responses = {
            @ApiResponse(responseCode = "200", description = "Venue updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VenueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueDTO venueDTO) {
        return venueService.getVenueById(id)
                .map(existingVenue -> ResponseEntity.ok(venueService.updateVenue(id, venueDTO)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a venue", responses = {
            @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        return venueService.getVenueById(id)
                .map(existingVenue -> {
                    venueService.deleteVenue(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
