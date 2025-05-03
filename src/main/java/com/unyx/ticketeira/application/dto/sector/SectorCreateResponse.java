package com.unyx.ticketeira.application.dto.sector;

public record SectorCreateResponse(
        String id,
        String name,
        int capacity,
        String description,
        String message
) {
}
