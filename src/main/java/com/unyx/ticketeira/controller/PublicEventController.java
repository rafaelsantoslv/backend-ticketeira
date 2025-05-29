package com.unyx.ticketeira.controller;

import com.unyx.ticketeira.dto.PaginetedResponse;
import com.unyx.ticketeira.dto.event.dto.EventListDTO;
import com.unyx.ticketeira.service.Interface.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class PublicEventController {
    @Autowired
    private IEventService eventService;

    @GetMapping()
    public ResponseEntity<PaginetedResponse<EventListDTO>> listAllPublishedEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(eventService.listAllEventsPublished(page, limit));
    }
}
