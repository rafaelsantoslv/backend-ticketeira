package com.unyx.ticketeira.application.controller;

import com.unyx.ticketeira.application.dto.event.EventCreateRequest;
import com.unyx.ticketeira.application.dto.event.EventCreateResponse;
import com.unyx.ticketeira.application.dto.event.EventsResponse;
import com.unyx.ticketeira.application.usecases.event.AddEventProducerUseCase;
import com.unyx.ticketeira.application.usecases.event.GetEventsProducerUseCase;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.domain.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producers")
public class EventController {
    private final GetEventsProducerUseCase getEventsProducerUseCase;
    private final AddEventProducerUseCase addEventProducerUseCase;

    public EventController(GetEventsProducerUseCase getEventsProducerUseCase, AddEventProducerUseCase addEventProducerUseCase) {
        this.getEventsProducerUseCase = getEventsProducerUseCase;
        this.addEventProducerUseCase = addEventProducerUseCase;
    }

    @GetMapping("/me/events")
    public ResponseEntity<EventsResponse> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();


        return ResponseEntity.ok(getEventsProducerUseCase.execute(user.getId() ,page, limit));
    }

    @PostMapping("/me/events")
    public ResponseEntity<EventCreateResponse> createEvent(Authentication authentication, @RequestBody @Valid EventCreateRequest request) {
        System.out.println(request);
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(addEventProducerUseCase.execute(user.getId(),request));

    }
}
