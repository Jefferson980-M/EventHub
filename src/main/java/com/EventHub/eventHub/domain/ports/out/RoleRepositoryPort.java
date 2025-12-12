package com.EventHub.eventHub.domain.ports.out;

import com.EventHub.eventHub.domain.model.Role;

import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findByName(String name);
}
