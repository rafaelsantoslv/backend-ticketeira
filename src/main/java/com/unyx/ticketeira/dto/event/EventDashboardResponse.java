package com.unyx.ticketeira.dto.event;

import com.unyx.ticketeira.dto.event.dto.*;

public record EventDashboardResponse(
        SummaryDTO summary,
        CheckinStatsDTO checkins,
        SectorsDTO sectors,
        PaymentMethodsDTO paymentMethods,
        RecentSalesDTO recentSales
) {
}
