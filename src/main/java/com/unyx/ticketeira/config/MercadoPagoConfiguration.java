package com.unyx.ticketeira.config;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.payment.PaymentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfiguration {

    @Value("${mercadopago.accessToken}")
    private String accessToken;

    @Bean
    public PaymentClient paymentClient() {
        MercadoPagoConfig.setAccessToken(accessToken);
        return new PaymentClient();
    }
}
