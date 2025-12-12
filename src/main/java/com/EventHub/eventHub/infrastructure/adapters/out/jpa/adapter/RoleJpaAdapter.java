package com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter;

import com.EventHub.eventHub.domain.model.Role;
import com.EventHub.eventHub.domain.ports.out.RoleRepositoryPort;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper.RoleJpaMapper;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleJpaAdapter implements RoleRepositoryPort {

    private final RoleRepository roleRepository;
    private final RoleJpaMapper roleJpaMapper;

    public RoleJpaAdapter(RoleRepository roleRepository, RoleJpaMapper roleJpaMapper) {
        this.roleRepository = roleRepository;
        this.roleJpaMapper = roleJpaMapper;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name).map(roleJpaMapper::toDomain);
    }
}
