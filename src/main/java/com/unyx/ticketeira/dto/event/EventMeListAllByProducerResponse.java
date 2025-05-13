package com.unyx.ticketeira.dto.event;

import java.time.LocalDateTime;

public record EventMeListAllByProducerResponse(
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
        Long soldQuantity
) {
}
