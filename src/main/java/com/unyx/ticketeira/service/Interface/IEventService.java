package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.model.Event;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    Event create(Event event);
    Event update(String id, Event event);
    void delete(String id);
    Optional<Event> findById(String id);
    List<Event> findAll();
    List<Event> findUpcoming();
    List<Event> findFeatured();
    Event publish(String id);
    Event unpublish(String id);
    void addCategory(String eventId, String categoryId);
    void removeCategory(String eventId, String categoryId);
    List<Event> findByLocation(String city, String state);
}
