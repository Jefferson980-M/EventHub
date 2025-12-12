package com.EventHub.eventHub.application.usecase;

import com.EventHub.eventHub.domain.exception.DuplicateResourceException;
import com.EventHub.eventHub.domain.model.Event;
import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.domain.ports.out.EventRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventUseCaseImplTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @InjectMocks
    private EventUseCaseImpl eventUseCase;

    private Event event;
    private Venue venue;

    @BeforeEach
    void setUp() {
        venue = new Venue();
        venue.setId(1L);
        venue.setName("Test Venue");

        event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setStartDate(LocalDate.now().plusDays(1));
        event.setEndDate(LocalDate.now().plusDays(2));
        event.setVenue(venue);
        event.setStatus("AVAILABLE");
    }

    @Test
    void createEvent_shouldSaveAndReturnEvent_whenNameIsUnique() {
        // Given
        when(eventRepositoryPort.findByName(event.getName())).thenReturn(Optional.empty());
        when(eventRepositoryPort.save(any(Event.class))).thenReturn(event);

        // When
        Event createdEvent = eventUseCase.createEvent(event);

        // Then
        assertNotNull(createdEvent);
        assertEquals("Test Event", createdEvent.getName());
        assertNull(createdEvent.getId(), "ID should be null before saving to let the database generate it.");
        verify(eventRepositoryPort, times(1)).findByName("Test Event");
        verify(eventRepositoryPort, times(1)).save(event);
    }

    @Test
    void createEvent_shouldThrowDuplicateResourceException_whenNameExists() {
        // Given
        when(eventRepositoryPort.findByName(event.getName())).thenReturn(Optional.of(event));

        // When & Then
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () -> {
            eventUseCase.createEvent(event);
        });

        assertEquals("Event with name 'Test Event' already exists.", exception.getMessage());
        verify(eventRepositoryPort, times(1)).findByName("Test Event");
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }
}
