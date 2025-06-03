package com.unyx.ticketeira.service.Interface;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.unyx.ticketeira.dto.payment.CardPaymentResponse;
import com.unyx.ticketeira.dto.payment.PixPaymentResponse;

public interface IPaymentService {
    CardPaymentResponse processCardPayment(String orderId, String userId, String cardToken) throws MPException, MPApiException;
    PixPaymentResponse processPixPayment(String orderId, String userId) throws MPException, MPApiException;
}
