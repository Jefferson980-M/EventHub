package com.EventHub.eventHub.infrastructure.adapters.in.web.mapper;

import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.infrastructure.adapters.in.web.dto.VenueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    VenueMapper INSTANCE = Mappers.getMapper(VenueMapper.class);

    @Mapping(source = "capacity", target = "capacity")
    Venue toDomain(VenueDTO venueDTO);

    @Mapping(source = "capacity", target = "capacity")
    VenueDTO toDto(Venue venue);
}
