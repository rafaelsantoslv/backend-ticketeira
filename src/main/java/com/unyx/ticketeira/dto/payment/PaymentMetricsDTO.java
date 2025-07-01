package com.unyx.ticketeira.dto.payment;

import java.math.BigDecimal;

public record PaymentMetricsDTO(
        BigDecimal creditCard,
        BigDecimal pix,
        BigDecimal total
) {
}
