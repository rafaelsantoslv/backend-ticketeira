package com.unyx.ticketeira.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SesClient sesClient;

    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(Destination.builder()
                        .toAddresses(to)
                        .build())
                .message(Message.builder()
                        .subject(Content.builder()
                                .data(subject)
                                .charset("UTF-8")
                                .build())
                        .body(Body.builder()
                                .text(Content.builder()
                                        .data(body)
                                        .charset("UTF-8")
                                        .build())
                                .build())
                        .build())
                .source("contatorafaelsantosdv@gmail.com") // Remetente (deve ser verificado no modo sandbox)
                .build();

        sesClient.sendEmail(emailRequest);
        System.out.println("E-mail enviado com sucesso para: " + to);
    }
}
