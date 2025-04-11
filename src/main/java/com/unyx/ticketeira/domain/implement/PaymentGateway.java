package com.unyx.ticketeira.domain.implement;

public interface PaymentGateway {
    boolean processPayment(double amount, String buyerEmail);
}
