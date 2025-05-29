package com.unyx.ticketeira.dto.batch;

public record BatchDTO(
        String name,
        Double price,
        Boolean isActive
) {
}
