package com.unyx.ticketeira.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.dto.payment.CardPaymentRequest;
import com.unyx.ticketeira.dto.payment.CardPaymentResponse;
import com.unyx.ticketeira.dto.payment.PixPaymentResponse;
import com.unyx.ticketeira.service.Interface.IPaymentService;
import com.unyx.ticketeira.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/pix/{orderId}")
    public ResponseEntity<PixPaymentResponse> createPixPayment(@PathVariable String orderId) throws MPException, MPApiException {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(paymentService.processPixPayment(orderId, user.getId()));
    }

    @PostMapping("/card")
    public ResponseEntity<CardPaymentResponse> createCardPayment(@RequestBody CardPaymentRequest cardPaymentRequest) throws MPException, MPApiException {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(paymentService.processCardPayment(cardPaymentRequest.orderId(), user.getId(), cardPaymentRequest.cardToken()));
    }
}
