package com.unyx.ticketeira.dto.event;

public record EventCreateResponse(
        String id,
        String imageUrl,
        String message
) {
}
