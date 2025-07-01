package com.unyx.ticketeira.dto.event;

public record EventUpdateResponse(
            String id,
            String title,
            String description,
            String category,
            String ageRating,
            String locationName,
            String locationAddress,
            String locationCity,
            String locationState,
            String locationZip,
            boolean isPublished,
            boolean isFeatured
) {
}
