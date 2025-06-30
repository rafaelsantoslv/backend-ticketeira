package com.unyx.ticketeira.dto.sector;

public record SectorCreateResponse(
        String id,
        String name,
        int capacity,
        String description,
        String message
) {
}
