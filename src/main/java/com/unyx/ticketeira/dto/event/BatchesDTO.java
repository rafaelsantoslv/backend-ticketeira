package com.unyx.ticketeira.dto.event;

public record BatchesDTO(
        String id,
        String name,
        double price,
        long quantity,
        long sold,
        double revenue
) {
}
