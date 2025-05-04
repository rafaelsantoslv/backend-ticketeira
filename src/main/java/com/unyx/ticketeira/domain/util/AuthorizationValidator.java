package com.unyx.ticketeira.domain.util;

import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.repository.EventRepository;

import com.unyx.ticketeira.exception.AccessDeniedException;
import com.unyx.ticketeira.exception.EventNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationValidator {
    private final EventRepository eventRepository;

    public AuthorizationValidator(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event validateEventProducer(String eventId, String producerId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event not found")
        );
        if(!event.getCreator().getId().equals(producerId)){
            throw new AccessDeniedException("Your not permission");
        }

        return event;
    }

}
