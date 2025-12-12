package com.EventHub.eventHub.infrastructure.config;

import com.EventHub.eventHub.domain.ports.out.EventRepositoryPort;
import com.EventHub.eventHub.domain.ports.out.RoleRepositoryPort;
import com.EventHub.eventHub.domain.ports.out.UserRepositoryPort;
import com.EventHub.eventHub.domain.ports.out.VenueRepositoryPort;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter.EventJpaAdapter;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter.RoleJpaAdapter;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter.UserJpaAdapter;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter.VenueJpaAdapter;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper.EventJpaMapper;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper.RoleJpaMapper;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper.UserJpaMapper;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper.VenueJpaMapper;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.EventRepository;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.RoleRepository;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.UserRepository;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.VenueRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public EventRepositoryPort eventRepositoryPort(EventRepository eventRepository, EventJpaMapper eventJpaMapper) {
        return new EventJpaAdapter(eventRepository, eventJpaMapper);
    }

    @Bean
    public VenueRepositoryPort venueRepositoryPort(VenueRepository venueRepository, VenueJpaMapper venueJpaMapper) {
        return new VenueJpaAdapter(venueRepository, venueJpaMapper);
    }

    @Bean
    public UserRepositoryPort userRepositoryPort(UserRepository userRepository, RoleRepository roleRepository, UserJpaMapper userJpaMapper) {
        return new UserJpaAdapter(userRepository, roleRepository, userJpaMapper);
    }

    @Bean
    public RoleRepositoryPort roleRepositoryPort(RoleRepository roleRepository, RoleJpaMapper roleJpaMapper) {
        return new RoleJpaAdapter(roleRepository, roleJpaMapper);
    }
}
