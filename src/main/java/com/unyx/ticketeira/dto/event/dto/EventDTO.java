package com.unyx.ticketeira.dto.event.dto;

import java.time.LocalDateTime;

public record EventDTO(
        String id,
        String title,
        String locationName,
        String locationCity,
        String locationState,
        String category,
        String imageUrl,
        boolean isPublished,
        boolean isFeatured,
        LocalDateTime startDate,
        long soldQuantity
) {
}
