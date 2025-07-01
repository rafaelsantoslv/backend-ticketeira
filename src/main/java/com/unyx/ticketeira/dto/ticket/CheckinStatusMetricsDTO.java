package com.unyx.ticketeira.dto.ticket;

public record CheckinStatusMetricsDTO(
        int total,
        int validated,
        int pending,
        int cancelled
) {
}
