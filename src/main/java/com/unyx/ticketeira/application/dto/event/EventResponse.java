package com.unyx.ticketeira.application.dto.event;

import java.time.LocalDateTime;

public record EventResponse(
        String id,
        String title,
        LocalDateTime date,
        String venueName,
        String address,
        String status,
        String image
) {
}
