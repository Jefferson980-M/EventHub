package com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper;

import com.EventHub.eventHub.domain.model.User;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleJpaMapper.class})
public interface UserJpaMapper {

    @Mapping(source = "roles", target = "roles")
    User toDomain(UserEntity userEntity);
    UserEntity toEntity(User user);
}
