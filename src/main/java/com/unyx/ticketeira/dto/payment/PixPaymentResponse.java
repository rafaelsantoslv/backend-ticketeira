package com.unyx.ticketeira.dto.payment;

import java.math.BigDecimal;

public record PixPaymentResponse(
        String status,
        Long paymentId,
        BigDecimal amount,
        String method,
        String qrCodeBase64,
        String qrCode
) {
}
