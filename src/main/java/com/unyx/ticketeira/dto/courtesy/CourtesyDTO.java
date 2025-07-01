package com.unyx.ticketeira.dto.courtesy;

import java.time.LocalDateTime;

public record CourtesyDTO(
        String id,
        String name,
        String email,
        boolean sent,
        String sector,
        LocalDateTime createdAt
) {
}
