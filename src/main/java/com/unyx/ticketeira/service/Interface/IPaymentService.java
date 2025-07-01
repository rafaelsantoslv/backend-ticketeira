package com.unyx.ticketeira.service.Interface;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.unyx.ticketeira.dto.payment.CardPaymentResponse;
import com.unyx.ticketeira.dto.payment.PixPaymentResponse;
import com.unyx.ticketeira.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPaymentService {
    CardPaymentResponse processCardPayment(String orderId, String userId, String cardToken) throws MPException, MPApiException;
    PixPaymentResponse processPixPayment(String orderId, String userId) throws MPException, MPApiException;
    List<Payment> findByEventId(String eventId);

}
