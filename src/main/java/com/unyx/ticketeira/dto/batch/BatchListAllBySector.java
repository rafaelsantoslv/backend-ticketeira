package com.unyx.ticketeira.dto.batch;

import java.time.LocalDateTime;

public record BatchListAllBySector(
        String id,
        String name,
        int quantity,
        Double price,
        Boolean active
        ) {
}
