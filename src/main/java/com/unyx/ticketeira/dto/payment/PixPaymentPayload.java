package com.unyx.ticketeira.dto.payment;

import java.math.BigDecimal;

public record PixPaymentPayload(
        BigDecimal amount,
        String email,
        String firstName,
        String lastName,
        String cpf,
        String description
) {
}
