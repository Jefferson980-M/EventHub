package com.EventHub.eventHub.domain.model;

import java.util.List;

public class Venue {
    private Long id;
    private String name;
    private String city;
    private String state;
    private int capacity;
    private List<Event> events;

    public Venue() {
    }

    public Venue(Long id, String name, String city, String state, int capacity, List<Event> events) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.capacity = capacity;
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
