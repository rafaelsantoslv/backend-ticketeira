package com.unyx.ticketeira.dto.batch;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BatchListAllBySector(
        String id,
        String name,
        int quantity,
        BigDecimal price,
        Boolean active
        ) {
}
