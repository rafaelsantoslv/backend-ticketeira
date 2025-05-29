package com.unyx.ticketeira.controller;

import com.unyx.ticketeira.dto.PaginetedResponse;
import com.unyx.ticketeira.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.dto.batch.BatchListAllBySector;
import com.unyx.ticketeira.dto.event.EventCreateRequest;
import com.unyx.ticketeira.dto.event.EventCreateResponse;
import com.unyx.ticketeira.dto.event.dto.EventListDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/producers/me/events")
public class EventController {

    @Autowired
    private IEventService eventService;

    @Autowired
    private ISectorService sectorService;

    @Autowired
    IBatchService batchService;


    @GetMapping
    public ResponseEntity<PaginetedResponse<EventListDTO>> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();


        return ResponseEntity.ok(eventService.listEventsByProducer(user.getId() ,page, limit));
    }

    @GetMapping("/{eventId}/dashboard")
    public void getDataDashboard(@PathVariable String eventId) {
        eventService.getDashboardInfo(eventId);
    }

    @PostMapping
    public ResponseEntity<EventCreateResponse> createEvent(
            Authentication authentication,
            @RequestBody @Valid EventCreateRequest request) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(eventService.createEvent(user.getId(),request));

    }

    @PostMapping("/{eventId}/sectors")
    public ResponseEntity<SectorCreateResponse> createSector(
            Authentication authentication,
            @PathVariable String eventId,
            @RequestBody @Valid SectorCreateRequest request) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(sectorService.createSector(eventId, user.getId(), request));
    }

    @GetMapping("/{eventId}/sectors")
    public ResponseEntity<List<SectorListAllByEventResponse>> getSectorsByEvent(
            Authentication authentication,
            @PathVariable String eventId
    ){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(sectorService.listAllSectors(eventId, user.getId()));
    }

    @PostMapping("/{eventId}/sectors/{sectorId}/batches")
    public ResponseEntity<BatchCreateResponse> createBatch(
            Authentication authentication,
            @PathVariable String eventId,
            @PathVariable String sectorId,
            @RequestBody BatchCreateRequest request
    ){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(batchService.createBatch(eventId, sectorId, user.getId(), request));
    }

    @GetMapping("/{eventId}/sectors/{sectorId}/batches")
    public ResponseEntity<List<BatchListAllBySector>> getBatchesBySector(
            Authentication authentication,
            @PathVariable String eventId,
            @PathVariable String sectorId
    ){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(batchService.listAllBatchByEvent(eventId, sectorId, user.getId()));
    }
}
