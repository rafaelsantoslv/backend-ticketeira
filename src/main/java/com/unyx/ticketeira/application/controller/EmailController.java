package com.unyx.ticketeira.application.controller;

import com.unyx.ticketeira.application.dto.email.EmailRequest;
import com.unyx.ticketeira.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return "E-mail enviado para: " + emailRequest.getTo();
    }

    @PostMapping("/send-html")
    public String sendEmailWithHtml(@RequestParam String to) {
        emailService.sendEmailWithHtmlTemplate(
                to,
                "Confirmação de Compra de Ingressos",
                "Rafael Santos",
                "Festival de Verão",
                "15 de Abril de 2025",
                "20:00",
                "Arena Unyx",
                "VIP GOLD",
                "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=UNYX-EVENT-12345",
                "UNYX-EVENT-12345"
        );
        return "E-mail enviado para: " + to;
    }
}
