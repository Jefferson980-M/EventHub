package com.EventHub.eventHub.domain.ports.in;

import com.EventHub.eventHub.domain.model.PageResponse;
import com.EventHub.eventHub.domain.model.Venue;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VenueUseCase {
    PageResponse<Venue> getAllVenues(Pageable pageable, String city, String state, Integer capacity);
    Optional<Venue> getVenueById(Long id);
    Venue createVenue(Venue venue);
    Optional<Venue> updateVenue(Long id, Venue venue);
    boolean deleteVenue(Long id);
}
