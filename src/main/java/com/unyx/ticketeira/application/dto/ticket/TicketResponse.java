package com.unyx.ticketeira.application.dto.ticket;

import com.unyx.ticketeira.application.dto.event.EventCreateResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketResponse {
    private String id;
    private String batchName;
    private Double price;
    private Integer quantity;
    private Boolean isActive;
    private EventCreateResponse event;
}
