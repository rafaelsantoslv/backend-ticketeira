package com.unyx.ticketeira.dto.event.dto;

import java.time.LocalDate;

public record RecentSalesDTO(
        String name,
        String email,
        double price,
        LocalDate date
) {
}
