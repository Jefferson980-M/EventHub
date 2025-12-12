package com.EventHub.eventHub.domain.ports.out;

import com.EventHub.eventHub.domain.model.PageResponse;
import com.EventHub.eventHub.domain.model.Venue;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VenueRepositoryPort {
    Venue save(Venue venue);
    Optional<Venue> findById(Long id);
    Optional<Venue> findByName(String name);
    List<Venue> findAll();
    PageResponse<Venue> findAll(Pageable pageable, String city, String state, Integer capacity);
    void deleteById(Long id);
}
