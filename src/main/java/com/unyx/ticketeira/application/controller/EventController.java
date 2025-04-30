package com.unyx.ticketeira.application.controller;

import com.unyx.ticketeira.application.usecases.event.GetEventsUseCase;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/producers")
public class EventController {
    private final GetEventsUseCase getEventsUseCase;

    public EventController(GetEventsUseCase getEventsUseCase) {
        this.getEventsUseCase = getEventsUseCase;
    }

    @GetMapping("/me/events")
    public ResponseEntity<List<Event>> getEvents(@RequestParam int page, @RequestParam int items) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        System.out.println("id é: " + user.getId());
        return ResponseEntity.ok(getEventsUseCase.execute(user.getId() ,page, items));
    }
}
