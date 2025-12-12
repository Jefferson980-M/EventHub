package com.EventHub.eventHub.infrastructure.adapters.in.web.dto;

import com.EventHub.eventHub.infrastructure.adapters.in.web.validation.DateRange;
import com.EventHub.eventHub.domain.validation.groups.Create;
import com.EventHub.eventHub.domain.validation.groups.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@DateRange(groups = {Create.class, Update.class})
public class EventDTO {

    @NotNull(groups = Update.class, message = "{event.id.notnull}")
    @Null(groups = Create.class, message = "{event.id.null}")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "The database generated event ID")
    private Long id;

    @NotBlank(message = "{event.name.notblank}")
    @Size(min = 3, max = 100, message = "{event.name.size}")
    private String name;

    @NotBlank(message = "{event.category.notblank}")
    private String category;

    @NotNull(message = "{event.startDate.notnull}")
    @Future(message = "{event.startDate.future}")
    private LocalDate startDate;

    @NotNull(message = "{event.endDate.notnull}")
    private LocalDate endDate;

    @NotNull(message = "{event.venueId.notnull}")
    @Schema(description = "The ID of the venue where the event will take place.")
    private Long venueId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "The venue details. This is only included in responses.")
    private VenueDTO venue;

    @NotBlank(message = "{event.status.notblank}")
    private String status;
}
