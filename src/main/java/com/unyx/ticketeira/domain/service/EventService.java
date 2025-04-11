package com.unyx.ticketeira.domain.service;

import com.unyx.ticketeira.application.dto.event.EventResponse;
import com.unyx.ticketeira.application.dto.ticket.TicketResponse;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.Ticket;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.EventRepository;
import com.unyx.ticketeira.domain.repository.TicketRepository;
import com.unyx.ticketeira.exception.EventNotFoundException;
import com.unyx.ticketeira.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    public EventService(EventRepository eventRepository, TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Long eventId, Ticket ticket, User producer) {
        // Busca o evento pelo ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + eventId + " not found"));

        // Verifica se o producer autenticado é o criador do evento
        if (!event.getProducerId().getId().equals(producer.getId())) {
            throw new UnauthorizedException("You are not authorized to create tickets for this event.");
        }

        // Associa o ticket ao evento
        ticket.setEvent(event);
        return ticketRepository.save(ticket);

    }

    public Event updateEvent(Long eventId, Event updatedEvent, User producer) {
        // Busca o evento pelo ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + eventId + " not found"));

        // Verifica se o producer autenticado é o criador do evento
        if (!event.getProducerId().getId().equals(producer.getId())) {
            throw new UnauthorizedException("You are not authorized to update this event.");
        }

        // Atualiza os dados do evento
        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setNameLocale(updatedEvent.getNameLocale());
        event.setAddress(updatedEvent.getAddress());
        event.setClassification(updatedEvent.getClassification());
        event.setCategory(updatedEvent.getCategory());
        event.setCoverImageUrl(updatedEvent.getCoverImageUrl());
        event.setMainImageUrl(updatedEvent.getMainImageUrl());
        event.setEventDateTime(updatedEvent.getEventDateTime());

        return eventRepository.save(event);
    }

    public Event createEvent(Event event, User producer) {
        // Associa o evento ao produtor autenticado
        event.setProducerId(producer);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Ticket updateTicket(Long ticketId, Ticket updatedTicket, User producer) {
        // Busca o ticket pelo ID
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EventNotFoundException("Ticket with ID " + ticketId + " not found"));

        // Verifica se o producer autenticado é o criador do evento associado ao ticket
        if (!ticket.getEvent().getProducerId().getId().equals(producer.getId())) {
            throw new UnauthorizedException("You are not authorized to update this ticket.");
        }

        // Atualiza os dados do ticket
        ticket.setBatchName(updatedTicket.getBatchName());
        ticket.setPrice(updatedTicket.getPrice());
        ticket.setQuantity(updatedTicket.getQuantity());
        ticket.setIsActive(updatedTicket.getIsActive());

        return ticketRepository.save(ticket);
    }
}
