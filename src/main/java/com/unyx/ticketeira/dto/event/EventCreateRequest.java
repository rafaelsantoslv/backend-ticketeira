package com.unyx.ticketeira.dto.event;

import java.time.LocalDateTime;

public record EventCreateRequest(
        String title,
        String description,
        String category,
        String ageRating,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String locationName,
        String locationAddress,
        String locationCity,
        String locationState,
        String locationZip

) {
}
