package com.unyx.ticketeira.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.unyx.ticketeira.dto.payment.PixPaymentPayload;
import com.unyx.ticketeira.dto.payment.PixPaymentResponse;
import com.unyx.ticketeira.model.User;
import com.unyx.ticketeira.service.Interface.IOrderService;
import com.unyx.ticketeira.service.Interface.IUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MercadoPagoService {
    @Autowired
    private MercadoPagoClient mercadoPagoClient;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PaymentClient paymentClient;



    public PixPaymentResponse createPixPayment(String orderId, String userId) throws MPException, MPApiException {
        com.unyx.ticketeira.model.Order order = orderService.validateOrderAndGetOrder(orderId);

        User userExist = userService.validateUserAndGetUser(userId);

        PixPaymentPayload pixPaymentPayload = new PixPaymentPayload(
                order.getTotal(),
                userExist.getEmail(),
                userExist.getName(),
                userExist.getName(),
                userExist.getDocument(),
                ("pagamento da order " + order.getId())
        );

//        PaymentClient client = new PaymentClient();

        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .transactionAmount(pixPaymentPayload.amount())
                .description("Pagamento de ingresso")
                .paymentMethodId("pix")
                .payer(PaymentPayerRequest.builder()
                        .email(userExist.getEmail())
                        .firstName(userExist.getName())
                        .lastName(userExist.getName())
                        .build())
                .build();

        Payment payment = paymentClient.create(request);

        return new PixPaymentResponse(
                payment.getId().toString(),
                order.getId(),
                payment.getStatus(), payment.getPointOfInteraction().getTransactionData().getQrCodeBase64(),
                payment.getPointOfInteraction().getTransactionData().getQrCode()
        );
    }


}
