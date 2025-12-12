package com.EventHub.eventHub.infrastructure.adapters.out.jpa.adapter;

import com.EventHub.eventHub.domain.model.Event;
import com.EventHub.eventHub.domain.model.PageResponse;
import com.EventHub.eventHub.domain.ports.out.EventRepositoryPort;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.EventEntity;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.mapper.EventJpaMapper;
import com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EventJpaAdapter implements EventRepositoryPort {

    private final EventRepository eventRepository;
    private final EventJpaMapper eventJpaMapper;

    public EventJpaAdapter(EventRepository eventRepository, EventJpaMapper eventJpaMapper) {
        this.eventRepository = eventRepository;
        this.eventJpaMapper = eventJpaMapper;
    }

    @Override
    public Event save(Event event) {
        EventEntity eventEntity = eventJpaMapper.toEntity(event);
        EventEntity savedEventEntity = eventRepository.save(eventEntity);
        return eventJpaMapper.toDomain(savedEventEntity);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id).map(eventJpaMapper::toDomain);
    }

    @Override
    public PageResponse<Event> findAll(Pageable pageable, String city, String category, LocalDate startDate, LocalDate endDate, String status) {
        Specification<EventEntity> spec = (root, query, cb) -> cb.conjunction();

        if (city != null && !city.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("venue").get("city")), "%" + city.toLowerCase() + "%"));
        }
        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("category")), category.toLowerCase()));
        }
        if (startDate != null && endDate != null) {
            spec = spec.and((root, query, cb) -> cb.between(root.get("startDate"), startDate, endDate));
        } else if (startDate != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
        } else if (endDate != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("startDate"), endDate));
        }
        if (status != null && !status.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("status")), status.toLowerCase()));
        }

        Page<EventEntity> page = eventRepository.findAll(spec, pageable);
        return new PageResponse<>(
                page.getContent().stream().map(eventJpaMapper::toDomain).collect(Collectors.toList()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Optional<Event> findByName(String name) {
        return eventRepository.findByName(name).map(eventJpaMapper::toDomain);
    }
}
