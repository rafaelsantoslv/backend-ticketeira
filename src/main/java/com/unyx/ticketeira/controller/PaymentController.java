package com.unyx.ticketeira.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.unyx.ticketeira.config.security.AuthenticatedUser;
import com.unyx.ticketeira.dto.payment.PixPaymentResponse;
import com.unyx.ticketeira.service.MercadoPagoService;
import com.unyx.ticketeira.service.PaymentService;
import com.unyx.ticketeira.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseEntity<PixPaymentResponse> createPixPayment(@PathVariable String orderId) throws MPException, MPApiException {
        AuthenticatedUser user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(paymentService.processPixPayment(orderId, user.getId()));
    }
}
