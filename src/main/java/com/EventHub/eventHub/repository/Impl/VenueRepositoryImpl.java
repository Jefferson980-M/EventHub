package com.EventHub.eventHub.repository.Impl;

import com.EventHub.eventHub.model.Venue;
import com.EventHub.eventHub.repository.VenueRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VenueRepositoryImpl implements VenueRepository {
    private final List<Venue> venues = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Venue save(Venue venue) {
        if (venue.getId() == null) {
            venue.setId(counter.incrementAndGet());
            venues.add(venue);
        } else {
            Optional<Venue> existingVenue = findById(venue.getId());
            existingVenue.ifPresent(value -> {
                value.setName(venue.getName());
                value.setCity(venue.getCity());
                value.setState(venue.getState());
            });
        }
        return venue;
    }

    @Override
    public List<Venue> findAll() {
        return new ArrayList<>(venues);
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return venues.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Long id) {
        venues.removeIf(v -> v.getId().equals(id));
    }
}
