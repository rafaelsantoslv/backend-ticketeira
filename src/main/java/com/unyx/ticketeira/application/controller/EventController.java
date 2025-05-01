package com.unyx.ticketeira.application.controller;

import com.unyx.ticketeira.application.dto.event.EventCreateRequest;
import com.unyx.ticketeira.application.dto.event.EventCreateResponse;
import com.unyx.ticketeira.application.dto.event.EventListAllByProducerResponse;
import com.unyx.ticketeira.application.dto.event.EventsResponse;
import com.unyx.ticketeira.application.usecases.event.AddEventUseCase;
import com.unyx.ticketeira.application.usecases.event.GetEventsUseCase;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producers")
public class EventController {
    private final GetEventsUseCase getEventsUseCase;
    private final AddEventUseCase addEventUseCase;

    public EventController(GetEventsUseCase getEventsUseCase, AddEventUseCase addEventUseCase) {
        this.getEventsUseCase = getEventsUseCase;
        this.addEventUseCase = addEventUseCase;
    }

    @GetMapping("/me/events")
    public ResponseEntity<EventsResponse> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();


        return ResponseEntity.ok(getEventsUseCase.execute(user.getId() ,page, limit));
    }

    @PostMapping("/me/events")
    public ResponseEntity<EventCreateResponse> createEvent(Authentication authentication, @RequestBody @Valid EventCreateRequest request) {
        System.out.println(request);
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(addEventUseCase.execute(user.getId(),request));

    }
}
