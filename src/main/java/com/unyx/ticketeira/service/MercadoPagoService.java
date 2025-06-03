package com.unyx.ticketeira.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.unyx.ticketeira.dto.payment.CardPaymentPayload;
import com.unyx.ticketeira.dto.payment.CardPaymentResponse;
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

    @Value("${mercadopago.accessToken}")
    private String accessToken;



    public PixPaymentResponse createPixPayment(String orderId, String userId) throws MPException, MPApiException {
        com.unyx.ticketeira.model.Order order = orderService.validateOrderAndGetOrder(orderId);

        User userExist = userService.validateUserAndGetUser(userId);

        System.out.println(userExist.getEmail());
        System.out.println(userExist.getDocument());

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

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .accessToken(accessToken)
                .build();

        Payment payment = paymentClient.create(request, requestOptions);

        return new PixPaymentResponse(
                payment.getId().toString(),
                order.getId(),
                payment.getStatus(), payment.getPointOfInteraction().getTransactionData().getQrCodeBase64(),
                payment.getPointOfInteraction().getTransactionData().getQrCode()
        );
    }

    public CardPaymentResponse createCardPayment(CardPaymentPayload payload) throws MPException, MPApiException {

        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .transactionAmount(payload.amount())
                .description("Pagamento de ingresso")
                .installments(1)
                .token(payload.token())
                .payer(PaymentPayerRequest.builder()
                        .email(payload.email())
                        .identification(IdentificationRequest.builder()
                                .type("CPF")
                                .number(payload.cpf()).build())
                        .firstName(payload.firstName())
                        .lastName(payload.firstName())
                        .build())
                .build();

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .accessToken(accessToken)
                .build();

        Payment payment = paymentClient.create(request, requestOptions);

        return new CardPaymentResponse(
                payment.getStatus(),
                payment.getId(),
                payment.getTransactionAmount(),
                payment.getPaymentMethodId(),
                payment.getStatusDetail()
        );
    }


}
