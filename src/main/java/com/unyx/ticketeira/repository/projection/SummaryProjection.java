package com.unyx.ticketeira.repository.projection;

public interface SummaryProjection {
    Long getTotalSold();
    Double getTicketMedium();
    Double getTotalRevenue();
}
