package com.unyx.ticketeira.application.controller;

import com.unyx.ticketeira.application.dto.event.EventRequest;
import com.unyx.ticketeira.application.dto.event.EventResponse;
import com.unyx.ticketeira.application.dto.ticket.TicketRequest;
import com.unyx.ticketeira.application.dto.ticket.TicketResponse;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.Ticket;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody @Valid EventRequest request,
            @AuthenticationPrincipal User producer) {
        System.out.println("Requisição recebida para criar evento: " + request);
        System.out.println("Usuário autenticado: " + producer);
        // Cria o objeto Event com os dados do request
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setNameLocale(request.getNameLocale());
        event.setAddress(request.getAddress());
        event.setClassification(request.getClassification());
        event.setCategory(request.getCategory());
        event.setCoverImageUrl(request.getCoverImageUrl());
        event.setMainImageUrl(request.getMainImageUrl());
        event.setEventDateTime(request.getEventDateTime());

        // Salva o evento no banco de dados
        Event createdEvent = eventService.createEvent(event, producer);

        // Cria a resposta
        EventResponse response = new EventResponse(
                createdEvent.getId(),
                createdEvent.getTitle(),
                createdEvent.getDescription(),
                createdEvent.getNameLocale(),
                createdEvent.getAddress(),
                createdEvent.getClassification(),
                createdEvent.getCategory(),
                createdEvent.getCoverImageUrl(),
                createdEvent.getMainImageUrl(),
                createdEvent.getEventDateTime(),
                createdEvent.getProducerId().getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        List<EventResponse> responses = events.stream()
                .map(event -> new EventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getNameLocale(),
                        event.getAddress(),
                        event.getClassification(),
                        event.getCategory(),
                        event.getCoverImageUrl(),
                        event.getMainImageUrl(),
                        event.getEventDateTime(),
                        event.getProducerId().getEmail()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long eventId,
            @RequestBody @Valid EventRequest request,
            @AuthenticationPrincipal User producer) {

        // Cria o objeto Event com os dados do request
        Event updatedEvent = new Event();
        updatedEvent.setTitle(request.getTitle());
        updatedEvent.setDescription(request.getDescription());
        updatedEvent.setNameLocale(request.getNameLocale());
        updatedEvent.setAddress(request.getAddress());
        updatedEvent.setClassification(request.getClassification());
        updatedEvent.setCategory(request.getCategory());
        updatedEvent.setCoverImageUrl(request.getCoverImageUrl());
        updatedEvent.setMainImageUrl(request.getMainImageUrl());
        updatedEvent.setEventDateTime(request.getEventDateTime());

        // Atualiza o evento no banco de dados
        Event event = eventService.updateEvent(eventId, updatedEvent, producer);

        // Cria a resposta
        EventResponse response = new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getNameLocale(),
                event.getAddress(),
                event.getClassification(),
                event.getCategory(),
                event.getCoverImageUrl(),
                event.getMainImageUrl(),
                event.getEventDateTime(),
                event.getProducerId().getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{eventId}/ticket")
    public ResponseEntity<TicketResponse> createTicket(
            @PathVariable Long eventId,
            @RequestBody @Valid TicketRequest request,
            @AuthenticationPrincipal User producer) {

        // Cria o objeto Ticket com os dados do request
        Ticket ticket = new Ticket();
        ticket.setBatchName(request.getBatchName());
        ticket.setPrice(request.getPrice());
        ticket.setQuantity(request.getQuantity());
        ticket.setIsActive(request.getIsActive());

        // Salva o ticket no banco de dados
        Ticket createdTicket = eventService.createTicket(eventId, ticket, producer);
        TicketResponse response = new TicketResponse(
                createdTicket.getId(),
                createdTicket.getBatchName(),
                createdTicket.getPrice(),
                createdTicket.getQuantity(),
                createdTicket.getIsActive(),
                new EventResponse(
                        createdTicket.getEvent().getId(),
                        createdTicket.getEvent().getTitle(),
                        createdTicket.getEvent().getDescription(),
                        createdTicket.getEvent().getNameLocale(),
                        createdTicket.getEvent().getAddress(),
                        createdTicket.getEvent().getClassification(),
                        createdTicket.getEvent().getCategory(),
                        createdTicket.getEvent().getCoverImageUrl(),
                        createdTicket.getEvent().getMainImageUrl(),
                        createdTicket.getEvent().getEventDateTime(),
                        createdTicket.getEvent().getProducerId().getEmail()
                )
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{eventId}/ticket/{ticketId}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long eventId,
            @PathVariable Long ticketId,
            @RequestBody @Valid TicketRequest request,
            @AuthenticationPrincipal User producer) {

        // Cria o objeto Ticket com os dados do request
        Ticket updatedTicket = new Ticket();
        updatedTicket.setBatchName(request.getBatchName());
        updatedTicket.setPrice(request.getPrice());
        updatedTicket.setQuantity(request.getQuantity());
        updatedTicket.setIsActive(request.getIsActive());

        // Atualiza o ticket no banco de dados
        Ticket ticket = eventService.updateTicket(ticketId, updatedTicket, producer);

        // Cria o TicketResponse
        TicketResponse response = new TicketResponse(
                ticket.getId(),
                ticket.getBatchName(),
                ticket.getPrice(),
                ticket.getQuantity(),
                ticket.getIsActive(),
                new EventResponse(
                        ticket.getEvent().getId(),
                        ticket.getEvent().getTitle(),
                        ticket.getEvent().getDescription(),
                        ticket.getEvent().getNameLocale(),
                        ticket.getEvent().getAddress(),
                        ticket.getEvent().getClassification(),
                        ticket.getEvent().getCategory(),
                        ticket.getEvent().getCoverImageUrl(),
                        ticket.getEvent().getMainImageUrl(),
                        ticket.getEvent().getEventDateTime(),
                        ticket.getEvent().getProducerId().getEmail()
                )
        );

        return ResponseEntity.ok(response);
    }
}
