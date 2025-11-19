package com.EventHub.eventHub.repository;

import com.EventHub.eventHub.model.Venue;
import java.util.List;
import java.util.Optional;

public interface VenueRepository {
    Venue save(Venue venue);
    List<Venue> findAll();
    Optional<Venue> findById(Long id);
    void deleteById(Long id);
}
