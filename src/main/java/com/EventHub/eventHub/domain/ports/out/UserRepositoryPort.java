package com.EventHub.eventHub.domain.ports.out;

import com.EventHub.eventHub.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByUsername(String username);
}
