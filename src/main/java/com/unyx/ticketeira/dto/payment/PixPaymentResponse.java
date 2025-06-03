package com.unyx.ticketeira.dto.payment;

public record PixPaymentResponse(
        String paymentId,
        String orderId,
        String status,
        String qrCodeBase64,
        String qrCode
) {
}
