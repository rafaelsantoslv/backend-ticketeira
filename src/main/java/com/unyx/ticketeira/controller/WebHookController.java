package com.unyx.ticketeira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
public class WebHookController {
    public ResponseEntity<Void> mercadoPagoWebHook(@RequestBody Map<String, Object> payload) {
        System.out.println("Webhook recebido: " + payload);
        return ResponseEntity.ok().build();
    }
}
