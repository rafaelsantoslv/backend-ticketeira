package com.unyx.ticketeira.controller;

import com.unyx.ticketeira.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.dto.batch.BatchListAllBySector;
import com.unyx.ticketeira.dto.event.EventCreateRequest;
import com.unyx.ticketeira.dto.event.EventCreateResponse;
import com.unyx.ticketeira.dto.event.EventsResponse;
import com.unyx.ticketeira.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.dto.sector.SectorCreateResponse;
import com.unyx.ticketeira.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.usecases.batch.AddBatchUseCase;
import com.unyx.ticketeira.usecases.batch.GetBatchesUseCase;
import com.unyx.ticketeira.usecases.event.AddEventProducerUseCase;
import com.unyx.ticketeira.usecases.event.GetEventsProducerUseCase;
import com.unyx.ticketeira.usecases.sector.AddSectorUseCase;
import com.unyx.ticketeira.usecases.sector.GetSectorsUseCase;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producers")
public class EventController {
    private final GetEventsProducerUseCase getEventsProducerUseCase;
    private final AddEventProducerUseCase addEventProducerUseCase;
    private final AddSectorUseCase addSectorUseCase;
    private final GetSectorsUseCase getSectorsUseCase;
    private final AddBatchUseCase addBatchUseCase;
    private final GetBatchesUseCase getBatchesUseCase;

    public EventController(
            GetEventsProducerUseCase getEventsProducerUseCase,
            AddEventProducerUseCase addEventProducerUseCase,
            AddSectorUseCase addSectorUseCase,
            GetSectorsUseCase getSectorsUseCase,
            AddBatchUseCase addBatchUseCase,
            GetBatchesUseCase getBatchesUseCase
    ) {
        this.getEventsProducerUseCase = getEventsProducerUseCase;
        this.addEventProducerUseCase = addEventProducerUseCase;
        this.addSectorUseCase = addSectorUseCase;
        this.getSectorsUseCase = getSectorsUseCase;
        this.addBatchUseCase = addBatchUseCase;
        this.getBatchesUseCase = getBatchesUseCase;
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
    public ResponseEntity<EventCreateResponse> createEvent(
            Authentication authentication,
            @RequestBody @Valid EventCreateRequest request) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(addEventProducerUseCase.execute(user.getId(),request));

    }

    @PostMapping("/me/events/{eventId}/sectors")
    public ResponseEntity<SectorCreateResponse> createSector(
            Authentication authentication,
            @PathVariable String eventId,
            @RequestBody @Valid SectorCreateRequest request) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(addSectorUseCase.execute(eventId, user.getId(), request));
    }

    @GetMapping("/me/events/{eventId}/sectors")
    public ResponseEntity<List<SectorListAllByEventResponse>> getSectorsByEvent(
            Authentication authentication,
            @PathVariable String eventId
    ){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(getSectorsUseCase.execute(eventId, user.getId()));
    }

    @PostMapping("/me/events/{eventId}/sectors/{sectorId}/batches")
    public ResponseEntity<BatchCreateResponse> createBatch(
            Authentication authentication,
            @PathVariable String eventId,
            @PathVariable String sectorId,
            @RequestBody BatchCreateRequest request
    ){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(addBatchUseCase.execute(eventId, sectorId, user.getId(), request));
    }

    @GetMapping("/me/events/{eventId}/sectors/{sectorId}/batches")
    public ResponseEntity<List<BatchListAllBySector>> getBatchesBySector(
            Authentication authentication,
            @PathVariable String eventId,
            @PathVariable String sectorId
    ){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(getBatchesUseCase.execute(eventId, sectorId, user.getId()));
    }
}
