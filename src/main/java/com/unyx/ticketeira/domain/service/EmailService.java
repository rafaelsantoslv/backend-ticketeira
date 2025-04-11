package com.unyx.ticketeira.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public void sendEmailWithHtmlTemplate(String to, String subject, String name, String eventName, String date, String time, String location, String sector, String qrCodeUrl, String ticketCode) {
        try {
            // Carrega o HTML do arquivo
            String htmlBody = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/email-template.html")));

            // Substitui os placeholders no HTML
            htmlBody = htmlBody.replace("{{name}}", name)
                    .replace("{{eventName}}", eventName)
                    .replace("{{date}}", date)
                    .replace("{{time}}", time)
                    .replace("{{location}}", location)
                    .replace("{{sector}}", sector)
                    .replace("{{qrCodeUrl}}", qrCodeUrl)
                    .replace("{{ticketCode}}", ticketCode);

            // Cria a requisição para enviar o e-mail
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
                                    .html(Content.builder()
                                            .data(htmlBody)
                                            .charset("UTF-8")
                                            .build())
                                    .build())
                            .build())
                    .source("contatorafaelsantosdv@gmail.com") // Remetente (deve ser verificado)
                    .build();

            sesClient.sendEmail(emailRequest);
            System.out.println("E-mail enviado com sucesso para: " + to);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o template: " + e.getMessage());
        } catch (SesException e) {
            System.err.println("Erro ao enviar e-mail: " + e.awsErrorDetails().errorMessage());
            System.err.println("Código de erro: " + e.awsErrorDetails().errorCode());
            System.err.println("Status HTTP: " + e.statusCode());
        }
    }
}
