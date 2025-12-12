package com.EventHub.eventHub.application.usecase;

import com.EventHub.eventHub.domain.exception.DuplicateResourceException;
import com.EventHub.eventHub.domain.model.PageResponse;
import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.domain.ports.in.VenueUseCase;
import com.EventHub.eventHub.domain.ports.out.VenueRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VenueUseCaseImpl implements VenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public VenueUseCaseImpl(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<Venue> getAllVenues(Pageable pageable, String city, String state, Integer capacity) {
        return venueRepositoryPort.findAll(pageable, city, state, capacity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venue> getVenueById(Long id) {
        return venueRepositoryPort.findById(id);
    }

    @Override
    @Transactional
    public Venue createVenue(Venue venue) {

        if (venueRepositoryPort.findByName(venue.getName()).isPresent()) {
            throw new DuplicateResourceException("Venue with name '" + venue.getName() + "' already exists.");
        }

        venue.setId(null);

        return venueRepositoryPort.save(venue);
    }

    @Override
    @Transactional
    public Optional<Venue> updateVenue(Long id, Venue venue) {
        return venueRepositoryPort.findById(id).map(existingVenue -> {
            if (!existingVenue.getName().equals(venue.getName()) && venueRepositoryPort.findByName(venue.getName()).isPresent()) {
                throw new DuplicateResourceException("Venue with name '" + venue.getName() + "' already exists.");
            }
            venue.setId(id);
            return venueRepositoryPort.save(venue);
        });
    }

    @Override
    @Transactional
    public boolean deleteVenue(Long id) {
        return venueRepositoryPort.findById(id).map(venue -> {
            venueRepositoryPort.deleteById(id);
            return true;
        }).orElse(false);
    }
}
