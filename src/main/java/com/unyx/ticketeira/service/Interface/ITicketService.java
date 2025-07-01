package com.unyx.ticketeira.service.Interface;

import com.unyx.ticketeira.model.Ticket;

import java.util.List;

public interface ITicketService {
    Long getCountByBatchId(String batchId);
    List<Ticket> getTicketsByEvent(String eventId);
}
