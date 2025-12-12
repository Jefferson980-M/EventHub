package com.EventHub.eventHub.infrastructure.adapters.out.jpa.repository;

import com.EventHub.eventHub.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<VenueEntity, Long>, JpaSpecificationExecutor<VenueEntity> {
    Optional<VenueEntity> findByName(String name);
}
