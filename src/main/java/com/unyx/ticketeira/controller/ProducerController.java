package com.unyx.ticketeira.controller;

import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.dto.PaginatedResponse;
import com.unyx.ticketeira.dto.event.EventCreateRequest;
import com.unyx.ticketeira.dto.event.EventCreateResponse;
import com.unyx.ticketeira.dto.event.dto.EventDTO;
import com.unyx.ticketeira.service.Interface.IEventService;
import com.unyx.ticketeira.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/producers/me")
public class ProducerController {

    private final IEventService eventService;

    @PreAuthorize("hasRole('PRODUCER')")
    @GetMapping("/events")
    public ResponseEntity<PaginatedResponse<EventDTO>> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();


        return ResponseEntity.ok(eventService.listEventsByProducer(user.getId() ,page, limit));
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping("/events")
    public ResponseEntity<EventCreateResponse> createEvent(
            Authentication authentication,
            @RequestBody @Valid EventCreateRequest request) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(eventService.createEvent(user.getId(),request));

    }
}
