package com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository;

import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {
    Optional<EventEntity> findByName(String name);

    @Override
    @EntityGraph(attributePaths = {"venue"})
    Page<EventEntity> findAll(Specification<EventEntity> spec, Pageable pageable);

    @Query("SELECT e FROM EventEntity e JOIN FETCH e.venue WHERE e.venue.id = :venueId")
    List<EventEntity> findByVenueId(@Param("venueId") Long venueId);
}
