package com.unyx.ticketeira.service;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.unyx.ticketeira.dto.payment.CardPaymentPayload;
import com.unyx.ticketeira.dto.payment.CardPaymentResponse;
import com.unyx.ticketeira.dto.payment.PixPaymentPayload;
import com.unyx.ticketeira.dto.payment.PixPaymentResponse;
import com.unyx.ticketeira.model.*;
import com.unyx.ticketeira.repository.PaymentRepository;
import com.unyx.ticketeira.service.Interface.IGatewayPagamento;
import com.unyx.ticketeira.service.Interface.IOrderService;
import com.unyx.ticketeira.service.Interface.IPaymentService;
import com.unyx.ticketeira.service.Interface.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class PaymentService implements IPaymentService {

    private final IOrderService orderService;

    private final IUserService userService;

    private final IGatewayPagamento gatewayPagamento;

    private final PaymentRepository paymentRepository;

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
        System.out.println(payload);

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

    public List<Payment> findByEventId(String eventId) {
        return paymentRepository.findByEventId(eventId);
    }
}
