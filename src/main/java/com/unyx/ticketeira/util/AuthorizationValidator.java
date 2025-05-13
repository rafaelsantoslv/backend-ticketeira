package com.unyx.ticketeira.util;

import com.unyx.ticketeira.model.Event;
import com.unyx.ticketeira.model.Sector;
import com.unyx.ticketeira.repository.EventRepository;

import com.unyx.ticketeira.repository.SectorRepository;
import com.unyx.ticketeira.exception.AccessDeniedException;
import com.unyx.ticketeira.exception.EventNotFoundException;
import com.unyx.ticketeira.exception.SectorNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationValidator {
    private final EventRepository eventRepository;
    private final SectorRepository sectorRepository;

    public AuthorizationValidator(EventRepository eventRepository, SectorRepository sectorRepository) {
        this.eventRepository = eventRepository;
        this.sectorRepository = sectorRepository;
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

    public Sector validateEventSector(String eventId, String sectorId) {
        Sector sector = sectorRepository.findById(sectorId).orElseThrow(
                () -> new SectorNotFoundException("Sector not found")
        );
        if(!sector.getEvent().getId().equals(eventId)){
            throw new AccessDeniedException("Your not permission");
        }

        return sector;
    }

}
