package com.unyx.ticketeira.dto.batch;

import java.math.BigDecimal;

public record BatchDTO(
        String id,
        String name,
        BigDecimal price,
        Boolean isActive
) {
}
