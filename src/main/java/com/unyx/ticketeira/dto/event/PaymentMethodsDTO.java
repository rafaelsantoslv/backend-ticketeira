package com.unyx.ticketeira.dto.event;

public record PaymentMethodsDTO(
        String method,
        long quantity,
        double total
) {
}
