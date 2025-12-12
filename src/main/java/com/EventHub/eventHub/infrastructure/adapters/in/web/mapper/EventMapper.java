package com.EventHub.eventHub.infrastructure.adapters.in.web.mapper;

import com.EventHub.eventHub.domain.model.Event;
import com.EventHub.eventHub.infrastructure.adapters.in.web.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {VenueMapper.class})
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event toDomain(EventDTO eventDTO);

    @Mapping(source = "venue", target = "venue")
    @Mapping(source = "venue.id", target = "venueId")
    EventDTO toDto(Event event);
}
