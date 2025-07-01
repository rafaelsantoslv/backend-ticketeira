package com.unyx.ticketeira.service;

import com.unyx.ticketeira.model.Ticket;
import com.unyx.ticketeira.repository.BatchRepository;
import com.unyx.ticketeira.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public Long getCountByBatchId(String batchId) {
        return ticketRepository.countByBatchId(batchId);
    }
}
