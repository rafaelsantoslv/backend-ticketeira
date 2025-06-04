package com.unyx.ticketeira.dto.payment;

public record CardPaymentRequest(
        String orderId,
        String cardToken
) {
}
