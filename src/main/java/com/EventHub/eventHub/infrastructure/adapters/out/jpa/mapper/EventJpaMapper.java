package com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper;

import com.EventHub.eventHub.domain.model.Event;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {VenueJpaMapper.class})
public interface EventJpaMapper {

    EventJpaMapper INSTANCE = Mappers.getMapper(EventJpaMapper.class);

    @Mapping(source = "venue", target = "venue")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "endDate", target = "endDate")
    Event toDomain(EventEntity eventEntity);

    @Mapping(source = "venue", target = "venue")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "endDate", target = "endDate")
    EventEntity toEntity(Event event);
}
