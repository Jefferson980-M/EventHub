package com.EventHub.eventHub.infrastructure.adapters.in.web.dto;

import com.EventHub.eventHub.domain.validation.groups.Create;
import com.EventHub.eventHub.domain.validation.groups.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VenueDTO {

    @NotNull(groups = Update.class, message = "{venue.id.notnull}")
    @Null(groups = Create.class, message = "{venue.id.null}")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "The database generated venue ID")
    private Long id;

    @NotBlank(message = "{venue.name.notblank}")
    @Size(min = 3, max = 100, message = "{venue.name.size}")
    private String name;

    @NotBlank(message = "{venue.city.notblank}")
    private String city;

    @NotBlank(message = "{venue.state.notblank}")
    private String state;

    @Min(value = 1, message = "{venue.capacity.min}")
    private int capacity;
}
