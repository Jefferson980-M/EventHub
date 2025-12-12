package com.EventHub.eventHub.infrastructure.adapters.in.web.controller;

import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.domain.ports.in.VenueUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VenueUseCase venueUseCase;

    @Autowired
    private ObjectMapper objectMapper;

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
    @WithMockUser(roles = "ADMIN")
    void createVenue_shouldReturnCreated_whenUserIsAdmin() throws Exception {
        // Given
        when(venueUseCase.createVenue(any(Venue.class))).thenReturn(venue);

        String venueJson = """
                {
                    "name": "Test Venue",
                    "city": "Test City",
                    "state": "Test State",
                    "capacity": 100
                }
                """;

        // When & Then
        mockMvc.perform(post("/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(venueJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Venue"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createVenue_shouldReturnForbidden_whenUserIsUser() throws Exception {
        // Given
        String venueJson = """
                {
                    "name": "Test Venue",
                    "city": "Test City",
                    "state": "Test State",
                    "capacity": 100
                }
                """;

        // When & Then
        mockMvc.perform(post("/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(venueJson))
                .andExpect(status().isForbidden());
    }
}
