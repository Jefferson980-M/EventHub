package com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper;

import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VenueJpaMapper {

    VenueJpaMapper INSTANCE = Mappers.getMapper(VenueJpaMapper.class);

    @Mapping(target = "events", ignore = true)
    @Mapping(source = "capacity", target = "capacity")
    Venue toDomain(VenueEntity venueEntity);

    @Mapping(target = "events", ignore = true)
    @Mapping(source = "capacity", target = "capacity")
    VenueEntity toEntity(Venue venue);
}
