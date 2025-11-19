package com.EventHub.eventHub.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VenueDTO {
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private String city;
    private String state;
}
