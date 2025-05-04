package com.unyx.ticketeira.application.dto.batch;

public record BatchCreateResponse(
        String id,
        String name,
        int quantity,
        Double price,
        Boolean active,
        String message
) {
}
