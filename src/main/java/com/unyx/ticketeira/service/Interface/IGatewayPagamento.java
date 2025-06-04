package com.unyx.ticketeira.service.Interface;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.unyx.ticketeira.dto.payment.CardPaymentPayload;
import com.unyx.ticketeira.dto.payment.CardPaymentResponse;
import com.unyx.ticketeira.dto.payment.PixPaymentPayload;
import com.unyx.ticketeira.dto.payment.PixPaymentResponse;

public interface IGatewayPagamento {
    CardPaymentResponse createCardPayment(CardPaymentPayload payload) throws MPException, MPApiException;
    PixPaymentResponse createPixPayment(PixPaymentPayload payload) throws MPException, MPApiException;
}
