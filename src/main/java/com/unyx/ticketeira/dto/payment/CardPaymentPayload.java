package com.unyx.ticketeira.dto.payment;

import java.math.BigDecimal;

public record CardPaymentPayload(
        BigDecimal amount,
        String email,
        String cpf,
        String firstName,
        String lastName,
        String token
) {
}
