package com.unyx.ticketeira.domain.service.Interface;

import com.unyx.ticketeira.domain.model.Payment;

import java.util.List;
import java.util.Optional;

public interface IPaymentService {
    Payment process(Payment payment);
    Optional<Payment> findById(String id);
    List<Payment> findByOrder(String orderId);
    Payment refund(String paymentId);
    boolean validatePayment(String paymentId);
    List<String> getPaymentMethods();
    void handleWebhook(String payload);
    String generatePaymentIntent(String orderId);
}
