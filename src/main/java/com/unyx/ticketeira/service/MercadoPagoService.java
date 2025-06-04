package com.unyx.ticketeira.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.unyx.ticketeira.exception.InvalidCardTokenException;
import com.unyx.ticketeira.exception.PaymentConfigurationException;
import com.unyx.ticketeira.exception.PaymentException;
import com.unyx.ticketeira.exception.PaymentGatewayException;
import com.unyx.ticketeira.service.Interface.IGatewayPagamento;
import com.unyx.ticketeira.service.Interface.IOrderService;
import com.unyx.ticketeira.service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


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
        validatePixPayload(payload);

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

        Payment payment = executePayment(request, requestOptions);

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

        validateCardPayload(payload);

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


        Payment payment = executePayment(request, requestOptions);

        return new CardPaymentResponse(
                payment.getStatus(),
                payment.getId(),
                payment.getTransactionAmount(),
                payment.getPaymentMethodId(),
                payment.getStatusDetail()
        );

    }

    private Payment executePayment(PaymentCreateRequest request, MPRequestOptions requestOptions) {
        try {
            return paymentClient.create(request, requestOptions);
        } catch (MPApiException e) {
            handleMPApiException(e);
            return null; // Nunca vai chegar aqui
        } catch (MPException e) {
            throw new PaymentConfigurationException("Erro de configuração do MercadoPago", e);
        }
    }

    private void handleMPApiException(MPApiException e) {
        String errorMessage = e.getApiResponse().getContent();

        String errorCode = extractErrorCode(errorMessage);
        String description = extractErrorDescription(errorMessage);

        switch (e.getStatusCode()) {
            case 400:
                if ("bin_not_found".equals(errorCode)) {
                    throw new InvalidCardTokenException("Token do cartão inválido ou cartão não suportado", e);
                }
                throw new PaymentGatewayException("Dados de pagamento inválidos", e.getStatusCode(), errorCode, description, e);
            case 401:
                throw new PaymentConfigurationException("Token de acesso inválido", e);
            case 500:
                throw new PaymentGatewayException("Erro interno do gateway de pagamento", e.getStatusCode(), errorCode, description, e);
            default:
                throw new PaymentGatewayException("Erro no gateway de pagamento", e.getStatusCode(), errorCode, description, e);
        }
    }

    private void validatePixPayload(PixPaymentPayload payload) {
        if (payload.amount() == null || payload.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Valor do pagamento deve ser maior que zero");
        }
        if (payload.email() == null || payload.email().trim().isEmpty()) {
            throw new PaymentException("Email é obrigatório");
        }
    }

    private void validateCardPayload(CardPaymentPayload payload) {
        if (payload.amount() == null || payload.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Valor do pagamento deve ser maior que zero");
        }
        if (payload.token() == null || payload.token().trim().isEmpty()) {
            throw new InvalidCardTokenException("Token do cartão é obrigatório");
        }
        if (payload.email() == null || payload.email().trim().isEmpty()) {
            throw new PaymentException("Email é obrigatório");
        }
        if (payload.cpf() == null || payload.cpf().trim().isEmpty()) {
            throw new PaymentException("CPF é obrigatório");
        }

    }

    private String extractErrorCode(String errorMessage) {
        // Implementar parse do JSON para extrair o error code
        // Exemplo: {"message":"bin_not_found","error":"bad_request"...}
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(errorMessage);
            return jsonNode.path("message").asText();
        } catch (Exception e) {
            return "unknown_error";
        }
    }

    private String extractErrorDescription(String errorMessage) {
        // Implementar parse do JSON para extrair a descrição
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(errorMessage);
            JsonNode causes = jsonNode.path("cause");
            if (causes.isArray() && !causes.isEmpty()) {
                return causes.get(0).path("description").asText();
            }
            return jsonNode.path("message").asText();
        } catch (Exception e) {
            return "Erro desconhecido";
        }
    }


}
