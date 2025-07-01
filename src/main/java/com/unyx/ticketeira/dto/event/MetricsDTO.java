package com.unyx.ticketeira.dto.event;

import com.unyx.ticketeira.dto.payment.PaymentMetricsDTO;
import com.unyx.ticketeira.dto.ticket.CheckinStatusMetricsDTO;

import java.math.BigDecimal;

public record MetricsDTO(
        int totalSales,
        int totalTickets,
        BigDecimal averageTicket,
        int totalCheckins,
        BigDecimal totalValue,
        PaymentMetricsDTO payments,
        CheckinStatusMetricsDTO checkinStatus
) {
}
