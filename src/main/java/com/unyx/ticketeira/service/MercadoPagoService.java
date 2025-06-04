package com.unyx.ticketeira.service;

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
import com.unyx.ticketeira.service.Interface.IGatewayPagamento;
import com.unyx.ticketeira.service.Interface.IOrderService;
import com.unyx.ticketeira.service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MercadoPagoService implements IGatewayPagamento {
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



    public PixPaymentResponse createPixPayment(PixPaymentPayload payload) throws MPException, MPApiException {
        System.setProperty("mercadopago.log.level", "DEBUG");

        PaymentCreateRequest request = PaymentCreateRequest.builder()
                .transactionAmount(payload.amount())
                .description("Pagamento de ingresso")
                .paymentMethodId("pix")
                .payer(PaymentPayerRequest.builder()
                        .email(payload.email())
                        .firstName(payload.firstName())
                        .lastName(payload.lastName())
                        .build())
                .build();

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .accessToken(accessToken)
                .build();

        Payment payment = paymentClient.create(request, requestOptions);

        return new PixPaymentResponse(
                payment.getStatus(),
                payment.getId(),
                payment.getTransactionAmount(),
                payment.getPaymentMethodId(),
                payment.getPointOfInteraction().getTransactionData().getQrCodeBase64(),
                payment.getPointOfInteraction().getTransactionData().getQrCode()
        );
    }

    public CardPaymentResponse createCardPayment(CardPaymentPayload payload) throws MPException, MPApiException {
        try {
            PaymentCreateRequest request = PaymentCreateRequest.builder()
                    .transactionAmount(payload.amount())
                    .token(payload.token())
                    .paymentMethodId("visa")
                    .description("Pagamento de ingresso")
                    .installments(1)
                    .payer(PaymentPayerRequest.builder()
                            .email(payload.email())
                            .identification(IdentificationRequest.builder()
                                    .type("CPF")
                                    .number(payload.cpf())
                                    .build())
                            .build())
                    .build();

            MPRequestOptions requestOptions = MPRequestOptions.builder()
                    .accessToken(accessToken)
                    .build();

            System.out.println("=== DADOS RECEBIDOS ===");
            System.out.println("Amount: " + payload.amount());
            System.out.println("Token: " + payload.token());
            System.out.println("Email: " + payload.email());
            System.out.println("CPF: " + payload.cpf());
            System.out.println("FirstName: " + payload.firstName());
            System.out.println("Access Token: " + accessToken.substring(0, 10) + "...");

            System.out.println("=== ENVIANDO REQUEST ===");
            Payment payment = paymentClient.create(request, requestOptions);
            System.out.println("=== SUCESSO ===");

            return new CardPaymentResponse(
                    payment.getStatus(),
                    payment.getId(),
                    payment.getTransactionAmount(),
                    payment.getPaymentMethodId(),
                    payment.getStatusDetail()
            );

        } catch (MPApiException e) {
            // Aqui você loga os detalhes da resposta da API
            System.err.println("=== ERRO DA API DO MERCADOPAGO ===");
            System.err.println("Status Code: " + e.getStatusCode());
            System.err.println("Mensagem: " + e.getApiResponse().getContent());
            System.err.println("Cause: " + e.getCause());
            throw e; // relança para manter a stacktrace
        }
    }



}
