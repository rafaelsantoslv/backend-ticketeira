package com.unyx.ticketeira.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.order.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.order.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GWMercadoPago {

    public void GwTeste() {
        MercadoPagoConfig.setAccessToken("{{TEST-1531312437411117-052816-27c78a9845e9cc03c17aaac918afd859-1349485162}}");

        OrderClient orderClient = new OrderClient();

        OrderPaymentRequest payment = OrderPaymentRequest.builder()
                .amount("10.00")
                .paymentMethod(OrderPaymentMethodRequest.builder()
                        .id("master")
                        .type("credit_card")
                        .token("{{CARD_TOKEN}}")
                        .installments(1)
                        .build())
                .build();

        List<OrderPaymentRequest> payments = new ArrayList<>();
        payments.add(payment);

        OrderCreateRequest request = OrderCreateRequest.builder()
                .type("online")
                .totalAmount("10.00")
                .externalReference("ext_ref")
                .payer(OrderPayerRequest.builder().email("{{EMAIL}}").build())
                .transactions(OrderTransactionRequest.builder()
                        .payments(payments)
                        .build())
                .build();

        Map<String, String> headers =  new HashMap<>();
        headers.put("X-Idempotency-Key", "{{IDEMPOTENCY_KEY}}");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(headers)
                .build();
        try {
            Order order = orderClient.create(request, requestOptions);
            System.out.println("Order created: " + order.getId());
        } catch (MPApiException | MPException e) {
            System.out.println("Error creating order: " + e.getMessage());
        }
    }


}
