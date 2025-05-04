package com.unyx.ticketeira.application.controller;

import com.unyx.ticketeira.application.dto.event.EventCreateRequest;
import com.unyx.ticketeira.application.dto.event.EventCreateResponse;
import com.unyx.ticketeira.application.dto.event.EventsResponse;
import com.unyx.ticketeira.application.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.application.dto.sector.SectorCreateResponse;
import com.unyx.ticketeira.application.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.application.usecases.event.AddEventProducerUseCase;
import com.unyx.ticketeira.application.usecases.event.GetEventsProducerUseCase;
import com.unyx.ticketeira.application.usecases.sector.AddSectorUseCase;
import com.unyx.ticketeira.application.usecases.sector.GetSectorsUseCase;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.domain.util.SecurityUtils;
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

    public EventController(
            GetEventsProducerUseCase getEventsProducerUseCase,
            AddEventProducerUseCase addEventProducerUseCase,
            AddSectorUseCase addSectorUseCase,
            GetSectorsUseCase getSectorsUseCase
    ) {
        this.getEventsProducerUseCase = getEventsProducerUseCase;
        this.addEventProducerUseCase = addEventProducerUseCase;
        this.addSectorUseCase = addSectorUseCase;
        this.getSectorsUseCase = getSectorsUseCase;
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

    @PostMapping("/me/events/{eventId}/sectors")
    public ResponseEntity<SectorCreateResponse> createSector(Authentication authentication, @PathVariable String eventId, @RequestBody @Valid SectorCreateRequest request) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(addSectorUseCase.execute(eventId, user.getId(), request));
    }

    @GetMapping("/me/events/{eventId}/sectors")
    public ResponseEntity<List<SectorListAllByEventResponse>> getSectorsByEvent(Authentication authentication, @PathVariable String eventId){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(getSectorsUseCase.execute(eventId, user.getId()));
    }
}
