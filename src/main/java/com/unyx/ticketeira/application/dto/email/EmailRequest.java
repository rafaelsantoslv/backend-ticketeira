package com.unyx.ticketeira.application.dto.email;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
}
