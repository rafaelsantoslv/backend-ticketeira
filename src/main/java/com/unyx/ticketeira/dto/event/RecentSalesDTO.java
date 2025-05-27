package com.unyx.ticketeira.dto.event;

import java.time.LocalDate;

public record RecentSalesDTO(
        String name,
        String email,
        double price,
        LocalDate date
) {
}
