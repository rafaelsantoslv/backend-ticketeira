package com.unyx.ticketeira.application.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventResponse {
    private String id;
    private String title;
    private String description;
    private String nameLocale;
    private String address;
    private String classification;
    private String category;
    private String imageUrl;
    private LocalDateTime eventDate;
    private String producerEmail;
}
