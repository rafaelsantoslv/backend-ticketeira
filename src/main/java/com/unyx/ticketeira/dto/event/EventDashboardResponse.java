package com.unyx.ticketeira.dto.event;

public record EventDashboardResponse(
        long totalSold,
        double ticketMedium,
        double totalRevenue,
        CheckinStatsDTO checkins,
        SectorsDTO sectors,
        PaymentMethodsDTO paymentMethods,
        RecentSalesDTO recentSales
) {
}
