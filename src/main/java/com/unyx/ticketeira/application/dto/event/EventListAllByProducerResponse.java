package com.unyx.ticketeira.application.dto.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public record EventListAllByProducerResponse(
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
        String creator,
        LocalDateTime startDate,
        LocalDateTime endDate

) {
}
