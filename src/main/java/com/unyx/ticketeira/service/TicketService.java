package com.unyx.ticketeira.service;

import com.unyx.ticketeira.model.Ticket;
import com.unyx.ticketeira.repository.BatchRepository;
import com.unyx.ticketeira.repository.TicketRepository;
import com.unyx.ticketeira.service.Interface.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TicketService implements ITicketService {
    private final TicketRepository ticketRepository;

    public Long getCountByBatchId(String batchId) {
        return ticketRepository.countByBatchId(batchId);
    }

    public List<Ticket> getTicketsByEvent(String eventId) {
        return ticketRepository.findByEventId(eventId);
    }
}
