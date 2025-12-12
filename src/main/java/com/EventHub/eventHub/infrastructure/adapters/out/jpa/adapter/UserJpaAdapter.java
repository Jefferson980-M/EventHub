package com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter;

import com.EventHub.eventHub.domain.model.Role;
import com.EventHub.eventHub.domain.model.User;
import com.EventHub.eventHub.domain.ports.out.UserRepositoryPort;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.RoleEntity;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.UserEntity;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper.UserJpaMapper;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.RoleRepository;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserJpaMapper userJpaMapper;

    public UserJpaAdapter(UserRepository userRepository, RoleRepository roleRepository, UserJpaMapper userJpaMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userJpaMapper = userJpaMapper;
    }

    @Override
    public User save(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Set<Role> assignedRoles = new HashSet<>();
            long userCount = userRepository.count();
            System.out.println("DEBUG: Current user count: " + userCount);

            if (userCount == 0) {
                // First user, assign ROLE_ADMIN
                assignedRoles.add(new Role(null, "ROLE_ADMIN"));
                System.out.println("DEBUG: Assigning ROLE_ADMIN");
            } else {
                // Subsequent users, assign ROLE_USER
                assignedRoles.add(new Role(null, "ROLE_USER"));
                System.out.println("DEBUG: Assigning ROLE_USER");
            }
            user.setRoles(assignedRoles);
        } else {
            System.out.println("DEBUG: User already has roles, respecting existing roles: " + user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")));
        }


        UserEntity userEntity = userJpaMapper.toEntity(user);

        Set<RoleEntity> managedRoles = user.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                .collect(Collectors.toSet());
        userEntity.setRoles(managedRoles);

        UserEntity savedUser = userRepository.save(userEntity);
        return userJpaMapper.toDomain(savedUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userJpaMapper::toDomain);
    }
}
