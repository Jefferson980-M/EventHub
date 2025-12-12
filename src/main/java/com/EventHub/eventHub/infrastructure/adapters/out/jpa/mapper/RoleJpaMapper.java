package com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper;

import com.EventHub.eventHub.domain.model.Role;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleJpaMapper {
    Role toDomain(RoleEntity roleEntity);
    RoleEntity toEntity(Role role);
}
