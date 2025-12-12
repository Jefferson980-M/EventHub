package com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter;

import com.EventHub.eventHub.AbstractIntegrationTest;
import com.EventHub.eventHub.domain.model.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class VenueJpaAdapterTest extends AbstractIntegrationTest {

    @Autowired
    private VenueJpaAdapter venueJpaAdapter;

    @Test
    void saveAndFindById_shouldPersistAndRetrieveVenue() {
        // Given
        Venue venueToSave = new Venue();
        venueToSave.setName("Integration Test Venue");
        venueToSave.setCity("Container City");
        venueToSave.setState("Docker State");
        venueToSave.setCapacity(500);

        // When
        Venue savedVenue = venueJpaAdapter.save(venueToSave);
        Optional<Venue> foundVenueOpt = venueJpaAdapter.findById(savedVenue.getId());

        // Then
        assertNotNull(savedVenue.getId());
        assertTrue(foundVenueOpt.isPresent());
        Venue foundVenue = foundVenueOpt.get();
        assertEquals("Integration Test Venue", foundVenue.getName());
        assertEquals("Container City", foundVenue.getCity());
    }
}
