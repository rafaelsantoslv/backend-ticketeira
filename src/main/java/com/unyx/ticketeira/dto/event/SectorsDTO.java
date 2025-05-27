package com.unyx.ticketeira.dto.event;

public record SectorsDTO(
        String id,
        String name,
        String description,
        BatchesDTO batches
) {
}
