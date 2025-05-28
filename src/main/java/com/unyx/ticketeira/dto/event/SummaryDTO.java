package com.unyx.ticketeira.dto.event;

public record SummaryDTO(
    long totalSold,
    double ticketMedium,
    double totalRevenue
) {
}
