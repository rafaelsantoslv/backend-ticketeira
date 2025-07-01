package com.unyx.ticketeira.dto.ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketDTO(
        String id,
        String name,
        String email,
        String sector,
        String status,
        BigDecimal price,
        LocalDateTime createdAt
) {
}
