package com.unyx.ticketeira.dto.event;

public record EventDashboardResponse(
        SummaryDTO summary,
        CheckinStatsDTO checkins,
        SectorsDTO sectors,
        PaymentMethodsDTO paymentMethods,
        RecentSalesDTO recentSales
) {
}
