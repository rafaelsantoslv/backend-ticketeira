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
}
