package com.unyx.ticketeira.mapper;

import com.unyx.ticketeira.dto.ticket.TicketDTO;
import com.unyx.ticketeira.model.Ticket;

public class TicketMapper {
    public static TicketDTO toDTO(Ticket ticket, String sectorName) {
            return new TicketDTO(
                     ticket.getId(),
                     ticket.getOwnerName(),
                     ticket.getOwnerEmail(),
                     sectorName,
                     ticket.getStatus().name(),
                     ticket.getPrice(),
                     ticket.getCreatedAt()
            );
    }
}
