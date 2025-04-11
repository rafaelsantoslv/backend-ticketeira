package com.unyx.ticketeira.domain.service;

import com.unyx.ticketeira.domain.implement.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class FakePaymentGateway implements PaymentGateway {

    @Override
    public boolean processPayment(double amount, String buyerEmail) {
        System.out.println("Simulando pagamento para o e-mail "+ buyerEmail + " No valor de " + amount );
        return true;
    }
}
