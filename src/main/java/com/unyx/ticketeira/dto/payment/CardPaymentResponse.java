package com.unyx.ticketeira.dto.payment;

import java.math.BigDecimal;

public record CardPaymentResponse(
        String status,
        Long paymentId,
        BigDecimal amount,
        String method,
        String message
) {
}
