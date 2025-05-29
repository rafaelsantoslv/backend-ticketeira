package com.unyx.ticketeira.controller;

import com.unyx.ticketeira.dto.PaginetedResponse;
import com.unyx.ticketeira.dto.event.EventDetailsResponse;
import com.unyx.ticketeira.dto.event.dto.EventDTO;
import com.unyx.ticketeira.service.Interface.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class PublicEventController {
    @Autowired
    private IEventService eventService;

    @GetMapping()
    public ResponseEntity<PaginetedResponse<EventDTO>> listAllPublishedEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(eventService.listAllEventsPublished(page, limit));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsResponse> getEventDetails(
            @PathVariable String eventId
    ) {
        return ResponseEntity.ok(eventService.getEventDetails(eventId));
    }
}
