package com.unyx.ticketeira.repository.projection;

public interface PaymentMethodSummaryProjection {
    String getPaymentMethod();
    Long getTotalPayments();
    Double getTotalValue();
    Long getTotalSold();
}
