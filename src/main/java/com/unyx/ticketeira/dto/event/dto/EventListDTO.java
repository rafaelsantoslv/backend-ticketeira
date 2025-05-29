package com.unyx.ticketeira.dto.event.dto;

import java.time.LocalDateTime;

public record EventListDTO(
        String id,
        String title,
        String description,
        String ageRating,
        String locationName,
        String locationCity,
        String locationAddress,
        String locationState,
        String locationZip,
        String category,
        String imageUrl,
        boolean isPublished,
        boolean isFeatured,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
