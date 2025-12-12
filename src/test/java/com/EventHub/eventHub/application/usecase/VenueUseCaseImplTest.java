package com.EventHub.eventHub.application.usecase;

import com.EventHub.eventHub.domain.exception.DuplicateResourceException;
import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.domain.ports.out.VenueRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueUseCaseImplTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private VenueUseCaseImpl venueUseCase;

    private Venue venue;

    @BeforeEach
    void setUp() {
        venue = new Venue();
        venue.setId(1L);
        venue.setName("Test Venue");
        venue.setCity("Test City");
        venue.setState("Test State");
        venue.setCapacity(100);
    }

    @Test
    void createVenue_shouldSaveAndReturnVenue_whenNameIsUnique() {
        // Given
        when(venueRepositoryPort.findByName(venue.getName())).thenReturn(Optional.empty());
        when(venueRepositoryPort.save(any(Venue.class))).thenReturn(venue);

        // When
        Venue createdVenue = venueUseCase.createVenue(venue);

        // Then
        assertNotNull(createdVenue);
        assertEquals("Test Venue", createdVenue.getName());
        verify(venueRepositoryPort, times(1)).findByName("Test Venue");
        verify(venueRepositoryPort, times(1)).save(venue);
    }

    @Test
    void createVenue_shouldThrowDuplicateResourceException_whenNameExists() {
        // Given
        when(venueRepositoryPort.findByName(venue.getName())).thenReturn(Optional.of(venue));

        // When & Then
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () -> {
            venueUseCase.createVenue(venue);
        });

        assertEquals("Venue with name 'Test Venue' already exists.", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findByName("Test Venue");
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }
}
