package com.unyx.ticketeira.controller;

import com.unyx.ticketeira.dto.PaginatedResponse;
import com.unyx.ticketeira.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.dto.batch.BatchListAllBySector;
import com.unyx.ticketeira.dto.event.EventCreateRequest;
import com.unyx.ticketeira.dto.event.EventCreateResponse;
import com.unyx.ticketeira.dto.event.EventDetailsResponse;
import com.unyx.ticketeira.dto.event.dto.EventDTO;
import com.unyx.ticketeira.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.dto.sector.SectorCreateResponse;
import com.unyx.ticketeira.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.service.Interface.IBatchService;
import com.unyx.ticketeira.service.Interface.IEventService;
import com.unyx.ticketeira.service.Interface.ISectorService;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private IEventService eventService;

    @GetMapping()
    public ResponseEntity<PaginatedResponse<EventDTO>> listAllPublishedEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(eventService.getEventsPublished(page, limit));
    }
//
//    @GetMapping("/{eventId}")
//    public ResponseEntity<EventDetailsResponse> getEventDetails(
//            @PathVariable String eventId
//    ) {
//        return ResponseEntity.ok(eventService.getEventDetails(eventId));
//    }


}
