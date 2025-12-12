package com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter;

import com.EventHub.eventHub.domain.model.PageResponse;
import com.EventHub.eventHub.domain.model.Venue;
import com.EventHub.eventHub.domain.ports.out.VenueRepositoryPort;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.VenueEntity;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper.VenueJpaMapper;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.VenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VenueJpaAdapter implements VenueRepositoryPort {

    private final VenueRepository venueRepository;
    private final VenueJpaMapper venueJpaMapper;

    public VenueJpaAdapter(VenueRepository venueRepository, VenueJpaMapper venueJpaMapper) {
        this.venueRepository = venueRepository;
        this.venueJpaMapper = venueJpaMapper;
    }

    @Override
    public Venue save(Venue venue) {
        VenueEntity venueEntity = venueJpaMapper.toEntity(venue);
        VenueEntity savedVenueEntity = venueRepository.save(venueEntity);
        return venueJpaMapper.toDomain(savedVenueEntity);
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return venueRepository.findById(id).map(venueJpaMapper::toDomain);
    }

    @Override
    public Optional<Venue> findByName(String name) {
        return venueRepository.findByName(name).map(venueJpaMapper::toDomain);
    }

    @Override
    public List<Venue> findAll() {
        return venueRepository.findAll().stream()
                .map(venueJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<Venue> findAll(Pageable pageable, String city, String state, Integer capacity) {
        Specification<VenueEntity> spec = (root, query, cb) -> cb.conjunction();

        if (city != null && !city.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
        }
        if (state != null && !state.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("state")), "%" + state.toLowerCase() + "%"));
        }
        if (capacity != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("capacity"), capacity));
        }

        Page<VenueEntity> page = venueRepository.findAll(spec, pageable);
        return new PageResponse<>(
                page.getContent().stream().map(venueJpaMapper::toDomain).collect(Collectors.toList()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public void deleteById(Long id) {
        venueRepository.deleteById(id);
    }
}
