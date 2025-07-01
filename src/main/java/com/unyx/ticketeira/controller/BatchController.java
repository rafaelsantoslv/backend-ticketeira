package com.unyx.ticketeira.controller;

import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.dto.batch.BatchListAllBySector;
import com.unyx.ticketeira.service.Interface.IBatchService;
import com.unyx.ticketeira.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/producers/me/sectors/{sectorId}/batches")
public class BatchController {
     private final IBatchService batchService;

    @PostMapping()
    public ResponseEntity<BatchCreateResponse> createBatch(
            Authentication authentication,
            @PathVariable String eventId,
            @PathVariable String sectorId,
            @RequestBody BatchCreateRequest request
    ){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(batchService.createBatch(eventId, sectorId, user.getId(), request));
    }

    @GetMapping()
    public ResponseEntity<List<BatchListAllBySector>> getBatchesBySector(
            Authentication authentication,
            @PathVariable String eventId,
            @PathVariable String sectorId
    ){
        AuthenticatedUser user = SecurityUtils.getCurrentUser();

        return ResponseEntity.ok(batchService.listAllBatchByEvent(eventId, sectorId, user.getId()));
    }
}
