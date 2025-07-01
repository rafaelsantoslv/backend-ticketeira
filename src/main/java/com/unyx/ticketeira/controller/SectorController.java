package com.unyx.ticketeira.controller;

import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.dto.sector.SectorCreateResponse;
import com.unyx.ticketeira.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.service.Interface.ISectorService;
import com.unyx.ticketeira.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/producers/me/events/{eventId}/sectors")
public class SectorController {

    private final ISectorService sectorService;

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping()
    public ResponseEntity<SectorCreateResponse> createSector(
            Authentication authentication,
            @PathVariable String eventId,
            @RequestBody @Valid SectorCreateRequest request) {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(sectorService.createSector(eventId, user.getId(), request));
    }

//    @PreAuthorize("hasRole('PRODUCER')")
//    @GetMapping()
//    public ResponseEntity<List<SectorListAllByEventResponse>> getSectorsByEvent(
//            Authentication authentication,
//            @PathVariable String eventId
//    ){
//        AuthenticatedUser user = SecurityUtils.getCurrentUser();
//        return ResponseEntity.ok(sectorService.getAllSectorsByEventId(eventId, user.getId()));
//    }
}
