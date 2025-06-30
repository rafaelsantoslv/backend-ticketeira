package com.unyx.ticketeira.dto.event.dto;

public record SummaryDTO(
    long totalSold,
    double ticketMedium,
    double totalRevenue
) {
}
