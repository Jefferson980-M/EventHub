package com.EventHub.eventHub.service;

import com.EventHub.eventHub.dto.VenueDTO;
import com.EventHub.eventHub.model.Venue;
import com.EventHub.eventHub.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public VenueDTO createVenue(VenueDTO venueDTO) {
        Venue venue = new Venue();
        venue.setName(venueDTO.getName());
        venue.setCity(venueDTO.getCity());
        venue.setState(venueDTO.getState());
        venue = venueRepository.save(venue);
        venueDTO.setId(venue.getId());
        return venueDTO;
    }

    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll().stream().map(venue -> {
            VenueDTO dto = new VenueDTO();
            dto.setId(venue.getId());
            dto.setName(venue.getName());
            dto.setCity(venue.getCity());
            dto.setState(venue.getState());
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<VenueDTO> getVenueById(Long id) {
        return venueRepository.findById(id).map(venue -> {
            VenueDTO dto = new VenueDTO();
            dto.setId(venue.getId());
            dto.setName(venue.getName());
            dto.setCity(venue.getCity());
            dto.setState(venue.getState());
            return dto;
        });
    }

    public VenueDTO updateVenue(Long id, VenueDTO venueDTO) {
        Venue venue = new Venue();
        venue.setId(id);
        venue.setName(venueDTO.getName());
        venue.setCity(venueDTO.getCity());
        venue.setState(venueDTO.getState());
        venue = venueRepository.save(venue);
        venueDTO.setId(venue.getId());
        return venueDTO;
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
}
