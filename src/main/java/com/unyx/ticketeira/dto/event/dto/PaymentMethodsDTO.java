package com.unyx.ticketeira.dto.event.dto;

public record PaymentMethodsDTO(
        String method,
        long quantity,
        double total
) {
}
