package com.unyx.ticketeira.dto.batch;

import java.math.BigDecimal;

public record BatchCreateResponse(
        String id,
        String name,
        int quantity,
        BigDecimal price,
        Boolean active,
        String message
) {
}
