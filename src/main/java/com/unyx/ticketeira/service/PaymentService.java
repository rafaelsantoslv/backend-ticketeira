package com.unyx.ticketeira.service;

import com.unyx.ticketeira.dto.payment.CardPaymentPayload;
import com.unyx.ticketeira.dto.payment.CardPaymentResponse;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.service.Interface.IOrderService;
import com.unyx.ticketeira.service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    public CardPaymentResponse processCardPayment(String orderId, String userId, String cardToken) {
        Order order = orderService.validateOrderAndGetOrder(orderId);
        User user = userService.validateUserAndGetUser(userId);

        CardPaymentPayload payload = new CardPaymentPayload(
                order.getTotal(),
                user.getEmail(),
                user.getDocument(),
                user.getName(),
                user.getName(),
                cardToken
        );

        retur
    }
}
