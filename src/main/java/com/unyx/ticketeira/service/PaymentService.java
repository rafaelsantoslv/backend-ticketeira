package com.unyx.ticketeira.service;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.unyx.ticketeira.dto.payment.CardPaymentPayload;
import com.unyx.ticketeira.dto.payment.CardPaymentResponse;
import com.unyx.ticketeira.dto.payment.PixPaymentPayload;
import com.unyx.ticketeira.dto.payment.PixPaymentResponse;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.service.Interface.IGatewayPagamento;
import com.unyx.ticketeira.service.Interface.IOrderService;
import com.unyx.ticketeira.service.Interface.IPaymentService;
import com.unyx.ticketeira.service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IGatewayPagamento gatewayPagamento;

    public CardPaymentResponse processCardPayment(String orderId, String userId, String cardToken) throws MPException, MPApiException {
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

        return gatewayPagamento.createCardPayment(payload);
    }

    public PixPaymentResponse processPixPayment(String orderId, String userId) throws MPException, MPApiException {
        Order order = orderService.validateOrderAndGetOrder(orderId);
        User user = userService.validateUserAndGetUser(userId);

        PixPaymentPayload payload = new PixPaymentPayload(
                order.getTotal(),
                user.getEmail(),
                user.getName(),
                user.getName(),
                user.getDocument(),
                ("pagamento da order " + order.getId())
        );
        return  gatewayPagamento.createPixPayment(payload);
    }
}
